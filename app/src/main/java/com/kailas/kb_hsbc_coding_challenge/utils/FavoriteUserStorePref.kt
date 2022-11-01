package com.kailas.kb_hsbc_coding_challenge.utils

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

class FavoriteUserStorePref(private val context: Context) {
    private val USER_PREFERENCES_NAME = "favorite_user_preferences"

    private val Context.dataStore by preferencesDataStore(
        name = USER_PREFERENCES_NAME
    )

    companion object {
        val FAVORITE_USERS = stringPreferencesKey("favorite_users")
    }

    suspend fun storeFavoriteUsers(favoriteUsers: String) {
        context.dataStore.edit {
            it[FAVORITE_USERS] = favoriteUsers
        }
    }

    fun getFavoriteUsers() = context.dataStore.data.map {
        it[FAVORITE_USERS] ?: ""
    }
}