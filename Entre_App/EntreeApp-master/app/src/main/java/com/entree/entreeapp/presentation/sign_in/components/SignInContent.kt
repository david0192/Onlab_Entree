package com.entree.entreeapp.presentation.sign_in.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.entree.entreeapp.components.EmailField
import com.entree.entreeapp.components.PasswordField
import com.entree.entreeapp.components.SmallSpacer
import com.entree.entreeapp.core.Constants.FORGOT_PASSWORD
import com.entree.entreeapp.core.Constants.NO_ACCOUNT
import com.entree.entreeapp.core.Constants.NO_VALUE
import com.entree.entreeapp.core.Constants.SIGN_IN
import com.entree.entreeapp.core.Constants.VERTICAL_DIVIDER

@Composable
@ExperimentalComposeUiApi
fun SignInContent(
    padding: PaddingValues,
    signIn: (email: String, password: String) -> Unit,
    navigateToForgotPasswordScreen: () -> Unit,
    navigateToSignUpScreen: () -> Unit
) {
    var email by rememberSaveable(
        stateSaver = TextFieldValue.Saver
    ) { mutableStateOf(TextFieldValue(NO_VALUE)) }
    var password by rememberSaveable(
        stateSaver = TextFieldValue.Saver
    ) { mutableStateOf(TextFieldValue(NO_VALUE)) }
    val keyboard = LocalSoftwareKeyboardController.current

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
        PasswordField(
            password = password,
            onPasswordValueChange = { newValue ->
                password = newValue
            }
        )
        SmallSpacer()
        Button(colors = ButtonDefaults.buttonColors(Color.Black),
            onClick = {
                keyboard?.hide()
                signIn(email.text, password.text)
            }
        ) {
            Text(
                text = SIGN_IN,
                fontSize = 15.sp,
                color= Color.White
            )
        }
        Row {
            Text(
                modifier = Modifier.clickable {
                    navigateToForgotPasswordScreen()
                },
                text = FORGOT_PASSWORD,
                fontSize = 15.sp
            )
            Text(
                modifier = Modifier.padding(start = 4.dp, end = 4.dp),
                text = VERTICAL_DIVIDER,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                modifier = Modifier.clickable {
                    navigateToSignUpScreen()
                },
                text = NO_ACCOUNT,
                fontSize = 15.sp
            )
        }
    }
}