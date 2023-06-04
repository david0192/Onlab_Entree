package com.entree.entreeapp.presentation.forgot_password.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.entree.entreeapp.components.ProgressBar
import com.entree.entreeapp.core.Utils.Companion.print
import com.entree.entreeapp.domain.model.Response.*
import com.entree.entreeapp.presentation.forgot_password.ForgotPasswordViewModel

@Composable
fun ForgotPassword(
    viewModel: ForgotPasswordViewModel = hiltViewModel(),
    navigateBack: () -> Unit,
    showResetPasswordMessage: () -> Unit,
    showErrorMessage: (errorMessage: String?) -> Unit
) {
    when(val sendPasswordResetEmailResponse = viewModel.sendPasswordResetEmailResponse) {
        is Loading -> ProgressBar()
        is Success -> {
            val isPasswordResetEmailSent = sendPasswordResetEmailResponse.data
            LaunchedEffect(isPasswordResetEmailSent) {
                if (isPasswordResetEmailSent) {
                    navigateBack()
                    showResetPasswordMessage()
                }
            }
        }
        is Failure -> sendPasswordResetEmailResponse.apply {
            LaunchedEffect(e) {
                print(e)
                showErrorMessage(e.message)
            }
        }
    }
}