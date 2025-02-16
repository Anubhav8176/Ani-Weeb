package com.example.aniweeb.presentation.core.home

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.example.aniweeb.core.model.AnimeMinInfo
import com.example.aniweeb.core.model.AnimeResponse
import com.example.aniweeb.core.networking.viewmodel.AnimeViewModel
import com.example.aniweeb.ui.theme.poppinsFamily


@Composable
fun AnimeScreen(
    navController: NavHostController,
    animeViewModel: AnimeViewModel,
    modifier: Modifier = Modifier
) {

    val topAnime by animeViewModel.topAnime.collectAsState()
    val latestAnime by animeViewModel.latestAnime.collectAsState()
    val allAnime by animeViewModel.allAnime.collectAsState()

    LaunchedEffect (Unit){
        animeViewModel.getTopAnime()
        animeViewModel.getLatestAnime()
        animeViewModel.getAnime()
    }

    LazyColumn (
        modifier = modifier
            .fillMaxSize()
    ){
        item {
            topAnime?.let {
                HomeSection(
                    title = "Most Popular",
                    animeInfo = it,
                    navController = navController,
                    animeViewModel = animeViewModel
                )
            }
        }

        item {
            latestAnime?.let {
                HomeSection(
                    title = "Latest",
                    animeInfo = it,
                    navController = navController,
                    animeViewModel = animeViewModel
                )
            }
        }

        itemsIndexed(allAnime){index, animeMinInfo->
            MainAnimeDetail(
                animeMinInfo = animeMinInfo,
                navController = navController,
                animeViewModel = animeViewModel
            )

            if (index == allAnime.size-5){
                animeViewModel.getAnime()
            }
        }
    }

}

@Composable
fun MainAnimeDetail(
    animeMinInfo: AnimeMinInfo,
    navController: NavHostController,
    animeViewModel: AnimeViewModel,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .padding(horizontal = 15.dp, vertical = 8.dp),
        elevation = CardDefaults.elevatedCardElevation(15.dp),
        border = BorderStroke(width = 1.dp, color = Color.Black),
        shape = RoundedCornerShape(20.dp),
        onClick = {
            navController.navigate("anime_details_screen")
            animeViewModel.getAnimeInfoById(animeMinInfo.mal_id)
        }
    ){
        Column(
            modifier = modifier
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Card(
                shape = RoundedCornerShape(20.dp)
            ){
                AsyncImage(
                    modifier = modifier
                        .align(Alignment.CenterHorizontally)
                        .height(500.dp),
                    contentScale = ContentScale.FillBounds,
                    model = animeMinInfo.images.webp.large_image_url,
                    contentDescription = "Anime Main Cover"
                )
            }
            Spacer(modifier.height(10.dp))
            Text(
                modifier = modifier
                    .fillMaxWidth(),
                text = if (animeMinInfo.title_english != null) animeMinInfo.title_english else animeMinInfo.title,
                fontFamily = poppinsFamily,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Spacer(modifier.height(5.dp))

            Text(
                text = animeMinInfo.type,
                fontSize = 17.sp,
                fontFamily = poppinsFamily,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = modifier.height(10.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Text(
                    text = "Rating: ${animeMinInfo.score}",
                    fontSize = 17.sp,
                    fontFamily = poppinsFamily,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier.width(25.dp))

                Text(
                    text = "Episodes: ${animeMinInfo.episodes}",
                    fontSize = 17.sp,
                    fontFamily = poppinsFamily,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier.height(10.dp))
            Text(
                text = if (animeMinInfo.status == "Finished Airing") "Finished" else "Ongoing",
                fontFamily = poppinsFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 17.sp
            )
        }
    }
}

@Composable
fun HomeSection(
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
                fontFamily = poppinsFamily
            )
            Spacer(modifier = modifier.width(10.dp))
            IconButton(
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
        elevation = CardDefaults.elevatedCardElevation(10.dp),
        onClick = {
            navController.navigate("anime_details_screen")
            animeViewModel.getAnimeInfoById(animeMinInfo.mal_id)
        }
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
                    modifier = modifier
                        .height(200.dp)
                        .width(150.dp)
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
            Text(
                text = "Rated: ${animeMinInfo.score}",
                fontFamily = poppinsFamily,
                fontSize = 13.sp
            )
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