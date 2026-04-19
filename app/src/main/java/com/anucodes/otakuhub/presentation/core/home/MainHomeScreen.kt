package com.anucodes.otakuhub.presentation.core.home

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.anucodes.otakuhub.authentication.viewmodel.AuthViewmodel
import com.anucodes.otakuhub.core.networking.viewmodel.AnimeViewModel
import com.anucodes.otakuhub.core.networking.viewmodel.MangaViewModel
import com.anucodes.otakuhub.presentation.core.bottomnavigation.BottomNavigationBar
import com.anucodes.otakuhub.presentation.core.bottomnavigation.BottomNavigationItems
import com.anucodes.otakuhub.ui.theme.AppColors


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainHomeScreen(
    navController: NavHostController,
    animeViewModel: AnimeViewModel,
    mangaViewModel: MangaViewModel,
    authViewmodel: AuthViewmodel,
    modifier: Modifier = Modifier
) {

    val bottomNavController = rememberNavController()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.Background)
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "OtakuHub",
                            fontSize = 25.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = AppColors.Background,
                        titleContentColor = Color.White
                    )
                )
            },
            bottomBar = {
                BottomNavigationBar(
                    navController = bottomNavController
                )
            }
        )
        { innerpadding ->
            NavHost(
                navController = bottomNavController,
                startDestination = BottomNavigationItems.Anime.route
            ) {
                composable(
                    route = BottomNavigationItems.Anime.route,
                    enterTransition = {
                        scaleIn(
                            initialScale = 0.6f,
                            animationSpec = tween(1000)
                        ) + fadeIn(animationSpec = tween(1500))
                    }
                ) {
                    AnimeScreen(
                        navController = navController,
                        animeViewModel = animeViewModel,
                        modifier = modifier.padding(innerpadding)
                    )
                }

                composable(
                    route = BottomNavigationItems.Manga.route,
                    enterTransition = {
                        scaleIn(
                            initialScale = 0.6f,
                            animationSpec = tween(1000)
                        ) + fadeIn(animationSpec = tween(1500))
                    }
                ) {
                    MangaHomeScreen(
                        navController = navController,
                        mangaViewModel = mangaViewModel,
                        modifier = modifier.padding(innerpadding)
                    )
                }

                composable(
                    route = BottomNavigationItems.Explore.route,
                    enterTransition = {
                        scaleIn(
                            initialScale = 0.6f,
                            animationSpec = tween(1000)
                        ) + fadeIn(animationSpec = tween(1500))
                    }
                ) {
                    ExploreScreen()
                }


                composable(
                    route = BottomNavigationItems.Profile.route,
                    enterTransition = {
                        scaleIn(
                            initialScale = 0.6f,
                            animationSpec = tween(1000)
                        ) + fadeIn(animationSpec = tween(1500))
                    }
                ) {
                    ProfileScreen(
                        navController = navController,
                        authViewmodel = authViewmodel,
                        animeViewModel = animeViewModel,
                        modifier = modifier.padding(innerpadding)
                    )
                }
            }

        }
    }
}