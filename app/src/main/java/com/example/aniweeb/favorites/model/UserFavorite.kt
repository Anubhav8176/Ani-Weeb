package com.example.aniweeb.favorites.model


data class UserFavorite(
    val mal_id: Int = -1,
    val title: String = "",
    val popularity: Int,
    val rank: Int,
    val imageUrl: String = "",
    val category: String = ""
)
