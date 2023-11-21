package com.entree.entreeapp.presentation.forgot_password.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.sp
import com.entree.entreeapp.components.EmailField
import com.entree.entreeapp.components.SmallSpacer
import com.entree.entreeapp.core.Constants.NO_VALUE
import com.entree.entreeapp.core.Constants.RESET_PASSWORD

@Composable
fun ForgotPasswordContent(
    padding: PaddingValues,
    sendPasswordResetEmail: (email: String) -> Unit,
) {
    var email by rememberSaveable(
        stateSaver = TextFieldValue.Saver
    ) { mutableStateOf(TextFieldValue(NO_VALUE)) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        EmailField(
            email = email,
            onEmailValueChange = { newValue ->
                email = newValue
            }
        )
        SmallSpacer()
        Button(
            onClick = {
                sendPasswordResetEmail(email.text)
            }
        ) {
            Text(
                text = RESET_PASSWORD,
                fontSize = 15.sp
            )
        }
    }
}