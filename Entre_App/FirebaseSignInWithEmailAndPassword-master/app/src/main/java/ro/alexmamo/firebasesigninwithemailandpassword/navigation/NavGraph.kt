package ro.alexmamo.firebasesigninwithemailandpassword.navigation

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import ro.alexmamo.firebasesigninwithemailandpassword.myapplication.SportFacilityViewModel
import ro.alexmamo.firebasesigninwithemailandpassword.myapplication.TicketTypeViewModel
import ro.alexmamo.firebasesigninwithemailandpassword.myapplication.User
import ro.alexmamo.firebasesigninwithemailandpassword.myapplication.UserViewModel
import ro.alexmamo.firebasesigninwithemailandpassword.navigation.Screen.*
import ro.alexmamo.firebasesigninwithemailandpassword.presentation.HomeScreen
import ro.alexmamo.firebasesigninwithemailandpassword.presentation.forgot_password.ForgotPasswordScreen
import ro.alexmamo.firebasesigninwithemailandpassword.presentation.profile.ProfileScreen
import ro.alexmamo.firebasesigninwithemailandpassword.presentation.sign_in.SignInScreen
import ro.alexmamo.firebasesigninwithemailandpassword.presentation.sign_up.SignUpScreen
import ro.alexmamo.firebasesigninwithemailandpassword.presentation.verify_email.VerifyEmailScreen

@Composable
@ExperimentalAnimationApi
@ExperimentalComposeUiApi
fun NavGraph(
    navController: NavHostController,
     spfvm:SportFacilityViewModel,
     ttvm:TicketTypeViewModel,
     uvm:UserViewModel

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
                    val adduser:User=User(email=FirebaseAuth.getInstance().currentUser?.email , role="Guest")


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
            HomeScreen(navcontroller = rememberNavController(), sportFacilityViewModel = spfvm, ticketTypeViewModel = ttvm, userViewModel = uvm)
        }
    }
}