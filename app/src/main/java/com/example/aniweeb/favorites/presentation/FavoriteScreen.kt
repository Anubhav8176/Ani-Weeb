package com.example.aniweeb.favorites.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.example.aniweeb.core.networking.viewmodel.AnimeViewModel
import com.example.aniweeb.core.networking.viewmodel.MangaViewModel
import com.example.aniweeb.favorites.model.UserFavorite
import com.example.aniweeb.ui.theme.poppinsFamily
import kotlinx.coroutines.delay

@Composable
fun FavoriteScreen(
    animeViewModel: AnimeViewModel,
    mangaViewModel: MangaViewModel,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {

    val favorite by animeViewModel.favorites.collectAsState()

    var selectedFavoriteAnimeTab by remember { mutableStateOf(true) }

    val favAnime = favorite.filter { it.category == "anime" }
    val favManga = favorite.filter { it.category == "manga" }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.linearGradient(
                    colors = listOf(Color(0xFF4A00E0), Color(0xFF8E2DE2))
                )
            )

    ){
        Column(
            modifier = modifier
                .padding(top = 40.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row(
                modifier = modifier
                    .fillMaxWidth()
            ) {
                Box(
                    modifier = modifier
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = rememberRipple(bounded = true)
                        ) {
                            selectedFavoriteAnimeTab = !selectedFavoriteAnimeTab
                        }
                        .padding(
                            horizontal = 10.dp
                        )
                        .clip(RoundedCornerShape(15.dp))
                        .background(
                            color = if (selectedFavoriteAnimeTab) Color(0xFF03b1fc) else Color.Transparent
                        )
                        .weight(1F)
                        .border(
                            width = 1.dp,
                            color = Color.Black,
                            shape = RoundedCornerShape(15.dp)
                        )
                ){
                    Text(
                        modifier = modifier
                            .padding(vertical = 8.dp)
                            .fillMaxWidth(),
                        text = "Anime",
                        fontSize = 18.sp,
                        fontFamily = poppinsFamily,
                        textAlign = TextAlign.Center,
                        color = if (selectedFavoriteAnimeTab) Color.Black else Color.White
                    )
                }

                Box(
                    modifier = modifier
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = rememberRipple(bounded = true)
                        ) {
                            selectedFavoriteAnimeTab = !selectedFavoriteAnimeTab
                        }
                        .padding(
                            horizontal = 10.dp
                        )
                        .clip(RoundedCornerShape(15.dp))
                        .background(
                            color = if (!selectedFavoriteAnimeTab) Color(0xFF03b1fc) else Color.Transparent
                        )
                        .weight(1F)
                        .border(
                            width = 1.dp,
                            color = Color.Black,
                            shape = RoundedCornerShape(15.dp)
                        )
                ){
                    Text(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        text = "Manga",
                        fontSize = 18.sp,
                        fontFamily = poppinsFamily,
                        textAlign = TextAlign.Center,
                        color = if (!selectedFavoriteAnimeTab) Color.Black else Color.White
                    )
                }
            }

            if (selectedFavoriteAnimeTab){
                FavoriteAnimeList(
                    favAnime = favAnime,
                    navController = navController,
                    animeViewModel = animeViewModel,
                    mangaViewModel = mangaViewModel
                )
            }else{
                FavoriteMangaList(
                    favManga = favManga,
                    navController = navController,
                    animeViewModel = animeViewModel,
                    mangaViewModel = mangaViewModel
                )
            }

        }
    }
}


@Composable
fun FavoriteAnimeList(
    favAnime: List<UserFavorite>,
    navController: NavHostController,
    animeViewModel: AnimeViewModel,
    mangaViewModel: MangaViewModel,
    modifier: Modifier = Modifier
) {
    LazyColumn {
        items(favAnime){anime->
            FavoriteTile(
                favorite = anime,
                navController = navController,
                animeViewModel = animeViewModel,
                mangaViewModel = mangaViewModel
            )
        }
    }
}

@Composable
fun FavoriteMangaList(
    favManga: List<UserFavorite>,
    navController: NavHostController,
    animeViewModel: AnimeViewModel,
    mangaViewModel: MangaViewModel,
    modifier: Modifier = Modifier
) {
    LazyColumn {
        items(favManga){manga->
            FavoriteTile(
                favorite = manga,
                navController = navController,
                animeViewModel = animeViewModel,
                mangaViewModel = mangaViewModel
            )
        }
    }
}

@Composable
fun FavoriteTile(
    favorite: UserFavorite,
    navController: NavHostController,
    animeViewModel: AnimeViewModel,
    mangaViewModel: MangaViewModel,
    modifier: Modifier = Modifier
) {
   Column {
       Row (
           modifier = modifier
               .padding(
                   horizontal = 10.dp,
                   vertical = 15.dp
               )
               .fillMaxWidth()
               .clickable(
                   interactionSource = remember { MutableInteractionSource() },
                   indication = rememberRipple(bounded = true)
               ) {
                   if (favorite.category == "anime"){
                       animeViewModel.getAnimeInfoById(favorite.mal_id)
                       navController.navigate("anime_details_screen")
                   }else{
                       mangaViewModel.getMangaById(favorite.mal_id)
                       navController.navigate("manga_details_screen")
                   }
               }
       ){
           AsyncImage(
               modifier = modifier
                   .height(200.dp)
                   .width(150.dp)
                   .clip(RoundedCornerShape(20.dp)),
               model = favorite.imageUrl,
               contentScale = ContentScale.FillBounds,
               contentDescription = "Anime cover"
           )

           Column (
               modifier = modifier
                   .padding(horizontal = 10.dp)
           ){
               Text(
                   text = favorite.title,
                   fontSize = 20.sp,
                   fontFamily = poppinsFamily,
                   textDecoration = TextDecoration.Underline,
                   color = Color.White
               )
               Spacer(modifier.height(20.dp))
               Text(
                   text = "Popularity: ${favorite.popularity}",
                   fontSize = 18.sp,
                   fontFamily = poppinsFamily,
                   color = Color.White
               )
               Spacer(modifier.height(4.dp))
               Text(
                   text = "Rank: ${favorite.rank}",
                   fontSize = 18.sp,
                   fontFamily = poppinsFamily,
                   color = Color.White
               )
           }
       }

       Button(
           modifier = modifier
               .padding(horizontal = 20.dp)
               .fillMaxWidth(),
           shape = RoundedCornerShape(10.dp),
           border = BorderStroke(width = 1.dp, color = Color.Black),
           colors = ButtonDefaults.buttonColors(
               contentColor = Color.Black,
               containerColor = Color.White
           ),
           elevation = ButtonDefaults.elevatedButtonElevation(10.dp),
           onClick = {
               animeViewModel.deleteFavoriteAnime(favorite)
           }
       ) {
           Row {
               Icon(
                   imageVector = Icons.Default.Delete,
                   contentDescription = "Delete"
               )

               Spacer(modifier.width(5.dp))

               Text(
                   text = "Remove favorite!!",
                   fontSize = 18.sp,
                   fontFamily = poppinsFamily
               )
           }
       }
       Spacer(modifier.height(13.dp))
       HorizontalDivider(thickness = 2.dp)
   }
}
