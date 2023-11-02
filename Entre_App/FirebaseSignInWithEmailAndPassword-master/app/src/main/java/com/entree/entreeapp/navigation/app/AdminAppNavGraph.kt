package com.entree.entreeapp.navigation.app

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.entree.entreeapp.presentation.admin_site.AdminScreen
import com.entree.entreeapp.presentation.admin_site.AdminScreenViewModel
import com.entree.entreeapp.presentation.admin_site.StatisticsScreen
import com.entree.entreeapp.presentation.admin_site.TicketTypeEditDetails
import com.entree.entreeapp.presentation.sportfacilities.SportFacilityDetails
import com.entree.entreeapp.screen.Screen

@Composable
fun AdminSetUpNavGraph(
    navController: NavHostController,
    avm: AdminScreenViewModel
){
    NavHost(
        navController = navController,
        startDestination = Screen.AdminSportFacility.route,
    ){
        composable(
            route= Screen.AdminSportFacility.route,
        ){
            AdminScreen(avm = avm, navcontroller = navController)
        }

        composable(
            route= Screen.AdminStatistics.route,
        ){
            StatisticsScreen(avm = avm)
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
            TicketTypeEditDetails(avm=avm, id=backStackEntry.arguments?.getInt("id"), navigateTypeId = backStackEntry.arguments?.getInt("NavigateTypeId"))
        }
    }
}