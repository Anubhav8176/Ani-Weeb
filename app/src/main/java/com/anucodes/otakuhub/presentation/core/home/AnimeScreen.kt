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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.anucodes.otakuhub.core.model.AnimeMinInfo
import com.anucodes.otakuhub.core.model.AnimeResponse
import com.anucodes.otakuhub.core.networking.viewmodel.AnimeViewModel
import com.anucodes.otakuhub.presentation.core.shared.AnimeScreenShimmer
import com.anucodes.otakuhub.presentation.core.shared.AnimeTile
import com.anucodes.otakuhub.presentation.core.shared.RatingStars
import com.anucodes.otakuhub.ui.theme.AppColors
import com.anucodes.otakuhub.ui.theme.poppinsFamily


@Composable
fun AnimeScreen(
    navController: NavHostController,
    animeViewModel: AnimeViewModel,
    modifier: Modifier = Modifier
) {

    val topAnime by animeViewModel.topAnime.collectAsState()
    val latestAnime by animeViewModel.latestAnime.collectAsState()
    val allAnime by animeViewModel.allAnime.collectAsState()

    val chunkedAnime: List<List<AnimeMinInfo>> = allAnime.chunked(2)

    LaunchedEffect(Unit) {
        animeViewModel.getAnime()
        animeViewModel.getTopAnime()
        animeViewModel.getLatestAnime()
    }

    if ((topAnime?.data?.isNotEmpty() == true) && (latestAnime?.data?.isNotEmpty()==true) && chunkedAnime.isNotEmpty()){
        MainAnimeScreen(
            modifier = modifier,
            navController = navController,
            animeViewModel = animeViewModel,
            topAnime = topAnime,
            latestAnime = latestAnime,
            chunkedAnime = chunkedAnime
        )
    }else{
        AnimeScreenShimmer(modifier = modifier)
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
        colors = CardDefaults.outlinedCardColors(
            containerColor = Color.Transparent,
            contentColor = Color.Transparent
        ),
        onClick = {
            navController.navigate("anime_details_screen")
            animeViewModel.getAnimeInfoById(animeMinInfo.mal_id)
        },
    ){
        Column(
            modifier = modifier
                .padding(10.dp)
        ){
            Card(
                shape = RoundedCornerShape(20.dp)
            ){
                AsyncImage(
                    modifier = modifier
                        .height(250.dp)
                        .align(Alignment.CenterHorizontally),
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
                maxLines = 2,
                minLines = 2,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center,
                color = Color.White
            )
            Spacer(modifier.height(5.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = animeMinInfo.type,
                    fontSize = 16.sp,
                    fontFamily = poppinsFamily,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
                RatingStars(rating = animeMinInfo.score.toDouble())
            }
        }
    }
}


@Composable
fun MainAnimeScreen(
    modifier: Modifier,
    navController: NavHostController,
    animeViewModel: AnimeViewModel,
    topAnime: AnimeResponse?,
    latestAnime: AnimeResponse?,
    chunkedAnime: List<List<AnimeMinInfo>>
){
    LazyColumn (
        modifier = modifier
            .fillMaxSize()
            .background(AppColors.Background)
    ){
        item {
            topAnime?.let {
                AnimeTile(
                    title = "Most Popular",
                    animeInfo = it,
                    navController = navController,
                    animeViewModel = animeViewModel
                )
            }
        }

        item {
            latestAnime?.let {
                AnimeTile(
                    title = "Top Airing",
                    animeInfo = it,
                    navController = navController,
                    animeViewModel = animeViewModel
                )
            }
        }

        item {
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = "Discover Animes",
                textAlign = TextAlign.Center,
                textDecoration = TextDecoration.Underline,
                fontSize = 20.sp,
                fontFamily = poppinsFamily,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )
        }

        chunkedAnime?.let {
            itemsIndexed(chunkedAnime) { index, animePair ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(modifier = Modifier.weight(1f)) {
                        MainAnimeDetail(
                            animeMinInfo = animePair[0],
                            navController = navController,
                            animeViewModel = animeViewModel
                        )
                    }

                    Box(modifier = Modifier.weight(1f)) {
                        if (animePair.size > 1) {
                            MainAnimeDetail(
                                animeMinInfo = animePair[1],
                                navController = navController,
                                animeViewModel = animeViewModel
                            )
                        }
                    }
                }

                if (index == chunkedAnime.size - 3) {
                    LaunchedEffect(index) {
                        animeViewModel.getAnime()
                    }
                }
            }
        }
    }
}

