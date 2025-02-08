package com.example.aniweeb.favorites.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.example.aniweeb.core.networking.viewmodel.AnimeViewModel
import com.example.aniweeb.ui.theme.poppinsFamily

@Composable
fun FavoriteScreen(
    animeViewModel: AnimeViewModel,
    modifier: Modifier = Modifier
) {

    val favorite by animeViewModel.favorites.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
          Text(
              text = favorite.toString(),
              fontSize = 18.sp,
              fontFamily = poppinsFamily
          )
    }
}