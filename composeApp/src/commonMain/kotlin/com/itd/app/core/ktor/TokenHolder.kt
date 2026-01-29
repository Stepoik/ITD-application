package com.itd.app.core.ktor

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class TokenHolder(
    private val dataStore: DataStore<Preferences>
) {
    val tokens = dataStore.data.map {
        val accessToken = it[TokenScheme.ACCESS_TOKEN] ?: return@map null
        val refreshToken = it[TokenScheme.REFRESH_TOKEN] ?: return@map null
        BearerTokens(accessToken = accessToken, refreshToken = refreshToken)
    }

    suspend fun getTokens(): BearerTokens? {
        val data = dataStore.data.first()
        val accessToken = data[TokenScheme.ACCESS_TOKEN] ?: return null
        val refreshToken = data[TokenScheme.REFRESH_TOKEN] ?: return null
        return BearerTokens(accessToken = accessToken, refreshToken = refreshToken)
    }

    suspend fun setTokens(tokens: BearerTokens?) {
        dataStore.edit { prefs ->
            if (tokens == null) {
                prefs.remove(TokenScheme.ACCESS_TOKEN)
                prefs.remove(TokenScheme.REFRESH_TOKEN)
            } else {
                prefs[TokenScheme.ACCESS_TOKEN] = tokens.accessToken
                prefs[TokenScheme.REFRESH_TOKEN] = tokens.refreshToken
            }
        }
    }
}

private object TokenScheme {
    val ACCESS_TOKEN = stringPreferencesKey("access_token")
    val REFRESH_TOKEN = stringPreferencesKey("refresh_token")
}