package com.anucodes.otakuhub.core.networking.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anucodes.otakuhub.core.model.FavoriteStatus
import com.anucodes.otakuhub.core.model.MangaFullInfo
import com.anucodes.otakuhub.core.model.MangaMinInfo
import com.anucodes.otakuhub.core.model.MangaResponse
import com.anucodes.otakuhub.core.model.NetworkStatus
import com.anucodes.otakuhub.core.networking.retrofit.AnimeApiInterface
import com.anucodes.otakuhub.presentation.favorites.model.UserFavorite
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MangaViewModel @Inject constructor(
    private val animeApiInterface: AnimeApiInterface,
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
):ViewModel() {

    private val _networkStatus = MutableStateFlow<NetworkStatus>(NetworkStatus.Idle)
    val networkStatus = _networkStatus.asStateFlow()

    private val _favoriteStatus = MutableStateFlow<FavoriteStatus>(FavoriteStatus.Idle)
    val favoriteStatus = _favoriteStatus.asStateFlow()

    private var page = 1

    private val _topManga = MutableStateFlow<MangaResponse?>(null)
    val topManga = _topManga.asStateFlow()

    private val _latestManga = MutableStateFlow<MangaResponse?>(null)
    val latestManga = _latestManga.asStateFlow()

    private val _allManga = MutableStateFlow<List<MangaMinInfo>>(emptyList())
    val allManga = _allManga.asStateFlow()

    private val _mangaInfo = MutableStateFlow<MangaFullInfo?>(null)
    val mangaInfo = _mangaInfo.asStateFlow()

    fun getTopManga(){
        viewModelScope.launch {
            _networkStatus.value = NetworkStatus.Loading
            try {
                val response = animeApiInterface.getTopManga("bypopularity")
                _topManga.value = response
                _networkStatus.value = NetworkStatus.Success
            }catch (e: Exception){
                Log.e("Didn't get top manga: ", e.message.toString())
                _networkStatus.value = NetworkStatus.Failure("Failed to get top manga!")
            }
        }
    }

    fun getLatestManga(){
        viewModelScope.launch {
            _networkStatus.value = NetworkStatus.Loading
            try {
                val response = animeApiInterface.getLatestManga("publishing")
                _latestManga.value = response
                _networkStatus.value = NetworkStatus.Success
            }catch (e: Exception){
                Log.e("Didn't get latest manga: ", e.message.toString())
                _networkStatus.value = NetworkStatus.Failure("Failed to get latest manga!")
            }
        }
    }

    fun getAllManga(){
        viewModelScope.launch {
            _networkStatus.value = NetworkStatus.Loading
            try {
                val response = animeApiInterface.getAllManga(page)
                _allManga.value += response.data
                page++
                _networkStatus.value = NetworkStatus.Success
            }catch (e: Exception){
                Log.e("Didn't get all manga: ", e.message.toString())
                _networkStatus.value = NetworkStatus.Failure("Failed to get all manga!")
            }
        }
    }

    fun getMangaById(id: Int){
        viewModelScope.launch {
            _networkStatus.value = NetworkStatus.Loading
            try {
                val response = animeApiInterface.getMangaInfo(id = id)
                _mangaInfo.value = response
                _networkStatus.value = NetworkStatus.Success
            }catch (e: Exception){
                Log.e("Didn't get manga with $id: ", e.message.toString())
                _networkStatus.value = NetworkStatus.Failure("Failed to get manga!")
            }
        }
    }

    fun addMangaToFavorite(favAnime: UserFavorite){
        viewModelScope.launch {
            _favoriteStatus.value = FavoriteStatus.Loading
            try {
                val currUser = auth.currentUser
                if (currUser != null){
                    val userId = currUser.uid
                    val userRef = firestore
                        .collection("favorite")
                        .document(userId)

                    userRef.get()
                        .addOnSuccessListener {document->
                            if (document.exists()) {
                                userRef.update("favorite", FieldValue.arrayUnion(favAnime))
                            }else{
                                userRef.set(mapOf("favorite" to listOf(favAnime)), SetOptions.merge())
                            }
                        }
                    _favoriteStatus.value = FavoriteStatus.Success("Added to favorite manga")
                }
            }catch (e: Exception){
                Log.e("Cannot add to favorite: ", e.message.toString())
                _favoriteStatus.value = FavoriteStatus.Failure("Can't add to favorite manga")
            }
        }
    }

}