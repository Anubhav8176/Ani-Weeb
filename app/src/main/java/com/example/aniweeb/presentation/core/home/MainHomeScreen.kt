package com.example.aniweeb.presentation.core.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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

    Scaffold(
        topBar = {
            TopAppBar(
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
    {innerpadding->
        NavHost(
            navController = bottomNavController,
            startDestination = BottomNavigationItems.Anime.route
        ) {
            composable(BottomNavigationItems.Anime.route) {
                AnimeScreen(
                    navController = navController,
                    animeViewModel = animeViewModel,
                    modifier = modifier.padding(innerpadding)
                )
            }

            composable(
                BottomNavigationItems.Manga.route
            ) {
                MangaHomeScreen(
                    navController = navController,
                    mangaViewModel = mangaViewModel,
                    modifier = modifier.padding(innerpadding)
                )
            }

            composable(BottomNavigationItems.Profile.route) {
                ProfileScreen(
                    navController = navController,
                    authViewmodel = authViewmodel
                )
            }
        }

    }
}