package com.example.aniweeb.presentation.core.details

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
    var isImageLoading by remember { mutableStateOf(true) }

    if (isImageLoading){
        CircularProgressIndicator(
            color = Color.DarkGray,
            modifier = modifier
                .size(30.dp)
        )
    }

    Box(
        modifier = modifier
            .fillMaxSize()
    ){
        Column(
            modifier = modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Column(
                modifier = modifier
                    .fillMaxHeight(0.25f)
                    .fillMaxWidth()
                    .background(color = Color.Magenta)
            ){

            }
        }

        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = modifier.height(50.dp))
            AsyncImage(
                model = animeInfo?.data?.images?.webp?.large_image_url,
                contentDescription = "Anime Cover",
                modifier = modifier
                    .height(300.dp)
                    .width(200.dp)
                    .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(20.dp))
                    .clip(RoundedCornerShape(20.dp)),
                contentScale = ContentScale.FillBounds,
                onLoading = {
                    isImageLoading = false
                }
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

            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Text(
                    text = "${animeInfo?.data?.status}",
                    fontSize = 18.sp,
                    fontFamily = poppinsFamily
                )

                Spacer(modifier.weight(1F))

                animeInfo?.data?.type?.let {
                    Text(
                        text = it,
                        fontSize = 18.sp,
                        fontFamily = poppinsFamily
                    )
                }
            }
            Spacer(modifier.height(10.dp))

            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
            ) {
                Text(
                    text = "Score: ${animeInfo?.data?.score}",
                    fontSize = 18.sp,
                    fontFamily = poppinsFamily
                )

                Spacer(modifier.weight(1f))

                Text(
                    text = "Source: ${animeInfo?.data?.source}",
                    fontFamily = poppinsFamily,
                    fontSize = 18.sp
                )
            }
            Spacer(modifier = modifier.height(10.dp))

            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.Event,
                    contentDescription = "Season",
                    tint = Color(0xFF6200EE)
                )
                Spacer(modifier.width(6.dp))
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

                Spacer(modifier.weight(1f))

                Row {
                    Icon(
                        imageVector = Icons.Filled.Movie,
                        contentDescription = "episodes",
                        tint = Color.Blue
                    )
                    Spacer(modifier.width(6.dp))
                    Text(
                        text = "Episodes: ${animeInfo?.data?.episodes}",
                        fontSize = 18.sp,
                        fontFamily = poppinsFamily
                    )
                }
            }
            Spacer(modifier.height(10.dp))

            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
            ){
                Icon(
                    imageVector = Icons.Default.TrendingUp,
                    contentDescription = "Popularity",
                    tint = Color.Red
                )
                Spacer(modifier.width(5.dp))
                Text(
                    text = animeInfo?.data?.popularity.toString(),
                    fontFamily = poppinsFamily,
                    fontSize = 18.sp
                )

                Spacer(modifier.weight(1F))

                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "Ranking",
                    tint = Color.Yellow
                )
                Spacer(modifier.width(5.dp))
                Text(
                    text = animeInfo?.data?.rank.toString(),
                    fontSize = 18.sp,
                    fontFamily = poppinsFamily
                )
            }

            Spacer(modifier.height(10.dp))


            animeInfo?.data?.duration?.let {
                Text(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp),
                    text = it,
                    fontSize = 18.sp,
                    fontFamily = poppinsFamily,
                    textAlign = TextAlign.Left
                )
            }

            Spacer(modifier.height(10.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            ){
                Divider(
                    modifier = modifier
                        .weight(1f)
                        .padding(end = 8.dp),
                    color = Color.Black
                )
                Text(
                    text = "Description",
                    fontSize = 18.sp,
                    fontFamily = poppinsFamily,
                    color = Color.Black,

                    )
                Divider(
                    modifier = modifier
                        .weight(1f)
                        .padding(start = 8.dp),
                    color = Color.Black
                )
            }

            Spacer(modifier.height(10.dp))


            animeInfo?.data?.synopsis?.let {
                Text(
                    modifier = modifier
                        .padding(horizontal = 10.dp),
                    text = it,
                    fontSize = 18.sp,
                    fontFamily = poppinsFamily,
                    textAlign = TextAlign.Left
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
                        popularity = animeInfo?.data?.popularity!!,
                        rank = animeInfo?.data?.rank!!,
                        category = "anime"
                    )
                    animeViewModel.addAnimeToFavorite(favoriteAnime)
                }
            ) {
                Row {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Favorite",
                        tint = Color.Red
                    )
                    Spacer(modifier.width(5.dp))
                    Text(
                        text = "Add to Favorites",
                        fontSize = 18.sp,
                        fontFamily = poppinsFamily
                    )
                }
            }

            Spacer(modifier = modifier.height(50.dp))
        }
    }

}