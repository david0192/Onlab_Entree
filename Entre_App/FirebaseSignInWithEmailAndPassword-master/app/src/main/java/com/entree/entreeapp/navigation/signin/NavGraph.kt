package com.entree.entreeapp.navigation.signin

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.firebase.auth.FirebaseAuth
import com.entree.entreeapp.apiservice.User
import com.entree.entreeapp.presentation.sportfacilities.SportFacilityViewModel
import com.entree.entreeapp.presentation.sportfacilities.TicketTypeViewModel
import com.entree.entreeapp.presentation.profile.UserViewModel
import com.entree.entreeapp.navigation.Screen.*
import com.entree.entreeapp.presentation.IntWrapper
import com.entree.entreeapp.presentation.forgot_password.ForgotPasswordScreen
import com.entree.entreeapp.presentation.home.HomeScreen
import com.entree.entreeapp.presentation.sign_in.SignInScreen
import com.entree.entreeapp.presentation.sign_up.SignUpScreen
import com.entree.entreeapp.presentation.verify_email.VerifyEmailScreen

@Composable
@ExperimentalAnimationApi
@ExperimentalComposeUiApi
fun NavGraph(
    navController: NavHostController,
    spfvm: SportFacilityViewModel,
    ttvm: TicketTypeViewModel,
    uvm: UserViewModel,
    boughtTicketTypeId:IntWrapper
) {
    AnimatedNavHost(
        navController = navController,
        startDestination = SignInScreen.route,
        enterTransition = {EnterTransition.None },
        exitTransition = { ExitTransition.None }
    ) {
        composable(
            route = SignInScreen.route
        ) {
            SignInScreen(
                navigateToForgotPasswordScreen = {
                    navController.navigate(ForgotPasswordScreen.route)
                },
                navigateToSignUpScreen = {
                    navController.navigate(SignUpScreen.route)
                }
            )
        }
        composable(
            route = ForgotPasswordScreen.route
        ) {
            ForgotPasswordScreen(
                navigateBack = {
                    navController.popBackStack()
                }
            )
        }
        composable(
            route = SignUpScreen.route
        ) {
            SignUpScreen(
                navigateBack = {
                    navController.popBackStack()
                }
            )
        }
        composable(
            route = VerifyEmailScreen.route
        ) {
            VerifyEmailScreen(
                navigateToProfileScreen = {
                    val adduser: User =User(email=FirebaseAuth.getInstance().currentUser?.email , role="Guest")


                     uvm.addGuestUser(adduser)
                    navController.navigate(ProfileScreen.route) {
                        popUpTo(navController.graph.id) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(
            route = ProfileScreen.route
        ) {
            HomeScreen(navcontroller = rememberNavController(), sportFacilityViewModel = spfvm, ticketTypeViewModel = ttvm, userViewModel = uvm, boughtTicketTypeId=boughtTicketTypeId)
        }
    }
}