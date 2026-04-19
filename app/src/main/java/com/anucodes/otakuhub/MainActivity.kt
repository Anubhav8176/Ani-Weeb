package com.anucodes.otakuhub

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.anucodes.otakuhub.authentication.viewmodel.AuthViewmodel
import com.anucodes.otakuhub.core.networking.viewmodel.AnimeViewModel
import com.anucodes.otakuhub.core.networking.viewmodel.MangaViewModel
import com.anucodes.otakuhub.presentation.navigation.CentralNavigation
import com.anucodes.otakuhub.ui.theme.OtakuHubTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val authViewmodel by viewModels<AuthViewmodel>()
    private val animeViewModel by viewModels<AnimeViewModel>()
    private val mangaViewModel by viewModels<MangaViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {

        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        splashScreen.setKeepOnScreenCondition{true}

        enableEdgeToEdge()

        lifecycleScope.launch {
            animeViewModel.getTopAnime()
            animeViewModel.getLatestAnime()
            animeViewModel.getAnime()

            delay(5000L)

            mangaViewModel.getAllManga()
            mangaViewModel.getTopManga()
            mangaViewModel.getLatestManga()
            splashScreen.setKeepOnScreenCondition{false}
        }


        setContent {
            val navController = rememberNavController()

            OtakuHubTheme {
                CentralNavigation(
                    navController = navController,
                    authViewmodel = authViewmodel,
                    animeViewModel = animeViewModel,
                    mangaViewModel = mangaViewModel
                )
            }
        }
    }
}
