package com.anucodes.otakuhub.presentation.core.shared

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.anucodes.otakuhub.ui.theme.OtakuHubTheme
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.getValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.anucodes.otakuhub.ui.theme.AppColors

@Composable
fun shimmerBrush(): Brush {
    val shimmerColors = listOf(
        Color(0xFF1A1A2E),
        Color(0xFF2A2A4A),
        Color(0xFF1A1A2E),
    )
    val transition = rememberInfiniteTransition(label = "shimmer")
    val translateAnim by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1200, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "shimmer_translate"
    )
    return Brush.linearGradient(
        colors = shimmerColors,
        start = Offset(translateAnim - 200f, 0f),
        end = Offset(translateAnim + 200f, 0f)
    )
}

@Composable
fun ShimmerBox(
    modifier: Modifier = Modifier,
    cornerRadius: Int = 8
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(cornerRadius.dp))
            .background(shimmerBrush())
    )
}

@Composable
fun AnimeScreenShimmer(modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(AppColors.Background),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(vertical = 12.dp)
    ) {
        item { AnimeTileShimmer() }
        item { AnimeTileShimmer() }
        items(4) {
            MainAnimeDetailRowShimmer()
        }
    }
}

@Composable
fun AnimeTileShimmer() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        ShimmerBox(
            modifier = Modifier
                .width(120.dp)
                .height(14.dp),
            cornerRadius = 6
        )
        LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            items(4) {
                AnimeCardShimmer()
            }
        }
    }
}

@Composable
fun AnimeCardShimmer() {
    Column(
        modifier = Modifier.width(120.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        ShimmerBox(
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp),
            cornerRadius = 12
        )
        ShimmerBox(
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .height(12.dp),
            cornerRadius = 6
        )
        ShimmerBox(
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .height(10.dp),
            cornerRadius = 6
        )
    }
}

@Composable
fun MainAnimeDetailRowShimmer() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        MainAnimeDetailShimmer(modifier = Modifier.weight(1f))
        MainAnimeDetailShimmer(modifier = Modifier.weight(1f))
    }
}

@Composable
fun MainAnimeDetailShimmer(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ShimmerBox(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),
            cornerRadius = 20
        )
        ShimmerBox(
            modifier = Modifier
                .fillMaxWidth()
                .height(12.dp),
            cornerRadius = 6
        )
        ShimmerBox(
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .height(12.dp),
            cornerRadius = 6
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            ShimmerBox(
                modifier = Modifier
                    .width(40.dp)
                    .height(10.dp),
                cornerRadius = 5
            )
            Row(horizontalArrangement = Arrangement.spacedBy(3.dp)) {
                repeat(5) {
                    ShimmerBox(
                        modifier = Modifier.size(10.dp),
                        cornerRadius = 2
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun ShimmerPreview(){
    OtakuHubTheme {
//        ShimmerHomePlaceHolder()
    }
}