package ro.alexmamo.firebasesigninwithemailandpassword.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dagger.hilt.android.AndroidEntryPoint
import ro.alexmamo.firebasesigninwithemailandpassword.navigation.Screen.*
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.razorpay.PaymentResultListener
import ro.alexmamo.firebasesigninwithemailandpassword.navigation.signin.NavGraph
import ro.alexmamo.firebasesigninwithemailandpassword.presentation.sportfacilities.SportFacilityViewModel
import ro.alexmamo.firebasesigninwithemailandpassword.presentation.sportfacilities.TicketTypeViewModel
import ro.alexmamo.firebasesigninwithemailandpassword.presentation.profile.UserViewModel

@AndroidEntryPoint
@ExperimentalAnimationApi
@ExperimentalComposeUiApi
class MainActivity : ComponentActivity(), PaymentResultListener {
    private lateinit var navController: NavHostController
    private lateinit var ttvm: TicketTypeViewModel
    private lateinit var spfvm: SportFacilityViewModel
    private lateinit var uvm: UserViewModel
    var boughtTicketTypeId=IntWrapper(null)
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            navController = rememberAnimatedNavController()
            ttvm= TicketTypeViewModel()
            spfvm=SportFacilityViewModel()
            uvm=UserViewModel()
            NavGraph(
                navController = navController,
                spfvm = spfvm,
                ttvm=ttvm,
                uvm= uvm,
                boughtTicketTypeId=boughtTicketTypeId
            )
            AuthState()
        }
    }

    @Composable
    private fun AuthState() {
        val isUserSignedOut = viewModel.getAuthState().collectAsState().value
        if (isUserSignedOut) {
            NavigateToSignInScreen()
        } else {
            if (viewModel.isEmailVerified) {
                NavigateToProfileScreen()
            } else {
                NavigateToVerifyEmailScreen()
            }
        }
    }

    @Composable
    private fun NavigateToSignInScreen() = navController.navigate(SignInScreen.route) {
        popUpTo(navController.graph.id) {
            inclusive = true
        }
    }

    @Composable
    private fun NavigateToProfileScreen() = navController.navigate(ProfileScreen.route) {
        popUpTo(navController.graph.id) {
            inclusive = true
        }
    }

    @Composable
    private fun NavigateToVerifyEmailScreen() = navController.navigate(VerifyEmailScreen.route) {
        popUpTo(navController.graph.id) {
            inclusive = true
        }
    }

    override fun onPaymentSuccess(p0: String?) {
        ttvm.AddTicketToUser(boughtTicketTypeId.value, email= FirebaseAuth.getInstance().currentUser?.email)
        navController.navigate(ProfileScreen.route)
    }

    override fun onPaymentError(p0: Int, p1: String?) {
        navController.navigate(ProfileScreen.route)
    }
}

class IntWrapper(var value: Int?)

