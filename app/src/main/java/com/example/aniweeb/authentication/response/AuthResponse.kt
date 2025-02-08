package com.example.aniweeb.authentication.response

sealed class AuthResponse {
    object Idle: AuthResponse()
    object Loading: AuthResponse()
    object Success: AuthResponse()
    data class Failure(val message: String): AuthResponse()
}