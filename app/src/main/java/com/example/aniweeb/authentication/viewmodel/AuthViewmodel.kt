package com.example.aniweeb.authentication.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aniweeb.authentication.model.UserInfo
import com.example.aniweeb.authentication.response.AuthResponse
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewmodel @Inject constructor(
    val auth: FirebaseAuth,
    val firestore: FirebaseFirestore,
    val googleSignInClient: GoogleSignInClient
):ViewModel() {

    private val _isLoggedIn = MutableStateFlow<AuthResponse>(AuthResponse.Idle)
    val isLoggedIn = _isLoggedIn.asStateFlow()

    private val _isAuthenticated = MutableStateFlow(false)
    val isAuthenticated = _isAuthenticated.asStateFlow()

    private val _currentUser = MutableStateFlow<UserInfo?>(null)
    val currentUser = _currentUser.asStateFlow()

    init {
        checkUserAuthenticated()
    }

    fun loginUser(email: String, password: String){
        viewModelScope.launch {
            auth
                .signInWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    _isLoggedIn.value = AuthResponse.Success
                    getCurrentUser()
                    Log.i("Login user", "Login successful!")
                }
                .addOnFailureListener {
                    _isLoggedIn.value = AuthResponse.Failure(it.message.toString())
                    Log.e("Login user", "Login is unsuccessful!")
                }
        }
    }

    fun registerUser(userInfo: UserInfo, password: String){
        viewModelScope.launch {
            auth
                .createUserWithEmailAndPassword(userInfo.email, password)
                .addOnSuccessListener {
                    val currUser = auth.currentUser
                    if (currUser != null){
                        firestore
                            .collection("user")
                            .document(currUser.uid)
                            .set(userInfo)
                            .addOnSuccessListener {
                                _isLoggedIn.value = AuthResponse.Success
                                getCurrentUser()
                                Log.i("Register user", "The user is registered successfully!")
                            }
                            .addOnFailureListener {
                                _isLoggedIn.value = AuthResponse.Failure(it.message.toString())
                                Log.i("Register user", "Failed to load the user!")
                            }
                    }
                }
                .addOnFailureListener {
                    _isLoggedIn.value = AuthResponse.Failure(it.message.toString())
                    Log.i("Register user", "Failed to load the user!")
                }
        }
    }

    private fun checkUserAuthenticated(){
        val currUser = auth.currentUser
        if (currUser != null) {
            _isAuthenticated.value = true
            getCurrentUser()
        }
        else
            _isAuthenticated.value = false
    }

    fun signOut(){
        auth.signOut()
        _isAuthenticated.value = false
        _isLoggedIn.value = AuthResponse.Idle
    }

    fun getCurrentUser(){
        viewModelScope.launch {
            val currUser = auth.currentUser
            if (currUser != null){
                val userId = currUser.uid

                firestore
                    .collection("user")
                    .document(userId)
                    .get()
                    .addOnSuccessListener { userInfo->
                        if (userInfo!=null){
                            val user = userInfo.toObject(UserInfo::class.java)
                            _currentUser.value = user
                            Log.i("Current User", user.toString())
                        }
                    }
                    .addOnFailureListener {
                        Log.e("Fetching User", it.message.toString())
                    }
            }
        }
    }
}