package com.example.aniweeb.presentation.authentication.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.aniweeb.R
import com.example.aniweeb.ui.theme.AniWeebTheme
import com.example.aniweeb.ui.theme.splashFamily
import com.example.aniweeb.ui.theme.splashFamily2

@Composable
fun WelcomeScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {

    var isPressed by remember{ mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxSize()
    ){
        Image(
            modifier = modifier.fillMaxSize(),
            painter = painterResource(R.drawable.main),
            contentDescription = "",
            contentScale = ContentScale.Crop
        )
        Column (
            modifier = modifier
                .fillMaxSize(),
        ){
            Spacer(modifier = modifier.weight(3F))

            Text(
                modifier = modifier.fillMaxWidth(),
                text = "AniWeeb",
                fontSize = 35.sp,
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Center,
                fontFamily = splashFamily,
                color = Color.White
            )

            Text(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp, vertical = 10.dp),
                text = "Discover all the information about the anime," +
                        " manga, their characters and latest updates",
                fontSize = 25.sp,
                fontWeight = FontWeight.SemiBold,
                fontStyle = FontStyle.Italic,
                textAlign = TextAlign.Center,
                fontFamily = splashFamily2,
                color = Color.White
            )

            Spacer(modifier = modifier.weight(5F))

            Button(
                modifier = modifier.fillMaxWidth()
                    .padding(horizontal = 30.dp),
                shape = RoundedCornerShape(10.dp),
                onClick = {
                    navController.navigate("login_screen"){
                        popUpTo(navController.graph.startDestinationId){
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
            ) {
                Text(
                    text = "Get Started!",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = modifier.weight(2F))

        }
    }

}
