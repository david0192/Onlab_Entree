package com.example.entreeapp

import android.net.wifi.hotspot2.pps.HomeSp
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

@Composable
fun SetUpNavGraph(
    navController: NavHostController,
     ttvm:TicketTypeViewModel,
    spfvm:SportFacilityViewModel
){
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
    ){
        composable(
            route=Screen.Home.route,
        ){
            SportFacilityView(vm = spfvm, navController)

            /*https://www.youtube.com/watch?v=glyqjzkc4fk*/
        }

        composable(
            route=Screen.Profile.route,
        ){
            Profile()
        }

        composable(
            route=Screen.Detail.route,
            arguments= listOf(navArgument("id"){
                type=NavType.IntType
            })
        ){

            backStackEntry->
            SportFacilityDetails(backStackEntry.arguments?.getInt("id"), vm=ttvm)
        }
    }
}

