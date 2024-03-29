package com.entree.entreeapp.presentation.sign_in.components

import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.entree.entreeapp.core.Constants.SIGN_IN_SCREEN

@Composable
fun SignInTopBar() {
    TopAppBar (
        backgroundColor = Color.DarkGray,
        title = {
            Text(
                text = SIGN_IN_SCREEN,color=Color.White
            )
        },

    )
}