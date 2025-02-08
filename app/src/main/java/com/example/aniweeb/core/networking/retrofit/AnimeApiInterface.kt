package com.example.aniweeb.core.networking.retrofit

import com.example.aniweeb.core.model.AnimeFullInfo
import com.example.aniweeb.core.model.AnimeResponse
import com.example.aniweeb.core.model.MangaFullInfo
import com.example.aniweeb.core.model.MangaResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AnimeApiInterface {

    //Anime EndPoints.
    @GET("top/anime")
    suspend fun getTopAnime(
        @Query("filter") filter: String
    ): AnimeResponse

    @GET("top/anime")
    suspend fun getLatestAnime(
        @Query("filter") filter: String
    ):AnimeResponse


    @GET("anime")
    suspend fun getAllAnime(
        @Query("page") page: Int
    ): AnimeResponse


    //Manga Endpoints
    @GET("top/manga")
    suspend fun getTopManga(
        @Query("filter") filter: String
    ): MangaResponse

    @GET("top/manga")
    suspend fun getLatestManga(
        @Query("filter") filter: String
    ):MangaResponse


    @GET("manga")
    suspend fun getAllManga(
        @Query("page") page: Int
    ): MangaResponse


    @GET("anime/{id}/full")
    suspend fun getAnimeInfo(
        @Path("id") id: Int
    ): AnimeFullInfo

    @GET("manga/{id}/full")
    suspend fun getMangaInfo(
        @Path("id") id: Int
    ): MangaFullInfo
}