package com.entree.entreeapp.data_key_value_store

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.entree.entreeapp.data_key_value_store.DataKeyValueStore.PreferenceKeys.role
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_data")

class DataKeyValueStore (val context: Context){
    private object PreferenceKeys{
        val role: Preferences.Key<String> = stringPreferencesKey("user_role")
    }

    suspend fun updateRole(value: String){
        context.dataStore.edit{ user_data ->
            user_data[role] = value
        }
    }

    fun getRole(): Flow<String> {
        return context.dataStore.data.map {  preferences ->
            preferences[role] ?: "" }
    }
}