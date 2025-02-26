package com.example.aniweeb.presentation.core.home

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.aniweeb.authentication.viewmodel.AuthViewmodel
import com.example.aniweeb.core.networking.viewmodel.AnimeViewModel
import com.example.aniweeb.core.networking.viewmodel.MangaViewModel
import com.example.aniweeb.presentation.core.bottomnavigation.BottomNavigationBar
import com.example.aniweeb.presentation.core.bottomnavigation.BottomNavigationItems
import com.example.aniweeb.ui.theme.pacificoFamily


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
            .background(
                Brush.linearGradient(
                    colors = listOf(Color(0xFF4A00E0), Color(0xFF8E2DE2)) // Purple to Blue gradient
                )
            )
    ) {
        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                TopAppBar(
                    modifier = modifier
                        .clip(RoundedCornerShape(bottomEnd = 20.dp, bottomStart = 20.dp))
                        .border(
                            width = 1.dp,
                            color = Color.Black,
                            shape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp)
                        ),
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Gray,
                        titleContentColor = Color.White
                    ),
                    title = {
                        Text(
                            text = "AniWeeb",
                            fontSize = 25.sp,
                            fontFamily = pacificoFamily
                        )
                    }
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