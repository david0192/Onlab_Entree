package com.entree.entreeapp.presentation.sign_up.components

import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.entree.entreeapp.components.BackIcon
import com.entree.entreeapp.core.Constants.SIGN_UP_SCREEN

@Composable
fun SignUpTopBar(
    navigateBack: () -> Unit
) {
    TopAppBar (
        title = {
            Text(
                text = SIGN_UP_SCREEN, color=Color.White
            )
        },
        backgroundColor = Color.DarkGray,
        navigationIcon = {
            BackIcon(
                navigateBack = navigateBack
            )
        }
    )
}