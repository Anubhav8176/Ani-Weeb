package com.anucodes.otakuhub.presentation.core.bottomnavigation

import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlurEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.anucodes.otakuhub.ui.theme.AppColors
import com.anucodes.otakuhub.ui.theme.poppinsFamily

@Composable
fun BottomNavigationBar(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val navItems = listOf(
        BottomNavigationItems.Anime,
        BottomNavigationItems.Manga,
        BottomNavigationItems.Explore,
        BottomNavigationItems.Profile
    )

    val shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)

    Box(
        modifier = modifier
            .background(color = AppColors.Background)
            .clip(shape)
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            Spacer(
                modifier = Modifier
                    .matchParentSize()
                    .graphicsLayer {
                        renderEffect = BlurEffect(
                            radiusX = 20f,
                            radiusY = 20f,
                            edgeTreatment = TileMode.Clamp
                        )
                    }
                    .background(Color(0xFF1C1D28))
            )
        }

        Spacer(
            modifier = Modifier
                .matchParentSize()
                .background(Color(0xD21C1D28))
                .drawWithContent {
                    drawContent()
                    drawLine(
                        color = Color(0x0FFFFFFF),
                        start = Offset(0f, 0f),
                        end = Offset(size.width, 0f),
                        strokeWidth = 1.dp.toPx()
                    )
                }
        )

        NavigationBar(
            containerColor = Color.Transparent,
            contentColor = Color.White,
            modifier = Modifier
                .border(
                    width = 1.dp,
                    color = Color(0x0FFFFFFF),
                    shape = shape
                )
        ) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            navItems.forEach { items ->
                NavigationBarItem(
                    icon = {
                        Icon(
                            imageVector = items.icon,
                            contentDescription = items.label
                        )
                    },
                    label = {
                        Text(
                            text = items.label,
                            fontFamily = poppinsFamily,
                            fontSize = 16.sp
                        )
                    },
                    alwaysShowLabel = false,
                    selected = currentRoute == items.route,
                    onClick = {
                        navController.navigate(items.route) {
                            popUpTo(navController.graph.findStartDestination().id){
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.White,
                        selectedTextColor = Color.White,
                        unselectedIconColor = Color(0xFF8E8FA8),
                        unselectedTextColor = Color(0xFF8E8FA8),
                        indicatorColor = Color(0xFF7B5CF0)
                    )
                )
            }
        }
    }
}