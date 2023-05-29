package ro.alexmamo.firebasesigninwithemailandpassword.myapplication

import android.net.wifi.hotspot2.pps.HomeSp
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import ro.alexmamo.firebasesigninwithemailandpassword.myapplication.MyProfile
import ro.alexmamo.firebasesigninwithemailandpassword.myapplication.SportFacilityDetails
import ro.alexmamo.firebasesigninwithemailandpassword.myapplication.SportFacilityView
import ro.alexmamo.firebasesigninwithemailandpassword.presentation.IntWrapper

@Composable
fun SetUpNavGraph(
    navController: NavHostController,
     ttvm:TicketTypeViewModel,
    spfvm:SportFacilityViewModel,
    uvm:UserViewModel,
    boughtTicketTypeId:IntWrapper
){
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
    ){
        composable(
            route=Screen.Home.route,
        ){
            SportFacilityView(vm = spfvm, navController)
        }

        composable(
            route=Screen.Profile.route,
        ){
            MyProfile(ticketTypeViewModel = ttvm,navcontroller = navController )
        }

        composable(
            route=Screen.Detail.route,
            arguments= listOf(navArgument("id"){
                type=NavType.IntType
            })
        ){

            backStackEntry->
            SportFacilityDetails(backStackEntry.arguments?.getInt("id"), vm=ttvm, navcontroller = navController)
        }

        composable(
            route=Screen.Checkout.route ,
            arguments= listOf(navArgument("ticketTypeId"){
                type=NavType.IntType
            }, navArgument("amount"){type=NavType.IntType})
        ){ backStackEntry->
            CheckoutScreen(backStackEntry.arguments?.getInt("ticketTypeId"), backStackEntry.arguments?.getInt("amount"), navController, ttvm=ttvm, boughtTicketTypeId=boughtTicketTypeId)
        }

        composable(
            route = Screen.QrCode.route
        ) {
            QrCodeScreen("www.google.com")
        }
    }
}

