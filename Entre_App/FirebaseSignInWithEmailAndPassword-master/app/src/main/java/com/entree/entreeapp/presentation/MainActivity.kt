package com.entree.entreeapp.presentation

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.NavHostController
import com.entree.entreeapp.data_key_value_store.DataKeyValueStore
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dagger.hilt.android.AndroidEntryPoint
import com.entree.entreeapp.navigation.Screen.*
import com.google.firebase.auth.FirebaseAuth
import com.razorpay.PaymentResultListener
import com.entree.entreeapp.navigation.signin.NavGraph
import com.entree.entreeapp.presentation.sportfacilities.SportFacilityViewModel
import com.entree.entreeapp.presentation.sportfacilities.TicketTypeViewModel
import com.entree.entreeapp.presentation.profile.UserViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
@ExperimentalAnimationApi
@ExperimentalComposeUiApi
class MainActivity : ComponentActivity(), PaymentResultListener {
    private lateinit var navController: NavHostController
    private lateinit var ttvm: TicketTypeViewModel
    private lateinit var spfvm: SportFacilityViewModel
    private lateinit var uvm: UserViewModel
    var boughtTicketTypeId = IntWrapper(null)
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

    @SuppressLint("CoroutineCreationDuringComposition", "UnrememberedMutableState")
    @Composable
    private fun AuthState() {
        val isUserSignedOut = viewModel.getAuthState().collectAsState().value
        var isLoading by remember { mutableStateOf(false) }
        var role: String by mutableStateOf("")
        if (isUserSignedOut) {
            NavigateToSignInScreen()
        } else {
            if (viewModel.isEmailVerified) {
                isLoading=true
                CoroutineScope(Dispatchers.Default).launch {
                    role = viewModel.getRole()
                    isLoading=false
                }
                if(isLoading){
                    Text("Betöltés...")
                }
                else{
                    if(!role.isNullOrEmpty()){
                        when (role) {
                            "Guest" -> {
                                NavigateToProfileScreen()
                            }
                            "Admin" -> {
                                NavigateToAdminScreen()
                            }
                            else -> {
                                NavigateToProfileScreen()
                            }
                        }
                    }
                    else{
                        NavigateToProfileScreen()
                    }
                }

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

    @SuppressLint("CoroutineCreationDuringComposition")
    @Composable
    private fun NavigateToProfileScreen() = navController.navigate(ProfileScreen.route) {
        popUpTo(navController.graph.id) {
            inclusive = true
        }
    }

    @SuppressLint("CoroutineCreationDuringComposition")
    @Composable
    private fun NavigateToAdminScreen() = navController.navigate(AdminScreen.route) {
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

