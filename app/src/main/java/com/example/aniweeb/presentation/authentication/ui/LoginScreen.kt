package com.example.aniweeb.presentation.authentication.ui

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.aniweeb.R
import com.example.aniweeb.authentication.response.AuthResponse
import com.example.aniweeb.authentication.viewmodel.AuthViewmodel
import com.example.aniweeb.ui.theme.pacificoFamily
import com.example.aniweeb.ui.theme.poppinsFamily

@Composable
fun LoginScreen(
    navController: NavHostController,
    authViewmodel: AuthViewmodel,
    modifier: Modifier = Modifier
) {

    var email by remember {mutableStateOf("")}
    var password by remember { mutableStateOf("") }
    val isLoggedIn by authViewmodel.isLoggedIn.collectAsState()
    var passwordVisi by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(true) }

    when(isLoggedIn){
        is AuthResponse.Failure -> {
            isLoading = false

            Toast.makeText(LocalContext.current, (isLoggedIn as AuthResponse.Failure).message, Toast.LENGTH_SHORT).show()
        }
        AuthResponse.Loading -> {
            isLoading = true
        }
        AuthResponse.Success -> {
            navController.navigate("home_graph"){
                popUpTo(navController.graph.startDestinationId){
                    inclusive = true
                }
                launchSingleTop = true
            }
            isLoading = false
        }
        else -> {
            isLoading = false
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
    ){
        Box(
            modifier = modifier
                .fillMaxHeight(0.4f)
        ){
            Image(
                modifier = modifier
                    .clip(RectangleShape),
                painter = painterResource(R.drawable.auth_back),
                contentDescription = "Authentication background",
                contentScale = ContentScale.FillHeight
            )
            Column(
                modifier = modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ){
                Spacer(modifier.weight(1F))
                Text(
                    text = "AniWeeb",
                    fontFamily = pacificoFamily,
                    fontSize = 30.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFFF8F8F8)
                )
                Spacer(modifier.weight(2F))
            }
        }
        Column(
            modifier = modifier
                .fillMaxWidth()
                .fillMaxHeight(0.7f)
                .align(Alignment.BottomCenter)
        ){
            Box(
                modifier = modifier
                    .background(
                        color = Color.DarkGray,
                        shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)
                    )
            ) {

                Image(
                    modifier = modifier
                        .fillMaxSize(),
                    painter = painterResource(R.drawable.itachi_back),
                    contentDescription = "Itachi Uchiha"
                )

                Column (
                    modifier = modifier
                        .fillMaxSize()
                        .background(color = Color.Transparent),
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Spacer(modifier = modifier.height(20.dp))

                    Text(
                        modifier = modifier
                            .fillMaxWidth(),
                        text = "Welcome Back!",
                        fontFamily = pacificoFamily,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFFF8F8F8)
                    )

                    Spacer(modifier = modifier.weight(2F))

                    Text(
                        modifier = modifier
                            .fillMaxWidth(0.82f),
                        text = "Email",
                        fontSize = 16.sp,
                        fontFamily = poppinsFamily,
                        color = Color.White
                    )
                    OutlinedTextField(
                        modifier = modifier
                            .fillMaxWidth(0.85f)
                            .border(2.dp, shape = RoundedCornerShape(20.dp), color = Color(0xFFFFC0C8)),
                        value = email,
                        onValueChange = {
                            email = it
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White
                        ),
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Email,
                                contentDescription = "Email Icon",
                                tint = Color.White
                            )
                        },
                        shape = RoundedCornerShape(20.dp),
                        placeholder = {
                            Text(
                                text = "Enter you email!",
                                fontFamily = poppinsFamily,
                                fontSize = 18.sp,
                                color = Color(0xFFFFC0CB)
                            )
                        }
                    )

                    Spacer(modifier = modifier.height(20.dp))

                    Text(
                        modifier = modifier
                            .fillMaxWidth(0.82f),
                        text = "Password",
                        fontSize = 16.sp,
                        fontFamily = poppinsFamily,
                        color = Color.White
                    )
                    OutlinedTextField(
                        modifier = modifier
                            .fillMaxWidth(0.85f)
                            .border(2.dp, shape = RoundedCornerShape(20.dp), color = Color(0xFFFFC0C8)),
                        value = password,
                        onValueChange = {
                            password = it
                        },
                        visualTransformation = if(passwordVisi) VisualTransformation.None else PasswordVisualTransformation(),
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Key,
                                contentDescription = "Password",
                                tint = Color.White
                            )
                        },
                        trailingIcon = {
                            IconButton(
                                onClick = {
                                    passwordVisi = !passwordVisi
                                }
                            ) {
                                Icon(
                                    imageVector = if (passwordVisi)Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                    contentDescription = "Password Visibility",
                                    tint = Color.White
                                )
                            }
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White
                        ),
                        shape = RoundedCornerShape(20.dp),
                        placeholder = {
                            Text(
                                text = "Enter you password!",
                                fontFamily = poppinsFamily,
                                fontSize = 18.sp,
                                color = Color(0xFFFFC0CB)
                            )
                        }
                    )

                    Spacer(modifier = modifier.height(15.dp))

                    Row (
                        modifier = modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ){
                        Text(
                            text = "New to AniWeeb?",
                            fontSize = 18.sp,
                            color = Color(0xFFFFC0CB),
                            fontFamily = poppinsFamily
                        )
                        Spacer(modifier = modifier.width(5.dp))
                        Text(
                            modifier = modifier
                                .clickable {
                                    navController.navigate("signup_screen")
                                },
                            text = "SignUp!!",
                            fontSize = 18.sp,
                            color = Color(0xFFFFC0CB),
                            fontFamily = poppinsFamily
                        )
                    }

                    Spacer(modifier = modifier.weight(1F))

                    Button(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(horizontal = 15.dp, vertical = 10.dp),
                        onClick = {
                            isLoading = true
                            authViewmodel.loginUser(email, password)
                        },
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        if (isLoading){
                            CircularProgressIndicator(
                                color = Color.White,
                                modifier = modifier.size(25.dp)
                            )
                        }else {
                            Text(
                                text = "Log In!",
                                fontSize = 18.sp,
                                fontFamily = poppinsFamily
                            )
                        }
                    }


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
                            text = "OR",
                            fontSize = 18.sp,
                            color = Color.White,

                        )
                        Divider(
                            modifier = modifier
                                .weight(1f)
                                .padding(start = 5.dp),
                            color = Color.White
                        )
                    }

                    Button(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(horizontal = 15.dp, vertical = 10.dp),
                        onClick = {

                        },
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ){
                            Image(
                                painter = painterResource(R.drawable.ic_google_icon),
                                contentDescription = "Google Button"
                            )
                            Spacer(modifier = modifier.width(10.dp))
                            Text(
                                text = "Log In!",
                                fontSize = 18.sp,
                                fontFamily = poppinsFamily
                            )
                        }
                    }

                    Spacer(modifier = modifier.weight(2F))
                }
            }
        }
    }
}
