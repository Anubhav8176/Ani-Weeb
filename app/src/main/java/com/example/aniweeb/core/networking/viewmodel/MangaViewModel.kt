package com.example.aniweeb.core.networking.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aniweeb.core.model.AnimeFullInfo
import com.example.aniweeb.core.model.MangaFullInfo
import com.example.aniweeb.core.model.MangaMinInfo
import com.example.aniweeb.core.model.MangaResponse
import com.example.aniweeb.core.networking.retrofit.AnimeApiInterface
import com.example.aniweeb.favorites.model.UserFavorite
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.Response
import javax.inject.Inject


@HiltViewModel
class MangaViewModel @Inject constructor(
    private val animeApiInterface: AnimeApiInterface,
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
):ViewModel() {

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
            try {
                val response = animeApiInterface.getTopManga("bypopularity")
                _topManga.value = response
                Log.i("The Top Manga: ", response.toString())
            }catch (e: Exception){
                Log.e("Didn't get top manga: ", e.message.toString())
            }
        }
    }

    fun getLatestManga(){
        viewModelScope.launch {
            try {
                val response = animeApiInterface.getLatestManga("publishing")
                _latestManga.value = response
                Log.i("The Latest Manga: ", response.toString())
            }catch (e: Exception){
                Log.e("Didn't get latest manga: ", e.message.toString())
            }
        }
    }

    fun getAllManga(){
        viewModelScope.launch {
            try {
                val response = animeApiInterface.getAllManga(page)
                _allManga.value += response.data
                page++
            }catch (e: Exception){
                Log.e("Didn't get all manga: ", e.message.toString())
            }
        }
    }

    fun getMangaById(id: Int){
        viewModelScope.launch {
            try {
                val response = animeApiInterface.getMangaInfo(id = id)
                _mangaInfo.value = response
                Log.i("The Manga details $id:", response.toString())
            }catch (e: Exception){
                Log.e("Didn't get manga with $id: ", e.message.toString())
            }
        }
    }

    fun addMangaToFavorite(favAnime: UserFavorite){
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
                }
            }catch (e: Exception){
                Log.e("Cannot add to favorite: ", e.message.toString())
            }
        }
    }

}