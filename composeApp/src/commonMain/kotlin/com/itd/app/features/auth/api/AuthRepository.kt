package com.itd.app.features.auth.api

interface AuthRepository {
    suspend fun saveAuthToken(token: String): Result<Any?>

    suspend fun logout(): Result<Any?>
}