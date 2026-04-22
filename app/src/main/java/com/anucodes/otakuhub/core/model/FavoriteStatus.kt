package com.anucodes.otakuhub.core.model

sealed class FavoriteStatus {
    object Loading: FavoriteStatus()
    data class Success(val message: String): FavoriteStatus()
    object Idle: FavoriteStatus()
    data class Failure(val message: String): FavoriteStatus()
}