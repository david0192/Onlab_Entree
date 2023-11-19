package com.entree.entreeapp.presentation.start_destination

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.entree.entreeapp.R
import com.entree.entreeapp.components.TopBar
import com.entree.entreeapp.presentation.profile.ProfileViewModel

@Composable
fun StartScreen(){
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.entreelogo),
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )
    }
}