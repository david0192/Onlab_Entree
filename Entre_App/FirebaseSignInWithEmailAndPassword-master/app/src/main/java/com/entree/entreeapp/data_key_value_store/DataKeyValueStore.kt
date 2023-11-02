package com.entree.entreeapp.data_key_value_store

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_data")

class DataKeyValueStore (val context: Context){
    private object PreferenceKeys{
        val roleId: Preferences.Key<Int> = intPreferencesKey("user_roleId")
    }

    suspend fun updateRole(value: Int){
        context.dataStore.edit{ user_data ->
            user_data[PreferenceKeys.roleId] = value
        }
    }

    fun getUserRole(): Flow<Int?> {
        return context.dataStore.data
            .map { preferences ->
                preferences[PreferenceKeys.roleId]
            }
    }
}