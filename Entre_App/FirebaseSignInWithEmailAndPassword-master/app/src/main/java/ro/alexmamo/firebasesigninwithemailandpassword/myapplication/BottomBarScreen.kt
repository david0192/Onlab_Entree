package ro.alexmamo.firebasesigninwithemailandpassword.myapplication

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Home : BottomBarScreen(
        route = "home_sportfacilities",
        title = "home_sportfacilities",
        icon = Icons.Default.Home
    )

    object Profile : BottomBarScreen(
        route = "profile",
        title = "profile",
        icon = Icons.Default.Person
    )


}