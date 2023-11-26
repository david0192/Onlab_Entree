package com.entree.entreeapp.navigation.app

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.entree.entreeapp.presentation.admin_site.*
import com.entree.entreeapp.screen.Screen

@Composable
fun AdminSetUpNavGraph(
    navController: NavHostController,
){
    NavHost(
        navController = navController,
        startDestination = Screen.AdminSportFacility.route,
    ){
        composable(
            route= Screen.AdminSportFacility.route,
        ){
            SportFacilityEditScreen(navcontroller = navController)
        }

        composable(
            route= Screen.AdminStatistics.route,
        ){
            StatisticsScreen()
        }

        composable(
            route=Screen.AdminTicketTypeDetail.route,
            arguments= listOf(navArgument("id"){
                type= NavType.IntType
            }, navArgument("NavigateTypeId"){
                type= NavType.IntType
            })
        ){
                backStackEntry->
            TicketTypeEditDetails(id=backStackEntry.arguments?.getInt("id"), navigateTypeId = backStackEntry.arguments?.getInt("NavigateTypeId"))
        }

        composable(
            route=Screen.AdminTrainerDetail.route,
            arguments= listOf(navArgument("id"){
                type= NavType.IntType
            }, navArgument("NavigateTypeId"){
                type= NavType.IntType
            })
        ){
                backStackEntry->
            TrainerEditDetails(id=backStackEntry.arguments?.getInt("id"), navigateTypeId = backStackEntry.arguments?.getInt("NavigateTypeId"))
        }
    }
}