package com.anucodes.otakuhub

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
        setContent {
            val navController = rememberNavController()
            val isAuthenticated by authViewmodel.isAuthenticated.collectAsState()

            val startDestination = if (!isAuthenticated){
                "auth_graph"
            }else{
                "home_graph"
            }

            splashScreen.setKeepOnScreenCondition {false}

            OtakuHubTheme {
                CentralNavigation(
                    navController = navController,
                    authViewmodel = authViewmodel,
                    animeViewModel = animeViewModel,
                    mangaViewModel = mangaViewModel,
                    startDestination = startDestination
                )
            }
        }
    }
}
