package com.entree.entreeapp.presentation.home

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.entree.entreeapp.enums.Roles
import com.entree.entreeapp.navigation.app.AdminSetUpNavGraph
import com.entree.entreeapp.navigation.app.SetUpNavGraph
import com.entree.entreeapp.presentation.IntWrapper
import com.entree.entreeapp.presentation.admin_site.AdminScreen
import com.entree.entreeapp.presentation.admin_site.AdminScreenViewModel
import com.entree.entreeapp.screen.BottomBarScreen
import com.entree.entreeapp.presentation.sportfacilities.SportFacilityViewModel
import com.entree.entreeapp.presentation.sportfacilities.TicketTypeViewModel
import com.entree.entreeapp.presentation.profile.UserViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(navcontroller: NavHostController, sportFacilityViewModel: SportFacilityViewModel, ticketTypeViewModel: TicketTypeViewModel, userViewModel: UserViewModel, boughtTicketTypeId:IntWrapper){
    Scaffold(bottomBar = {
        BottomBar(navController=navcontroller, roleId = Roles.GUEST.value)
    }) {
        SetUpNavGraph(navController = navcontroller, spfvm= sportFacilityViewModel, ttvm= ticketTypeViewModel, uvm=userViewModel, boughtTicketTypeId = boughtTicketTypeId)
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AdminHomeScreen(navcontroller: NavHostController, avm:AdminScreenViewModel){
    Scaffold(bottomBar = {
        BottomBar(navController=navcontroller, roleId = Roles.ADMIN.value)
    }) {
        AdminSetUpNavGraph(avm=avm, navController = navcontroller)
    }
}

@Composable
fun BottomBar(navController: NavHostController, roleId:Int) {
    val screens = if(roleId== Roles.ADMIN.value){
        listOf(
            BottomBarScreen.AdminSportFacilityDetails,
            BottomBarScreen.AdminStatistics,
        )
    }
    else{
        listOf(
            BottomBarScreen.Home,
            BottomBarScreen.Profile,
        )
    }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val bottomBarDestination = screens.any { it.route == currentDestination?.route }
    if (bottomBarDestination) {
        BottomNavigation(backgroundColor = Color.DarkGray) {
            screens.forEach { screen ->
                AddItem(
                    screen = screen,
                    currentDestination = currentDestination,
                    navController = navController
                )
            }
        }
    }
}

@Composable
fun RowScope.AddItem(
    screen: BottomBarScreen,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    BottomNavigationItem(
        label = {
            Text(text = screen.title, color= Color.White)
        },
        icon = {
            Icon(
                imageVector = screen.icon,
                contentDescription = "Navigation Icon",
                tint= Color.White
            )
        },
        selected = currentDestination?.hierarchy?.any {
            it.route == screen.route
        } == true,
        unselectedContentColor = LocalContentColor.current.copy(alpha = ContentAlpha.disabled),
        onClick = {
            navController.navigate(screen.route) {
                popUpTo(navController.graph.findStartDestination().id)
            }
        }
    )
}