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
import com.example.aniweeb.core.networking.viewmodel.MangaViewModel
import com.example.aniweeb.favorites.model.UserFavorite
import com.example.aniweeb.ui.theme.poppinsFamily


@Composable
fun MangaDetailsScreen(
    mangaViewModel: MangaViewModel,
    modifier: Modifier = Modifier
) {

    val mangaInfo by mangaViewModel.mangaInfo.collectAsState()

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
                model = mangaInfo?.data?.images?.webp?.large_image_url,
                contentDescription = "Anime Cover",
                modifier = modifier
                    .height(300.dp)
                    .width(200.dp)
                    .clip(RoundedCornerShape(20.dp)),
                contentScale = ContentScale.FillBounds
            )

            Spacer(modifier.height(10.dp))

            (if (mangaInfo?.data?.title_english != null) mangaInfo?.data?.title_english else mangaInfo?.data?.title)?.let {
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
                text = "Score ${mangaInfo?.data?.score}",
                fontSize = 18.sp,
                fontFamily = poppinsFamily
            )
            Spacer(modifier = modifier.height(10.dp))

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
                    val favManga = UserFavorite(
                        mal_id = mangaInfo?.data?.mal_id!!,
                        title = mangaInfo?.data?.title!!,
                        imageUrl = mangaInfo?.data?.images?.webp?.large_image_url!!,
                        category = "manga"
                    )

                    mangaViewModel.addMangaToFavorite(favManga)
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