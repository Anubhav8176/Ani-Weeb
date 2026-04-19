package com.anucodes.otakuhub.presentation.core.shared

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.anucodes.otakuhub.ui.theme.AppColors
import com.anucodes.otakuhub.ui.theme.OtakuHubTheme


@Composable
fun RatingStars(
    rating: Double,
    maxStars: Int = 5,
    starSize: Dp = 15.dp
) {
    Row {
        repeat(maxStars) { index ->
            val starFill = when {
                rating >= (index + 1) * 2.0 -> 1f
                rating <= index * 2.0 -> 0f
                else -> ((rating - index * 2.0) / 2.0).toFloat()
            }
            PartialStar(
                fillPercent = starFill,
                size = starSize
            )
        }
    }
}

@Composable
fun PartialStar(
    fillPercent: Float,
    filledColor: Color = AppColors.Warning,
    unfilledColor: Color = Color.Gray,
    size: Dp
) {
    val sizePx = with(LocalDensity.current) { size.toPx() }

    Box(contentAlignment = Alignment.Center) {
        Icon(
            imageVector = Icons.Filled.Star,
            contentDescription = "Star",
            tint = unfilledColor,
            modifier = Modifier.size(size)
        )
        Box(
            modifier = Modifier
                .size(size)
                .drawWithContent {
                    clipRect(
                        left = 0f,
                        top = 0f,
                        right = sizePx * fillPercent,
                        bottom = sizePx
                    ) {
                        this@drawWithContent.drawContent()
                    }
                }
        ) {
            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = null,
                tint = filledColor,
                modifier = Modifier.size(size)
            )
        }
    }
}