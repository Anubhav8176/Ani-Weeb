package com.anucodes.otakuhub.presentation.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.anucodes.otakuhub.authentication.viewmodel.AuthViewmodel
import com.anucodes.otakuhub.core.networking.viewmodel.AnimeViewModel
import com.anucodes.otakuhub.core.networking.viewmodel.MangaViewModel
import com.anucodes.otakuhub.presentation.favorites.presentation.FavoriteScreen
import com.anucodes.otakuhub.presentation.authentication.ui.LoginScreen
import com.anucodes.otakuhub.presentation.authentication.ui.SignUpScreen
import com.anucodes.otakuhub.presentation.authentication.ui.WelcomeScreen
import com.anucodes.otakuhub.presentation.core.details.AnimeDetailsScreen
import com.anucodes.otakuhub.presentation.core.details.MangaDetailsScreen
import com.anucodes.otakuhub.presentation.core.home.MainHomeScreen


@Composable
fun CentralNavigation(
    navController: NavHostController,
    authViewmodel: AuthViewmodel,
    animeViewModel: AnimeViewModel,
    mangaViewModel: MangaViewModel,
    startDestination: String
) {

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