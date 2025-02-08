package com.example.aniweeb.presentation.core.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.example.aniweeb.authentication.viewmodel.AuthViewmodel
import com.example.aniweeb.ui.theme.poppinsFamily

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    authViewmodel: AuthViewmodel
) {

    val currentUser by authViewmodel.currentUser.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            modifier = modifier
                .size(300.dp)
                .clip(CircleShape),
            model = currentUser?.imageUrl,
            contentDescription = "User Profile picture!",
        )
        Spacer(modifier = modifier.height(15.dp))
        currentUser?.name?.let {
            Text(
                text = it,
                fontSize = 18.sp,
                fontFamily = poppinsFamily
            )
        }
        Spacer(modifier = modifier.height(15.dp))
        currentUser?.gender?.let {
            Text(
                text = it,
                fontFamily = poppinsFamily,
                fontSize = 18.sp
            )
        }

        Spacer(modifier = modifier.height(20.dp))

        Button(
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
                fontFamily = poppinsFamily
            )
        }
    }
}