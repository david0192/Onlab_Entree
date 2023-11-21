package com.entree.entreeapp.data.repository

import android.annotation.SuppressLint
import com.entree.entreeapp.apiservice.APIService
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.tasks.await
import com.entree.entreeapp.domain.model.Response.Failure
import com.entree.entreeapp.domain.model.Response.Success
import com.entree.entreeapp.domain.repository.*
import javax.inject.Inject
import javax.inject.Singleton
import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.entree.entreeapp.EntreeApp
import com.entree.entreeapp.data_key_value_store.DataKeyValueStore
import com.entree.entreeapp.enums.Roles
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.*

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val context: Context
) : AuthRepository {
    override val currentUser get() = auth.currentUser
    private val dataKeyValueStore: DataKeyValueStore = DataKeyValueStore(context)

    override suspend fun firebaseSignUpWithEmailAndPassword(
        email: String, password: String
    ): SignUpResponse {
        return try {
            auth.createUserWithEmailAndPassword(email, password).await()
            Success(true)
        } catch (e: Exception) {
            Failure(e)
        }
    }

    override suspend fun sendEmailVerification(): SendEmailVerificationResponse {
        return try {
            auth.currentUser?.sendEmailVerification()?.await()
            Success(true)
        } catch (e: Exception) {
            Failure(e)
        }
    }

    override suspend fun firebaseSignInWithEmailAndPassword(
        email: String, password: String
    ): SignInResponse {
        return try {
            auth.signInWithEmailAndPassword(email, password).await()
            Success(true)
        } catch (e: Exception) {
            Failure(e)
        }
    }

    override suspend fun reloadFirebaseUser(): ReloadUserResponse {
        return try {
            auth.currentUser?.reload()?.await()
            Success(true)
        } catch (e: Exception) {
            Failure(e)
        }
    }

    override suspend fun sendPasswordResetEmail(email: String): SendPasswordResetEmailResponse {
        return try {
            auth.sendPasswordResetEmail(email).await()
            Success(true)
        } catch (e: Exception) {
            Failure(e)
        }
    }

    override fun signOut() = auth.signOut()

    override suspend fun revokeAccess(): RevokeAccessResponse {
        return try {
            auth.currentUser?.delete()?.await()
            Success(true)
        } catch (e: Exception) {
            Failure(e)
        }
    }

    override fun getAuthState(viewModelScope: CoroutineScope) = callbackFlow {
        val authStateListener = AuthStateListener { auth ->
            trySend(auth.currentUser == null)
        }
        auth.addAuthStateListener(authStateListener)
        awaitClose {
            auth.removeAuthStateListener(authStateListener)
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), auth.currentUser == null)

    @SuppressLint("SuspiciousIndentation")
    override suspend fun getRoleByEmail(email: String?): RoleResponse{
        val apiService = APIService.getInstance()
        return try {
            var roleId:Int = apiService.getRoleIdByEmail(email)
            val coroutineScope = CoroutineScope(Dispatchers.Default)
            val result = coroutineScope.async {
                if (roleId != null) {
                    dataKeyValueStore.updateRole(roleId)
                } else {
                    dataKeyValueStore.updateRole(0)
                }
            }
            result.await()

            Success(true)
        } catch (e: Exception) {
            Failure(e)
        }
    }

    override suspend fun getAuthorizationRole(): Int {
        return dataKeyValueStore.getUserRole().firstOrNull() ?: 0
    }
}