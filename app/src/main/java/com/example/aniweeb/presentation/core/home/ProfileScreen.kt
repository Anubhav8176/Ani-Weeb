package com.example.aniweeb.presentation.core.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.aniweeb.R
import com.example.aniweeb.authentication.viewmodel.AuthViewmodel
import com.example.aniweeb.core.networking.viewmodel.AnimeViewModel
import com.example.aniweeb.ui.theme.poppinsFamily

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    authViewmodel: AuthViewmodel,
    animeViewModel: AnimeViewModel
) {

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        ProfileUi(
            authViewmodel = authViewmodel,
            animeViewModel = animeViewModel,
            navController = navController
        )
    }
}

@Composable
fun ProfileUi(
    modifier: Modifier = Modifier,
    authViewmodel: AuthViewmodel,
    animeViewModel: AnimeViewModel,
    navController: NavHostController
) {
    val currentUser by authViewmodel.currentUser.collectAsState()

    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = modifier.height(20.dp))
        AsyncImage(
            modifier = modifier
                .size(275.dp)
                .clip(CircleShape)
                .border(
                    width = 2.dp,
                    shape = CircleShape,
                    color = Color.Black
                ),
            model = ImageRequest.Builder(LocalContext.current)
                .data(currentUser?.imageUrl)
                .build(),
            placeholder = painterResource(R.drawable.images),
            error = painterResource(R.drawable.images),
            contentScale = ContentScale.FillBounds,
            contentDescription = "User Profile picture!"
        )
        Spacer(modifier.height(20.dp))

        TextButton(
            onClick = {

            }
        ) {
            Text(
                modifier = modifier
                    .border(
                        width = 1.dp,
                        shape = RoundedCornerShape(15.dp),
                        color = Color.Black
                    )
                    .padding(horizontal = 15.dp, vertical = 7.dp),
                text = "Edit/Add image!",
                fontFamily = poppinsFamily,
                fontSize = 18.sp,
                color = Color.White
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
                    .padding(end = 5.dp),
                color = Color.White
            )
            Text(
                text = "Personal Info",
                fontSize = 18.sp,
                color = Color.White,
                fontFamily = poppinsFamily,
                fontWeight = FontWeight.Bold
            )
            Divider(
                modifier = modifier
                    .weight(1f)
                    .padding(start = 5.dp),
                color = Color.White
            )
        }
        Spacer(modifier = modifier.height(20.dp))
        currentUser?.name?.let {
            Text(
                text = "Name: $it",
                fontSize = 18.sp,
                fontFamily = poppinsFamily,
                color = Color.White
            )
        }
        Spacer(modifier.height(10.dp))
        currentUser?.gender?.let {
            Text(
                text = "Gender: $it",
                fontFamily = poppinsFamily,
                fontSize = 18.sp,
                color = Color.White
            )
        }
        Spacer(modifier.height(10.dp))
        currentUser?.email?.let {
            Text(
                text = "Email: $it",
                fontFamily = poppinsFamily,
                fontSize = 18.sp,
                color = Color.White
            )
        }
        Spacer(modifier.height(10.dp))
        Button(
            modifier = modifier
                .fillMaxWidth(0.8f),
            shape = RoundedCornerShape(15.dp),
            border = BorderStroke(width = 2.dp, color = Color.Black),
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.Black,
                containerColor = Color.White
            ),
            elevation = ButtonDefaults.elevatedButtonElevation(15.dp),
            onClick = {
                authViewmodel.signOut()
                navController.navigate("auth_graph"){
                    popUpTo(navController.graph.startDestinationId){
                        inclusive = true
                    }
                    launchSingleTop = true
                }
            }
        ) {
            Text(
                text = "Log Out!",
                fontSize = 18.sp,
                fontFamily = poppinsFamily,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier.height(20.dp))
        HorizontalDivider(
            thickness = 2.dp,
            modifier = modifier
                .padding(horizontal = 20.dp),
            color = Color.White
        )
        Spacer(modifier.height(15.dp))
        Button(
            modifier = modifier
                .fillMaxWidth(0.8f),
            shape = RoundedCornerShape(15.dp),
            border = BorderStroke(width = 2.dp, color = Color.Black),
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.Black,
                containerColor = Color.White
            ),
            elevation = ButtonDefaults.elevatedButtonElevation(15.dp),
            onClick = {
                animeViewModel.getAllFavorites()
                navController.navigate("favorite_screen")
            }
        ) {
            Text(
                text = "See Favorites!",
                fontSize = 19.sp,
                fontFamily = poppinsFamily,
                fontWeight = FontWeight.Bold
            )
        }
    }
}