package com.entree.entreeapp.presentation.sign_up

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.entree.entreeapp.core.Constants.VERIFY_EMAIL_MESSAGE
import com.entree.entreeapp.core.Utils.Companion.showMessage
import com.entree.entreeapp.presentation.sign_up.components.SendEmailVerification
import com.entree.entreeapp.presentation.sign_up.components.SignUp
import com.entree.entreeapp.presentation.sign_up.components.SignUpContent
import com.entree.entreeapp.presentation.sign_up.components.SignUpTopBar

@Composable
@ExperimentalComposeUiApi
fun SignUpScreen(
    viewModel: SignUpViewModel = hiltViewModel(),
    navigateBack: () -> Unit
) {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            SignUpTopBar(
                navigateBack = navigateBack
            )
        },
        content = { padding ->
            SignUpContent(
                padding = padding,
                signUp = { email, password ->
                    viewModel.signUpWithEmailAndPassword(email, password)
                },
                navigateBack = navigateBack
            )
        }
    )

    SignUp(
        sendEmailVerification = {
            viewModel.sendEmailVerification()
        },
        showVerifyEmailMessage = {
            showMessage(context, VERIFY_EMAIL_MESSAGE)
        }
    )

    SendEmailVerification()
}