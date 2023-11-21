package com.entree.entreeapp.presentation.forgot_password.components

import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import com.entree.entreeapp.components.BackIcon
import com.entree.entreeapp.core.Constants.FORGOT_PASSWORD_SCREEN

@Composable
fun ForgotPasswordTopBar(
    navigateBack: () -> Unit
) {
    TopAppBar (
        title = {
            Text(
                text = FORGOT_PASSWORD_SCREEN
            )
        },
        navigationIcon = {
            BackIcon(
                navigateBack = navigateBack
            )
        }
    )
}