package com.entree.entreeapp.presentation.sign_up.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.entree.entreeapp.components.ProgressBar
import com.entree.entreeapp.core.Utils.Companion.print
import com.entree.entreeapp.domain.model.Response.*
import com.entree.entreeapp.presentation.sign_up.SignUpViewModel

@Composable
fun SendEmailVerification(
    viewModel: SignUpViewModel = hiltViewModel()
) {
    when(val sendEmailVerificationResponse = viewModel.sendEmailVerificationResponse) {
        is Loading -> ProgressBar()
        is Success -> Unit
        is Failure -> sendEmailVerificationResponse.apply {
            LaunchedEffect(e) {
                print(e)
            }
        }
    }
}