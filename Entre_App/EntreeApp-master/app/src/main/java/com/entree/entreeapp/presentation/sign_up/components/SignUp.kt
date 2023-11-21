package com.entree.entreeapp.presentation.sign_up.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.entree.entreeapp.components.ProgressBar
import com.entree.entreeapp.core.Utils.Companion.print
import com.entree.entreeapp.domain.model.Response.*
import com.entree.entreeapp.presentation.sign_up.SignUpViewModel

@Composable
fun SignUp(
    viewModel: SignUpViewModel = hiltViewModel(),
    sendEmailVerification: () -> Unit,
    showVerifyEmailMessage: () -> Unit
) {
    when(val signUpResponse = viewModel.signUpResponse) {
        is Loading -> ProgressBar()
        is Success -> {
            val isUserSignedUp = signUpResponse.data
            LaunchedEffect(isUserSignedUp) {
                if (isUserSignedUp) {
                    sendEmailVerification()
                    showVerifyEmailMessage()
                }
            }
        }
        is Failure -> signUpResponse.apply {
            LaunchedEffect(e) {
                print(e)
            }
        }
    }
}