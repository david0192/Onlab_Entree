package ro.alexmamo.firebasesigninwithemailandpassword.navigation.signin

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
import ro.alexmamo.firebasesigninwithemailandpassword.apiservice.User
import ro.alexmamo.firebasesigninwithemailandpassword.presentation.sportfacilities.SportFacilityViewModel
import ro.alexmamo.firebasesigninwithemailandpassword.presentation.sportfacilities.TicketTypeViewModel
import ro.alexmamo.firebasesigninwithemailandpassword.presentation.profile.UserViewModel
import ro.alexmamo.firebasesigninwithemailandpassword.navigation.Screen.*
import ro.alexmamo.firebasesigninwithemailandpassword.myapplication.HomeScreen
import ro.alexmamo.firebasesigninwithemailandpassword.presentation.IntWrapper
import ro.alexmamo.firebasesigninwithemailandpassword.presentation.forgot_password.ForgotPasswordScreen
import ro.alexmamo.firebasesigninwithemailandpassword.presentation.sign_in.SignInScreen
import ro.alexmamo.firebasesigninwithemailandpassword.presentation.sign_up.SignUpScreen
import ro.alexmamo.firebasesigninwithemailandpassword.presentation.verify_email.VerifyEmailScreen

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