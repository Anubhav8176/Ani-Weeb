package com.anucodes.otakuhub.presentation.core.bottomnavigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Animation
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Person2
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavigationItems (
    val route: String,
    val icon: ImageVector,
    val label: String
){
    object Anime: BottomNavigationItems("Anime", Icons.Default.Animation, "Anime")
    object Manga: BottomNavigationItems("Manga", Icons.Default.Book, "Manga")
    object Explore: BottomNavigationItems("Explore", Icons.Default.Search, "Explore")
    object Profile: BottomNavigationItems("Profile", Icons.Default.Person2, "Profile")
}