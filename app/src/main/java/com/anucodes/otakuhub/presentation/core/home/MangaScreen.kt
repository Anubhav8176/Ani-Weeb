package com.anucodes.otakuhub.presentation.core.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.anucodes.otakuhub.core.model.MangaMinInfo
import com.anucodes.otakuhub.core.model.MangaResponse
import com.anucodes.otakuhub.core.networking.viewmodel.MangaViewModel
import com.anucodes.otakuhub.presentation.core.shared.AnimeScreenShimmer
import com.anucodes.otakuhub.presentation.core.shared.HomeMangaSection
import com.anucodes.otakuhub.presentation.core.shared.RatingStars
import com.anucodes.otakuhub.ui.theme.AppColors
import com.anucodes.otakuhub.ui.theme.poppinsFamily


@Composable
fun MangaScreen(
    navController: NavHostController,
    mangaViewModel: MangaViewModel,
    modifier: Modifier = Modifier
) {

    val topManga by mangaViewModel.topManga.collectAsState()
    val latestManga by mangaViewModel.latestManga.collectAsState()
    val allManga by mangaViewModel.allManga.collectAsState()

    val chunkedManga: List<List<MangaMinInfo>> = allManga.chunked(2)

    LaunchedEffect(Unit) {
        mangaViewModel.getTopManga()
        mangaViewModel.getAllManga()
        mangaViewModel.getLatestManga()
    }

    if ((topManga?.data?.isNotEmpty()==true) && (latestManga?.data?.isNotEmpty() == true) && chunkedManga.isNotEmpty()){
        MainMangaScreen(
            modifier = modifier,
            navController = navController,
            mangaViewModel = mangaViewModel,
            topManga = topManga,
            latestManga = latestManga,
            chunkedManga = chunkedManga
        )
    }else{
        AnimeScreenShimmer(modifier = modifier)
    }

}


@Composable
fun MainMangaScreen(
    modifier: Modifier,
    navController: NavHostController,
    mangaViewModel: MangaViewModel,
    topManga: MangaResponse?,
    latestManga: MangaResponse?,
    chunkedManga: List<List<MangaMinInfo>>
){

    LazyColumn (
        modifier = modifier
            .fillMaxSize()
            .background(color = AppColors.Background)
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

        item {
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = "Discover Mangas",
                textAlign = TextAlign.Center,
                textDecoration = TextDecoration.Underline,
                fontSize = 20.sp,
                fontFamily = poppinsFamily,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )
        }

        chunkedManga?.let {
            itemsIndexed(chunkedManga) { index, mangaPair ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(modifier = Modifier.weight(1f)) {
                        MainMangaDetail(
                            mangaMinInfo = mangaPair[0],
                            navController = navController,
                            mangaViewModel = mangaViewModel
                        )
                    }

                    Box(modifier = Modifier.weight(1f)) {
                        if (mangaPair.size > 1) {
                            MainMangaDetail(
                                mangaMinInfo = mangaPair[1],
                                navController = navController,
                                mangaViewModel = mangaViewModel
                            )
                        }
                    }
                }

                if (index == chunkedManga.size - 3) {
                    LaunchedEffect(index) {
                        mangaViewModel.getAllManga()
                    }
                }
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
        colors = CardDefaults.outlinedCardColors(
            containerColor = Color.Transparent,
            contentColor = Color.White
        ),
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
                        .height(250.dp),
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
                    maxLines = 2,
                    minLines = 2,
                    overflow = TextOverflow.Ellipsis,
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

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = mangaMinInfo.status,
                    fontSize = 16.sp,
                    fontFamily = poppinsFamily,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
                RatingStars(rating = mangaMinInfo.score.toDouble())
            }
        }
    }
}