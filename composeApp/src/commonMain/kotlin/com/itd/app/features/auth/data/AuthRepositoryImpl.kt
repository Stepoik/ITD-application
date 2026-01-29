package com.itd.app.features.auth.data

import com.itd.app.features.auth.api.AuthRepository

class AuthRepositoryImpl : AuthRepository {
    override suspend fun saveAuthToken(token: String): Result<Any?> {
        TODO("Not yet implemented")
    }

    override suspend fun logout(): Result<Any?> {
        TODO("Not yet implemented")
    }
}