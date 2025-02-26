package com.example.aniweeb.presentation.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
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

            composable(
                route = "welcome_screen",
                enterTransition = {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(1000)
                    )
                },
                exitTransition = {
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(1000)
                    )
                }
            ) {
                WelcomeScreen(
                    navController = navController,
                    modifier = Modifier
                )
            }

            composable(
                route = "login_screen",
                enterTransition = {
                    scaleIn(
                        initialScale = 0.6f,
                        animationSpec = tween(1000)
                    ) + fadeIn(animationSpec = tween(1500))
                },
                exitTransition = {
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(1000)
                    )
                }
            ) {
                LoginScreen(navController, authViewmodel)
            }

            composable(
                route = "signup_screen",
                enterTransition = {
                    scaleIn(
                        initialScale = 0.6f,
                        animationSpec = tween(1000)
                    ) + fadeIn(animationSpec = tween(1500))
                },
                exitTransition = {
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(1000)
                    )
                }
            ) {
                SignUpScreen(navController, authViewmodel)
            }
        }

        navigation(
            startDestination = "home_screen",
            route = "home_graph"
        ){
            composable(
                route = "home_screen"
            ) {
                MainHomeScreen(
                    navController = navController,
                    animeViewModel = animeViewModel,
                    mangaViewModel = mangaViewModel,
                    authViewmodel = authViewmodel
                )
            }

            composable(
                route = "manga_details_screen",
                enterTransition = {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(1000)
                    )
                },
                exitTransition = {
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(1000)
                    )
                }
            ) {
                MangaDetailsScreen(mangaViewModel = mangaViewModel)
            }

            composable(
                route = "anime_details_screen",
                enterTransition = {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(1000)
                    )
                },
                exitTransition = {
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(1000)
                    )
                }
            ) {
                AnimeDetailsScreen(animeViewModel = animeViewModel)
            }


            composable(
                route = "favorite_screen",
                enterTransition = {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(1000)
                    )
                },
                exitTransition = {
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(1000)
                    )
                }
            ) {
                FavoriteScreen(
                    animeViewModel = animeViewModel,
                    mangaViewModel = mangaViewModel,
                    navController = navController
                )
            }

        }
    }
}