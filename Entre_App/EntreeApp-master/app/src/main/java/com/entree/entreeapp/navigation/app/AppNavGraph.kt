package com.entree.entreeapp.navigation.app

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.entree.entreeapp.presentation.IntWrapper
import com.entree.entreeapp.presentation.checkout.CheckoutScreen
import com.entree.entreeapp.presentation.profile.MyProfile
import com.entree.entreeapp.presentation.profile.QrCodeScreen
import com.entree.entreeapp.screen.Screen
import com.entree.entreeapp.presentation.sportfacilities.SportFacilityViewModel
import com.entree.entreeapp.presentation.sportfacilities.TicketTypeViewModel
import com.entree.entreeapp.presentation.profile.UserViewModel
import com.entree.entreeapp.presentation.sportfacilities.SportFacilityDetails
import com.entree.entreeapp.presentation.sportfacilities.SportFacilityView

@Composable
fun SetUpNavGraph(
    navController: NavHostController,
    boughtTicketTypeId:IntWrapper
){
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
    ){
        composable(
            route=Screen.Home.route,
        ){
            SportFacilityView(navcontroller=navController)
        }

        composable(
            route=Screen.Profile.route,
        ){
            MyProfile(navcontroller = navController )
        }

        composable(
            route=Screen.Detail.route,
            arguments= listOf(navArgument("id"){
                type=NavType.IntType
            })
        ){

            backStackEntry->
            SportFacilityDetails(backStackEntry.arguments?.getInt("id"), navcontroller = navController)
        }

        composable(
            route=Screen.Checkout.route ,
            arguments= listOf(navArgument("ticketTypeId"){
                type=NavType.IntType
            }, navArgument("amount"){type=NavType.IntType})
        ){ backStackEntry->
            CheckoutScreen(backStackEntry.arguments?.getInt("ticketTypeId"), backStackEntry.arguments?.getInt("amount"), boughtTicketTypeId=boughtTicketTypeId)
        }

        composable(
            route = Screen.QrCode.route
        ) {
            QrCodeScreen("www.google.com")
        }
    }
}

