package com.example.aniweeb.presentation.core.details

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.scrollable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Article
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LibraryBooks
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.style.TextDecoration
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
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = modifier.height(50.dp))
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
                    text = mangaInfo?.data?.popularity.toString(),
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
                    text = mangaInfo?.data?.rank.toString(),
                    fontSize = 18.sp,
                    fontFamily = poppinsFamily
                )
            }

            Spacer(modifier.height(10.dp))

            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
            ) {

                Icon(
                    imageVector = Icons.Default.LibraryBooks,
                    contentDescription = "Chapters",
                    tint = Color.Blue
                )
                Spacer(modifier.width(10.dp))
                if (mangaInfo?.data?.volumes != null){
                    Text(
                        text = mangaInfo?.data?.volumes.toString(),
                        fontFamily = poppinsFamily,
                        fontSize = 18.sp
                    )
                }else{
                    Text(
                        text = "Ongoing",
                        fontFamily = poppinsFamily,
                        fontSize = 18.sp
                    )
                }

                Spacer(modifier.weight(1F))

                Icon(
                    imageVector = Icons.Default.Article,
                    contentDescription = "Chapters",
                    tint = Color.Black
                )
                Spacer(modifier.width(10.dp))
                Text(
                    text = mangaInfo?.data?.chapters.toString(),
                    fontFamily = poppinsFamily,
                    fontSize = 18.sp
                )
            }

            mangaInfo?.data?.published?.string.let {
                Text(
                    text = it.toString(),
                    fontSize = 18.sp,
                    fontFamily = poppinsFamily
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
                    fontWeight = FontWeight.Bold,
                    textDecoration = TextDecoration.Underline
                )
                Divider(
                    modifier = modifier
                        .weight(1f)
                        .padding(start = 8.dp),
                    color = Color.Black
                )
            }

            Spacer(modifier.height(10.dp))

            mangaInfo?.data?.synopsis?.let {
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
                    text = "Author",
                    fontSize = 18.sp,
                    fontFamily = poppinsFamily,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    textDecoration = TextDecoration.Underline
                )
                Divider(
                    modifier = modifier
                        .weight(1f)
                        .padding(start = 8.dp),
                    color = Color.Black
                )
            }

            mangaInfo?.data?.authors?.forEach{author->
                Column(
                    modifier = modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(15.dp))
                        .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(15.dp))
                        .padding(horizontal = 15.dp, vertical = 5.dp)
                ) {
                    Text(
                        text = author.name,
                        fontSize = 18.sp,
                        fontFamily = poppinsFamily
                    )
                    Text(
                        text = author.type,
                        fontSize = 18.sp,
                        fontFamily = poppinsFamily
                    )
                    Text(
                        text = "more..",
                        fontSize = 18.sp,
                        fontFamily = poppinsFamily
                    )
                }
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
                    val favManga = UserFavorite(
                        mal_id = mangaInfo?.data?.mal_id!!,
                        title = mangaInfo?.data?.title!!,
                        imageUrl = mangaInfo?.data?.images?.webp?.large_image_url!!,
                        category = "manga"
                    )

                    mangaViewModel.addMangaToFavorite(favManga)
                }
            ) {
                Row {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Favorite!",
                        tint = Color.Red
                    )
                    Spacer(modifier = modifier.width(10.dp))
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