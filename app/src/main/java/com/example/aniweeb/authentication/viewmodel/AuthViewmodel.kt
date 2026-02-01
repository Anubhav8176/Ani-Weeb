package com.example.aniweeb.authentication.viewmodel

import android.content.Context
import android.util.Log
import androidx.compose.ui.platform.LocalContext
import androidx.credentials.Credential
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aniweeb.R
import com.example.aniweeb.authentication.model.UserInfo
import com.example.aniweeb.authentication.response.AuthResponse
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential.Companion.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewmodel @Inject constructor(
    @ApplicationContext private val context: Context,
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


    //Authentication using email and password.
    fun loginUser(email: String, password: String){
        viewModelScope.launch {
            _isLoggedIn.value = AuthResponse.Loading
            auth
                .signInWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    getCurrentUser()
                    _isLoggedIn.value = AuthResponse.Success
                }
                .addOnFailureListener {
                    _isLoggedIn.value = AuthResponse.Failure(it.message.toString())
                }

            _isLoggedIn.value = AuthResponse.Idle
        }
    }

    fun makeIsLoggedInIdle(){
        _isLoggedIn.value = AuthResponse.Idle
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
                            }
                            .addOnFailureListener {
                                _isLoggedIn.value = AuthResponse.Failure(it.message.toString())
                            }
                    }
                }
                .addOnFailureListener {
                    _isLoggedIn.value = AuthResponse.Failure(it.message.toString())
                }
        }
    }

    //Authentication using google accounts
    fun signInWithGoogleButton() {
        viewModelScope.launch {
            try {

                val credentialManager: CredentialManager = CredentialManager.create(context)

                val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
                    .setFilterByAuthorizedAccounts(false)
                    .setServerClientId(context.getString(R.string.web_client_id))
                    .build()

                val request = GetCredentialRequest.Builder()
                    .addCredentialOption(googleIdOption)
                    .build()

                val result = credentialManager.getCredential(
                    request = request,
                    context = context
                )

                val credential = result.credential

                handleSignIn(credential)
            }catch (e: GetCredentialException){
                _isLoggedIn.value = AuthResponse.Failure("An unexpected error encountered in signing in with google.")
            }
        }
    }

    private suspend fun handleSignIn(credential: Credential?){
        if (credential is CustomCredential && credential.type == TYPE_GOOGLE_ID_TOKEN_CREDENTIAL){
            val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
            firebaseAuthWithGoogle(googleIdTokenCredential.idToken)
        }else{
            _isLoggedIn.value = AuthResponse.Failure("Invalid credential")
        }
    }

    private suspend fun firebaseAuthWithGoogle(idToken: String){
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener { authentication->
                if(authentication.isSuccessful){
                    val currentUser = auth.currentUser
                    if(currentUser!=null){
                        val userInfo = UserInfo(
                            name = currentUser.displayName.toString(),
                            email = currentUser.email.toString(),
                            gender = "",
                            imageUrl = currentUser.photoUrl.toString()
                        )

                        firestore
                            .collection("user")
                            .document(currentUser.uid)
                            .set(userInfo)
                            .addOnSuccessListener {
                                _isLoggedIn.value = AuthResponse.Success
                                getCurrentUser()
                            }
                            .addOnFailureListener {
                                _isLoggedIn.value = AuthResponse.Failure(it.message.toString())
                            }
                    }
                }else{
                    _isLoggedIn.value = AuthResponse.Failure("User is not authenticated, Try again.")
                }
            }
    }


    //Utility functions for authentication.
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
                        }
                    }
                    .addOnFailureListener {
                        _isLoggedIn.value = AuthResponse.Failure("Failed to fetch the user details.")
                    }
            }
        }
    }
}