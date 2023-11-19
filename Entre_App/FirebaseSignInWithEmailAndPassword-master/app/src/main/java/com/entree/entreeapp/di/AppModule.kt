package com.entree.entreeapp.di

import android.content.Context
import com.entree.entreeapp.FirebaseSignInWithEmailAndPasswordApp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import com.entree.entreeapp.data.repository.AuthRepositoryImpl
import com.entree.entreeapp.domain.repository.AuthRepository
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ViewModelComponent::class)
class AppModule {
    @Provides
    fun provideAuthRepository(@ApplicationContext context: Context): AuthRepository = AuthRepositoryImpl(
        auth = Firebase.auth,
        context=context
    )
}