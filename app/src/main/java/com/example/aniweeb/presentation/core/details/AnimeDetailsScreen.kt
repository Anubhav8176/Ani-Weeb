package com.example.aniweeb.presentation.core.details

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.aniweeb.core.networking.viewmodel.AnimeViewModel
import com.example.aniweeb.favorites.model.UserFavorite
import com.example.aniweeb.ui.theme.poppinsFamily

@Composable
fun AnimeDetailsScreen(
    animeViewModel: AnimeViewModel,
    modifier: Modifier = Modifier
) {

    val animeInfo by animeViewModel.animeInfo.collectAsState()

    Box(
        modifier = modifier
            .fillMaxSize()
    ){
        Column(
            modifier = modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = modifier
                    .fillMaxHeight(0.3f)
                    .fillMaxWidth()
                    .background(color = Color.Red)
            ){

            }

            Column(
                modifier = modifier
                    .fillMaxHeight(0.7f)
                    .fillMaxWidth()
            ){

            }
        }

        Column(
            modifier = modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = modifier.weight(2F))
            AsyncImage(
                model = animeInfo?.data?.images?.webp?.large_image_url,
                contentDescription = "Anime Cover",
                modifier = modifier
                    .height(300.dp)
                    .width(200.dp)
                    .clip(RoundedCornerShape(20.dp)),
                contentScale = ContentScale.FillBounds
            )

            Spacer(modifier.height(10.dp))

            (if (animeInfo?.data?.title_english != null) animeInfo?.data?.title_english else animeInfo?.data?.title)?.let {
                Text(
                    modifier = modifier
                        .padding(10.dp),
                    text = it,
                    fontFamily = poppinsFamily,
                    fontSize = 23.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }
            Spacer(modifier.height(10.dp))
            Text(
                text = "Score ${animeInfo?.data?.score}",
                fontSize = 18.sp,
                fontFamily = poppinsFamily
            )
            Spacer(modifier = modifier.height(10.dp))

            Row(
                modifier = modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                animeInfo?.data?.season?.let {
                    Text(
                        fontSize = 18.sp,
                        text = it,
                        fontFamily = poppinsFamily
                    )
                }
                Text(
                    text = "-${animeInfo?.data?.year}",
                    fontSize = 18.sp,
                    fontFamily = poppinsFamily
                )
            }
            Spacer(modifier.height(10.dp))
            Button(
                modifier = modifier
                    .fillMaxWidth(0.8f),
                shape = RoundedCornerShape(10.dp),
                border = BorderStroke(width = 1.dp, color = Color.Black),
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.Black,
                    containerColor = Color.White
                ),
                elevation = ButtonDefaults.elevatedButtonElevation(10.dp),
                onClick = {
                    val favoriteAnime = UserFavorite(
                        mal_id = animeInfo?.data?.mal_id!!,
                        title = animeInfo?.data?.title!!,
                        imageUrl = animeInfo?.data?.images?.webp?.large_image_url!!,
                        category = "anime"
                    )
                    animeViewModel.addAnimeToFavorite(favoriteAnime)
                }
            ) {
                Text(
                    text = "Add to Favorites",
                    fontSize = 18.sp,
                    fontFamily = poppinsFamily
                )
            }

            Spacer(modifier = modifier.weight(8F))
        }
    }

}