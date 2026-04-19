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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.anucodes.otakuhub.core.model.MangaMinInfo
import com.anucodes.otakuhub.core.model.MangaResponse
import com.anucodes.otakuhub.core.networking.viewmodel.MangaViewModel
import com.anucodes.otakuhub.ui.theme.poppinsFamily


@Composable
fun HomeMangaSection(
    title: String,
    mangaInfo: MangaResponse,
    navController: NavHostController,
    mangaViewModel: MangaViewModel,
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
            items(mangaInfo.data){mangaMinInfo->
                MangaOutline(
                    mangaMinInfo = mangaMinInfo,
                    navController = navController,
                    mangaViewModel = mangaViewModel
                )
            }
        }

    }
}

@Composable
fun MangaOutline(
    mangaMinInfo: MangaMinInfo,
    navController: NavHostController,
    mangaViewModel: MangaViewModel,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .padding(start = 5.dp, end = 10.dp, top = 5.dp, bottom = 5.dp),
        onClick = {
            navController.navigate("manga_details_screen")
            mangaViewModel.getMangaById(mangaMinInfo.mal_id)
        },
        colors = CardDefaults.outlinedCardColors(
            containerColor = Color.Transparent,
            contentColor = Color.White
        )
    ) {
        Column(
            modifier = modifier
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                shape = RoundedCornerShape(20.dp)
            ) {
                AsyncImage(
                    model = mangaMinInfo.images.webp.large_image_url,
                    contentDescription = "Manga Cover",
                    modifier = Modifier
                        .height(140.dp)
                        .width(100.dp)
                        .clip(RoundedCornerShape(20.dp)),
                    contentScale = ContentScale.FillBounds
                )
            }

            Spacer(modifier = modifier.height(10.dp))
            Text(
                modifier = modifier
                    .fillMaxWidth(),
                text = if (mangaMinInfo.title_english != null) formatTitle(mangaMinInfo.title_english) else formatTitle(mangaMinInfo.title),
                fontFamily = poppinsFamily,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = modifier.height(2.dp))
            RatingStars(rating = mangaMinInfo.score.toDouble())
        }
    }
}