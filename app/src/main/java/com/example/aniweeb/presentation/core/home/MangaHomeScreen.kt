package com.example.aniweeb.presentation.core.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.example.aniweeb.core.model.MangaMinInfo
import com.example.aniweeb.core.model.MangaResponse
import com.example.aniweeb.core.networking.viewmodel.MangaViewModel
import com.example.aniweeb.ui.theme.poppinsFamily


@Composable
fun MangaHomeScreen(
    navController: NavHostController,
    mangaViewModel: MangaViewModel,
    modifier: Modifier = Modifier
) {

    val topManga by mangaViewModel.topManga.collectAsState()
    val latestManga by mangaViewModel.latestManga.collectAsState()
    val allManga by mangaViewModel.allManga.collectAsState()

//    LaunchedEffect(Unit) {
//        mangaViewModel.getLatestManga()
//        mangaViewModel.getAllManga()
//        mangaViewModel.getTopManga()
//    }

    LazyColumn (
        modifier = modifier
            .fillMaxSize()
    ){
        item {
            topManga?.let {
                HomeMangaSection(
                    title = "Most Popular",
                    mangaInfo = it,
                    navController = navController,
                    mangaViewModel = mangaViewModel
                )
            }
        }

        item {
            latestManga?.let {
                HomeMangaSection(
                    title = "Latest",
                    mangaInfo = it,
                    navController = navController,
                    mangaViewModel = mangaViewModel
                )
            }
        }

        itemsIndexed(allManga){index, mangaMinInfo->
            MainMangaDetail(
                mangaMinInfo = mangaMinInfo,
                navController = navController,
                mangaViewModel = mangaViewModel
            )

            if (index == allManga.size-5){
                mangaViewModel.getAllManga()
            }
        }
    }

}

@Composable
fun MainMangaDetail(
    mangaMinInfo: MangaMinInfo,
    navController: NavHostController,
    mangaViewModel: MangaViewModel,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .padding(horizontal = 15.dp, vertical = 8.dp),
        elevation = CardDefaults.elevatedCardElevation(15.dp),
        border = BorderStroke(width = 1.dp, color = Color.Black),
        onClick = {
            navController.navigate("manga_details_screen")
            mangaViewModel.getMangaById(mangaMinInfo.mal_id)
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
                    model = mangaMinInfo.images.webp.large_image_url,
                    contentDescription = "Anime Main Cover"
                )
            }
            Spacer(modifier.height(10.dp))
            if (mangaMinInfo.title_english != null){
                Text(
                    modifier = modifier
                        .fillMaxWidth(),
                    text = mangaMinInfo.title_english,
                    fontFamily = poppinsFamily,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }else{
                Text(
                    modifier = modifier
                        .fillMaxWidth(),
                    text = mangaMinInfo.title,
                    fontFamily = poppinsFamily,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }
            Spacer(modifier.height(5.dp))

            Text(
                text = mangaMinInfo.status,
                fontSize = 17.sp,
                fontFamily = poppinsFamily,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = modifier.height(10.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Text(
                    text = "Rating: ${mangaMinInfo.score}",
                    fontSize = 17.sp,
                    fontFamily = poppinsFamily,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier.width(25.dp))

                if (mangaMinInfo.volumes != null){
                    Text(
                        text = "Volumes: ${mangaMinInfo.volumes}",
                        fontSize = 17.sp,
                        fontFamily = poppinsFamily,
                        fontWeight = FontWeight.SemiBold
                    )
                }else{
                    Text(
                        text = "Volumes: N/A",
                        fontSize = 17.sp,
                        fontFamily = poppinsFamily,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}

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
        elevation = CardDefaults.elevatedCardElevation(10.dp),
        shape = RoundedCornerShape(20.dp),
        onClick = {
            navController.navigate("manga_details_screen")
            mangaViewModel.getMangaById(mangaMinInfo.mal_id)
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
                    model = mangaMinInfo.images.webp.large_image_url,
                    contentDescription = "Manga Cover",
                    modifier = Modifier
                        .height(200.dp)
                        .width(150.dp)
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
            Text(
                text = "Rated: ${mangaMinInfo.score}",
                fontFamily = poppinsFamily,
                fontSize = 13.sp
            )
        }
    }
}