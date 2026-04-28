package com.anucodes.otakuhub.presentation.core.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.anucodes.otakuhub.ui.theme.AppColors


@Composable
fun ExploreScreen(
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(AppColors.Background)
    ) {
        Text(
            text = "Explore Screen",
            fontSize = 25.sp,
            color = Color.White
        )
    }
}