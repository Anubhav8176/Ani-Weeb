package com.example.aniweeb.presentation.authentication.ui

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material.icons.filled.ArrowDropDownCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
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
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.aniweeb.R
import com.example.aniweeb.authentication.model.UserInfo
import com.example.aniweeb.authentication.response.AuthResponse
import com.example.aniweeb.authentication.viewmodel.AuthViewmodel
import com.example.aniweeb.ui.theme.AniWeebTheme
import com.example.aniweeb.ui.theme.pacificoFamily
import com.example.aniweeb.ui.theme.poppinsFamily


@Composable
fun SignUpScreen(
    navController: NavHostController,
    authViewmodel: AuthViewmodel,
    modifier: Modifier = Modifier
) {

    var dropDownIndex by remember { mutableStateOf(0) }
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val isLoggedIn by authViewmodel.isLoggedIn.collectAsState()
    var passwordVisi by remember { mutableStateOf(false) }
    val context = LocalContext.current

    when(isLoggedIn){
        is AuthResponse.Failure -> {
            Toast.makeText(context, (isLoggedIn as AuthResponse.Failure).message, Toast.LENGTH_SHORT).show()
            authViewmodel.makeIsLoggedInIdle()
        }
        AuthResponse.Loading -> {
        }
        AuthResponse.Success -> {
            navController.navigate("home_graph"){
                popUpTo(navController.graph.startDestinationId){
                    inclusive = true
                }
                launchSingleTop = true
            }
            authViewmodel.makeIsLoggedInIdle()
        }
        else -> {
            authViewmodel.makeIsLoggedInIdle()
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(brush = Brush.verticalGradient(
                colorStops = arrayOf(
                    0.1f to Color(0xFFCA38ED),
                    0.6f to Color(0xFF3E1149),
                    0.99f to Color(0xFF1e0624)
                )
            )),
        verticalArrangement = Arrangement.SpaceBetween
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.33f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 40.dp)
                    .clip(RectangleShape)
                    .size(150.dp),
                painter = painterResource(R.drawable.login_image),
                contentDescription = "Authentication background",
                contentScale = ContentScale.FillHeight
            )

            Text(
                modifier = modifier
                    .fillMaxWidth(),
                text = "ようこそ!",
                fontFamily = pacificoFamily,
                fontSize = 40.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFFF8F8F8)
            )
        }
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(color = Color.Transparent),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = modifier.height(20.dp))

            Text(
                modifier = modifier
                    .fillMaxWidth(0.88f),
                text = "Your name",
                fontSize = 16.sp,
                fontFamily = poppinsFamily,
                color = Color.White
            )
            OutlinedTextField(
                modifier = modifier
                    .fillMaxWidth(0.9f),
                value = name,
                onValueChange = {
                    name = it
                },
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = Color.White.copy(alpha = 0.3f),
                    focusedContainerColor = Color.White.copy(alpha = 0.3f),
                    unfocusedBorderColor = Color.White,
                    focusedBorderColor = Color(0xFFE487FB)
                ),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Name Icon",
                        tint = Color.White
                    )
                },
                shape = RoundedCornerShape(10.dp),
                placeholder = {
                    Text(
                        text = "Enter you name!",
                        fontFamily = poppinsFamily,
                        fontSize = 18.sp,
                        color = Color.White
                    )
                }
            )

            Spacer(modifier = modifier.height(10.dp))

            Text(
                modifier = modifier
                    .fillMaxWidth(0.88f),
                text = "Email",
                fontSize = 16.sp,
                fontFamily = poppinsFamily,
                color = Color.White
            )
            OutlinedTextField(
                modifier = modifier
                    .fillMaxWidth(0.9f),
                value = email,
                onValueChange = {
                    email = it
                },
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = Color.White.copy(alpha = 0.3f),
                    focusedContainerColor = Color.White.copy(alpha = 0.3f),
                    unfocusedBorderColor = Color.White,
                    focusedBorderColor = Color(0xFFE487FB)
                ),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = "Email Icon",
                        tint = Color.White
                    )
                },
                shape = RoundedCornerShape(10.dp),
                placeholder = {
                    Text(
                        text = "Enter you email!",
                        fontFamily = poppinsFamily,
                        fontSize = 18.sp,
                        color = Color.White
                    )
                }
            )

            Spacer(modifier = modifier.height(15.dp))

            Text(
                modifier = modifier
                    .fillMaxWidth(0.88f),
                text = "Password",
                fontSize = 16.sp,
                fontFamily = poppinsFamily,
                color = Color.White
            )
            OutlinedTextField(
                modifier = modifier
                    .fillMaxWidth(0.9f),
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
                    unfocusedContainerColor = Color.White.copy(alpha = 0.3f),
                    focusedContainerColor = Color.White.copy(alpha = 0.3f),
                    unfocusedBorderColor = Color.White,
                    focusedBorderColor = Color(0xFFE487FB)
                ),
                shape = RoundedCornerShape(10.dp),
                placeholder = {
                    Text(
                        text = "Enter you password!",
                        fontFamily = poppinsFamily,
                        fontSize = 18.sp,
                        color = Color.White
                    )
                }
            )

            Spacer(modifier = modifier.height(35.dp))

            Button(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp, vertical = 10.dp),
                onClick = {
                    val newUser = UserInfo(
                        name = name,
                        email = email,
                        imageUrl = ""
                    )
                    if (newUser.name.isNotBlank() && newUser.email.isNotBlank() && password.isNotBlank()){
                        authViewmodel.registerUser(newUser, password)
                    }else{
                        Toast.makeText(context, "Fill all the details.", Toast.LENGTH_SHORT).show()
                    }
                },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFE487FB)
                ),
                contentPadding = PaddingValues(vertical = 15.dp)
            ) {
                if (isLoggedIn == AuthResponse.Loading){
                    CircularProgressIndicator(
                        color = Color.White,
                        modifier = modifier
                            .size(25.dp)
                    )
                }else {
                    Text(
                        text = "Register!!",
                        fontSize = 18.sp,
                        fontFamily = poppinsFamily,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            ){
                HorizontalDivider(
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
                HorizontalDivider(
                    modifier = modifier
                        .weight(1f)
                        .padding(start = 5.dp),
                    color = Color.White
                )
            }

            Row {
                Button(
                    modifier = modifier
                        .padding(
                            horizontal = 15.dp,
                            vertical = 10.dp
                        )
                        .clip(RoundedCornerShape(10.dp))
                        .background(
                            Color.White.copy(alpha = 0.3f)
                        )
                        .border(
                            width = 1.dp,
                            shape = RoundedCornerShape(10.dp),
                            color = Color.White
                        ),
                    onClick = {
                        authViewmodel.signInWithGoogleButton()
                    },
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent
                    ),
                    contentPadding = PaddingValues(vertical = 15.dp)
                ) {
                    Box{
                        Image(
                            modifier = Modifier
                                .size(26.dp),
                            painter = painterResource(R.drawable.ic_google_icon),
                            contentDescription = "Google Button"
                        )
                    }
                }

                Button(
                    modifier = modifier
                        .padding(
                            horizontal = 15.dp,
                            vertical = 10.dp
                        )
                        .clip(RoundedCornerShape(10.dp))
                        .background(
                            Color.White.copy(alpha = 0.3f)
                        )
                        .border(
                            width = 1.dp,
                            shape = RoundedCornerShape(10.dp),
                            color = Color.White
                        ),
                    onClick = {

                    },
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent
                    ),

                    contentPadding = PaddingValues(vertical = 15.dp)
                ) {
                    Box{
                        Image(
                            modifier = Modifier
                                .size(26.dp),
                            painter = painterResource(R.drawable.facebook),
                            contentDescription = "Facebook icon",
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Already registered?",
                    fontSize = 18.sp,
                    color = Color.White,
                    fontFamily = poppinsFamily
                )
                Spacer(modifier = modifier.width(5.dp))
                Text(
                    modifier = modifier
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = rememberRipple(bounded = true)
                        ) {
                            navController.navigate("login_screen"){
                                popUpTo(navController.graph.startDestinationId){
                                    inclusive = true
                                }
                                launchSingleTop = true
                            }

                        },
                    text = "Log In",
                    fontSize = 18.sp,
                    color = Color.White,
                    fontFamily = poppinsFamily
                )
            }
        }
    }
}

