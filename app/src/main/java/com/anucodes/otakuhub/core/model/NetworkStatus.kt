package com.anucodes.otakuhub.core.model

sealed class NetworkStatus {
    object Idle: NetworkStatus()
    object Loading: NetworkStatus()
    object Success: NetworkStatus()
    data class Failure(val message: String): NetworkStatus()
}