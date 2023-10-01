package com.entree.entreeapp.navigation

import com.entree.entreeapp.core.Constants.ADMIN_SCREEN
import com.entree.entreeapp.core.Constants.FORGOT_PASSWORD_SCREEN
import com.entree.entreeapp.core.Constants.PROFILE_SCREEN
import com.entree.entreeapp.core.Constants.SIGN_IN_SCREEN
import com.entree.entreeapp.core.Constants.VERIFY_EMAIL_SCREEN
import com.entree.entreeapp.core.Constants.SIGN_UP_SCREEN

sealed class Screen(val route: String) {
    object SignInScreen: Screen(SIGN_IN_SCREEN)
    object ForgotPasswordScreen: Screen(FORGOT_PASSWORD_SCREEN)
    object SignUpScreen: Screen(SIGN_UP_SCREEN)
    object VerifyEmailScreen: Screen(VERIFY_EMAIL_SCREEN)
    object ProfileScreen: Screen(PROFILE_SCREEN)
    object AdminScreen: Screen(ADMIN_SCREEN)
}