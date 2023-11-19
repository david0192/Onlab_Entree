package com.entree.entreeapp.screen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Home : BottomBarScreen(
        route = "home_sportfacilities",
        title = "Edzőtermek",
        icon = Icons.Default.SportsMartialArts
    )

    object Profile : BottomBarScreen(
        route = "profile",
        title = "Fiókom",
        icon = Icons.Default.Person
    )
    object AdminSportFacilityDetails : BottomBarScreen(
        route = "admin_sportfacility_details",
        title = "Edzőterem",
        icon = Icons.Default.SportsMartialArts
    )

    object AdminStatistics : BottomBarScreen(
        route = "admin_statistics",
        title = "Statisztika",
        icon = Icons.Default.QueryStats
    )

}