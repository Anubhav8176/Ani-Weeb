package com.example.aniweeb

import android.app.Activity
import android.app.StatusBarManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.example.aniweeb.authentication.viewmodel.AuthViewmodel
import com.example.aniweeb.core.networking.viewmodel.AnimeViewModel
import com.example.aniweeb.core.networking.viewmodel.MangaViewModel
import com.example.aniweeb.presentation.navigation.CentralNavigation
import com.example.aniweeb.ui.theme.AniWeebTheme
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

            AniWeebTheme {
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
