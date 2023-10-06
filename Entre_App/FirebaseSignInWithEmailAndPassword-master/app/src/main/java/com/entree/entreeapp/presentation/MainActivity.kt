package com.entree.entreeapp.presentation

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
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
        var isLoading by remember { mutableStateOf(true) }
        var role: String by remember { mutableStateOf("") }
        if (isUserSignedOut) {
            NavigateToSignInScreen()
        }
        else {
            if (viewModel.isEmailVerified) {
                LaunchedEffect(true) {
                    isLoading = true
                    role = viewModel.getRole()
                    isLoading = false
                }
                if(isLoading){
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        CircularProgressIndicator(
                            color = Color(0xff91a4fc),
                            modifier = Modifier.size(32.dp),
                        )
                    }
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

