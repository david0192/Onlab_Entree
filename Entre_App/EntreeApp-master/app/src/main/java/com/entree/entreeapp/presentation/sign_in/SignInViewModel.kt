package com.entree.entreeapp.presentation.sign_in

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.entree.entreeapp.apiservice.APIService
import com.entree.entreeapp.components.ProgressBar
import com.entree.entreeapp.core.Utils
import com.entree.entreeapp.domain.model.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import com.entree.entreeapp.domain.model.Response.*
import com.entree.entreeapp.domain.repository.AuthRepository
import com.entree.entreeapp.domain.repository.RoleResponse
import com.entree.entreeapp.domain.repository.SignInResponse
import kotlinx.coroutines.async
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val repo: AuthRepository
): ViewModel() {
    var signInResponse by mutableStateOf<SignInResponse>(Success(false))
        private set
    var roleResponse by mutableStateOf<RoleResponse>(Success(false))
        private set

    fun signInWithEmailAndPassword(email: String, password: String) = viewModelScope.launch {
        signInResponse = Loading
        roleResponse = Loading
        val roleDeferred = async { repo.getRoleByEmail(email) }
        val roleResult = roleDeferred.await()
        signInResponse = if(roleResult is Failure){
            roleResult
        } else{
            repo.firebaseSignInWithEmailAndPassword(email, password)
        }
    }
}