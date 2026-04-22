package com.anucodes.otakuhub.presentation.favorites.model


data class UserFavorite(
    val mal_id: Int = -1,
    val title: String = "",
    val popularity: Int,
    val rank: Int,
    val imageUrl: String = "",
    val category: String = ""
)
