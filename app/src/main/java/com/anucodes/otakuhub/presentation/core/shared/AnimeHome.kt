package com.anucodes.otakuhub.presentation.core.shared

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.anucodes.otakuhub.core.model.AnimeMinInfo
import com.anucodes.otakuhub.core.model.AnimeResponse
import com.anucodes.otakuhub.core.networking.viewmodel.AnimeViewModel
import com.anucodes.otakuhub.ui.theme.poppinsFamily


@Composable
fun AnimeTile(
    title: String,
    animeInfo: AnimeResponse,
    navController: NavHostController,
    animeViewModel: AnimeViewModel,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp, vertical = 10.dp)
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                fontSize = 18.sp,
                fontFamily = poppinsFamily,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )
            Spacer(modifier = modifier.width(10.dp))
            IconButton(
                colors = IconButtonDefaults.iconButtonColors(
                    contentColor = Color.White
                ),
                onClick = {

                }
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = ""
                )
            }
        }
        LazyRow{
            items(animeInfo.data){animeMinInfo->
                AnimeOutline(
                    animeMinInfo = animeMinInfo,
                    navController = navController,
                    animeViewModel = animeViewModel
                )
            }
        }

    }
}

@Composable
fun AnimeOutline(
    animeMinInfo: AnimeMinInfo,
    navController: NavHostController,
    animeViewModel: AnimeViewModel,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .padding(start = 5.dp, end = 10.dp, top = 5.dp, bottom = 5.dp),
        onClick = {
            navController.navigate("anime_details_screen")
            animeViewModel.getAnimeInfoById(animeMinInfo.mal_id)
        },
        colors = CardDefaults.outlinedCardColors(
            containerColor = Color.Transparent,
            contentColor = Color.White
        )
    ) {
        Column(
            modifier = modifier
                .padding(10.dp),
        ) {

            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(20.dp)
            ) {
                AsyncImage(
                    modifier = modifier
                        .fillMaxWidth()
                        .height(140.dp)
                        .width(100.dp)
                        .align(Alignment.CenterHorizontally),
                    contentScale = ContentScale.FillBounds,
                    model = animeMinInfo.images.webp.large_image_url,
                    contentDescription = "Anime Cover",
                )
            }

            Spacer(modifier = modifier.height(10.dp))

            Text(
                modifier = modifier
                    .fillMaxWidth(),
                text = if (animeMinInfo.title_english != null) formatTitle(animeMinInfo.title_english) else formatTitle(animeMinInfo.title),
                fontFamily = poppinsFamily,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = modifier.height(2.dp))
            RatingStars(rating = animeMinInfo.score.toDouble())
        }
    }
}

fun formatTitle(title: String): String{

    if (title.length > 20){
        val goodTitle = title.substring(0, 12)+"..."
        return goodTitle
    }else{
        return title
    }
}

