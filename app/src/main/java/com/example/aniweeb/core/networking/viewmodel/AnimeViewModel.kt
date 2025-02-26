package com.example.aniweeb.core.networking.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aniweeb.core.model.AnimeFullInfo
import com.example.aniweeb.core.model.AnimeMinInfo
import com.example.aniweeb.core.model.AnimeResponse
import com.example.aniweeb.core.networking.retrofit.AnimeApiInterface
import com.example.aniweeb.favorites.model.UserFavorite
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.auth.User
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

    init {
        getAllFavorites()
    }

    fun getAnime(){
        viewModelScope.launch {
            try {
                val response = animeApiInterface.getAllAnime(page = page)
                _allAnime.value += response.data
                page++
            }catch (e: Exception){
                Log.e("Failed to load data: ", e.message.toString())
            }
        }
    }

    fun getTopAnime(){
        viewModelScope.launch {
            try {
                val response = animeApiInterface.getTopAnime("bypopularity")
                _topAnime.value = response
//                Log.i("The data: ", response.toString())
            }catch (e: Exception){
                Log.e("Failed to load data: ", e.message.toString())
            }
        }
    }

    fun getLatestAnime(){
        viewModelScope.launch {
            try {
                val response = animeApiInterface.getLatestAnime("airing")
                _latestAnime.value = response
//                Log.i("The Latest anime: ", response.toString())
            }catch (e: Exception){
                Log.e("Failed to load latest: ", e.message.toString())
            }
        }
    }

    fun getAnimeInfoById(id: Int){
        viewModelScope.launch {
            try {
                val response = animeApiInterface.getAnimeInfo(id = id)
                _animeInfo.value = response
                Log.i("The Anime data for ${id}", response.toString())
            }catch (e: Exception){
                Log.e("Load Anime Info: ", "Failed!! ${e.message}")
            }
        }
    }

    fun addAnimeToFavorite(favAnime: UserFavorite){
        viewModelScope.launch {
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
                }
            }catch (e: Exception){
                Log.e("Cannot add to favorite: ", e.message.toString())
            }
        }
    }

    fun deleteFavoriteAnime(favAnime: UserFavorite){
        viewModelScope.launch {
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
                            Log.i("Delete favorite: ", "It's a success!!")
                        }
                        .addOnFailureListener {
                            Log.e("Delete favorite:", "It's a failure!!")
                        }
                }
            }catch (e: Exception){
                Log.e("Delete the favorite: ", e.message.toString())
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

}