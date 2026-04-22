package com.anucodes.otakuhub.core.networking.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anucodes.otakuhub.core.model.AnimeFullInfo
import com.anucodes.otakuhub.core.model.AnimeMinInfo
import com.anucodes.otakuhub.core.model.AnimeResponse
import com.anucodes.otakuhub.core.model.FavoriteStatus
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
class AnimeViewModel @Inject constructor(
    private val animeApiInterface: AnimeApiInterface,
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
): ViewModel() {

    private val _networkStatus = MutableStateFlow<NetworkStatus>(NetworkStatus.Idle)
    val networkStatus = _networkStatus.asStateFlow()

    private val _favoriteStatus = MutableStateFlow<FavoriteStatus>(FavoriteStatus.Idle)
    val favoriteStatus = _favoriteStatus.asStateFlow()

    private val _favorites = MutableStateFlow<List<UserFavorite>>(emptyList())
    val favorites = _favorites.asStateFlow()

    private val _allAnime = MutableStateFlow<List<AnimeMinInfo>>(emptyList())
    val allAnime = _allAnime.asStateFlow()

    private val _topAnime = MutableStateFlow<AnimeResponse?>(null)
    val topAnime = _topAnime.asStateFlow()

    private var page = 1

    private val _latestAnime = MutableStateFlow<AnimeResponse?>(null)
    val latestAnime = _latestAnime.asStateFlow()

    private val _animeInfo = MutableStateFlow<AnimeFullInfo?>(null)
    val animeInfo = _animeInfo.asStateFlow()

    fun getAnime(){
        viewModelScope.launch {
            _networkStatus.value = NetworkStatus.Loading
            try {
                val response = animeApiInterface.getAllAnime(page = page)
                _allAnime.value += response.data
                page++
                _networkStatus.value = NetworkStatus.Success
            }catch (e: Exception){
                Log.e("Failed to load data: ", e.message.toString())
                _networkStatus.value = NetworkStatus.Failure("Failed to load Anime!")
            }
        }
    }

    fun getTopAnime(){
        viewModelScope.launch {
            _networkStatus.value = NetworkStatus.Loading
            try {
                val response = animeApiInterface.getTopAnime("bypopularity")
                _topAnime.value = response
                _networkStatus.value = NetworkStatus.Success
            }catch (e: Exception){
                Log.e("Failed to load data: ", e.message.toString())
                _networkStatus.value = NetworkStatus.Failure("Failed to load top animes!!")
            }
        }
    }

    fun getLatestAnime(){
        viewModelScope.launch {
            _networkStatus.value = NetworkStatus.Loading
            try {
                val response = animeApiInterface.getLatestAnime("airing")
                _latestAnime.value = response
                _networkStatus.value = NetworkStatus.Success
            }catch (e: Exception){
                Log.e("Failed to load latest: ", e.message.toString())
                _networkStatus.value = NetworkStatus.Failure("Failed to load latest animes!!")
            }
        }
    }

    fun getAnimeInfoById(id: Int){
        viewModelScope.launch {
            _networkStatus.value = NetworkStatus.Loading
            try {
                val response = animeApiInterface.getAnimeInfo(id = id)
                _animeInfo.value = response
                _networkStatus.value = NetworkStatus.Success
            }catch (e: Exception){
                Log.e("Load Anime Info: ", "Failed!! ${e.message}")
                _networkStatus.value = NetworkStatus.Failure("Failed to load anime info!!")
            }
        }
    }

    fun addAnimeToFavorite(favAnime: UserFavorite){
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

                    getAllFavorites()
                    _favoriteStatus.value = FavoriteStatus.Success("Added to favorite!")
                }
            }catch (e: Exception){
                Log.e("Cannot add to favorite: ", e.message.toString())
                _favoriteStatus.value = FavoriteStatus.Success("Failed to add!")
            }
        }
    }

    fun deleteFavoriteAnime(favAnime: UserFavorite){
        viewModelScope.launch {
            _favoriteStatus.value = FavoriteStatus.Loading
            try {
                val currUser = auth.currentUser
                if(currUser != null){
                    val userId = currUser.uid

                    firestore
                        .collection("favorite")
                        .document(userId)
                        .update("favorite", FieldValue.arrayRemove(favAnime))
                        .addOnSuccessListener {
                            getAllFavorites()
                            _favoriteStatus.value = FavoriteStatus.Success("Removed from favorite!")
                        }
                        .addOnFailureListener {
                            Log.e("Delete favorite:", "It's a failure!!")
                            _favoriteStatus.value = FavoriteStatus.Failure("Failed to delete!")
                        }
                }
            }catch (e: Exception){
                Log.e("Delete the favorite: ", e.message.toString())
                _favoriteStatus.value = FavoriteStatus.Failure("Failed to delete!")
            }
        }
    }

    fun getAllFavorites(){
        viewModelScope.launch {
            try {
                val currUser = auth.currentUser
                if (currUser != null){
                    val userId = currUser.uid

                    firestore
                        .collection("favorite")
                        .document(userId)
                        .get()
                        .addOnSuccessListener {task->
                            val data = task.data
                            val favoriteList = data?.get("favorite") as? List<Map<String, Any>>

                            val favorite = favoriteList?.map {mp->
                                UserFavorite(
                                    mal_id = (mp["mal_id"] as Long).toInt(),
                                    title = mp["title"] as? String ?: "",
                                    imageUrl = mp["imageUrl"] as? String ?: "",
                                    popularity = (mp["popularity"] as? Long)?.toInt() ?: 0,
                                    rank = (mp["rank"] as? Long)?.toInt() ?: 0,
                                    category = mp["category"] as? String ?: ""
                                )
                            }?: emptyList()

                            _favorites.value = favorite

                            Log.i("All Favorites: ", "Favorites fetched from Firebase: ${_favorites.value}")

                        }
                        .addOnFailureListener {
                            Log.i("Favorite: ", it.message.toString())
                        }
                }
            }catch (e: Exception){
                Log.e("Get all favorites", e.message.toString())
            }
        }
    }

    fun updateFavoriteStatus(){
        _favoriteStatus.value = FavoriteStatus.Idle
    }

    fun updateNetworkStatus(){
        _networkStatus.value = NetworkStatus.Idle
    }

}