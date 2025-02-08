package com.example.aniweeb.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.aniweeb.authentication.viewmodel.AuthViewmodel
import com.example.aniweeb.core.networking.viewmodel.AnimeViewModel
import com.example.aniweeb.core.networking.viewmodel.MangaViewModel
import com.example.aniweeb.favorites.presentation.FavoriteScreen
import com.example.aniweeb.presentation.authentication.ui.LoginScreen
import com.example.aniweeb.presentation.authentication.ui.SignUpScreen
import com.example.aniweeb.presentation.authentication.ui.WelcomeScreen
import com.example.aniweeb.presentation.core.details.AnimeDetailsScreen
import com.example.aniweeb.presentation.core.details.MangaDetailsScreen
import com.example.aniweeb.presentation.core.home.MainHomeScreen
import com.example.aniweeb.presentation.core.home.ProfileScreen


@Composable
fun CentralNavigation(
    navController: NavHostController,
    authViewmodel: AuthViewmodel,
    animeViewModel: AnimeViewModel,
    mangaViewModel: MangaViewModel
) {

    val isAuthenticated by authViewmodel.isAuthenticated.collectAsState()

    val startDestination = if (!isAuthenticated){
        "auth_graph"
    }else{
        "home_graph"
    }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ){

        navigation(
            startDestination = "welcome_screen",
            route = "auth_graph"
        ){

            composable(route = "welcome_screen") {
                WelcomeScreen(
                    navController = navController,
                    modifier = Modifier
                )
            }

            composable(route = "login_screen") {
                LoginScreen(navController, authViewmodel)
            }

            composable(route = "signup_screen") {
                SignUpScreen(navController, authViewmodel)
            }
        }

        navigation(
            startDestination = "home_screen",
            route = "home_graph"
        ){
            composable("home_screen") {
                MainHomeScreen(
                    navController = navController,
                    animeViewModel = animeViewModel,
                    mangaViewModel = mangaViewModel,
                    authViewmodel = authViewmodel
                )
            }

            composable("manga_details_screen") {
                MangaDetailsScreen(mangaViewModel = mangaViewModel)
            }

            composable("anime_details_screen") {
                AnimeDetailsScreen(animeViewModel = animeViewModel)
            }

        }
    }
}