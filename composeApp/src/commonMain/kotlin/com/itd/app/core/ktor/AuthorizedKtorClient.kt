package com.itd.app.core.ktor

import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpSend
import io.ktor.client.plugins.plugin
import io.ktor.client.request.headers
import io.ktor.http.HttpStatusCode
import io.ktor.http.auth.AuthScheme
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

fun authorizedKtorClient(tokenHolder: TokenHolder, refresher: TokenRefresher): HttpClient {
    val client = HttpClient(HttpEngineFactory().createEngine()) {
        commonConfig()
    }
    authInterceptor(client = client, tokenHolder = tokenHolder, refresher = refresher)
    return client
}

private fun authInterceptor(
    client: HttpClient,
    tokenHolder: TokenHolder,
    refresher: TokenRefresher
) {
    val mutex = Mutex()
    client.plugin(HttpSend).intercept { request ->
        val tokens = tokenHolder.getTokens()
        val access = tokens?.accessToken
            ?: error("Access token is missing. User may need to log in again.")
        val refresh = tokens.refreshToken
        // Обычный запрос
        request.headers {
            append("Authorization", "${AuthScheme.Bearer} $access")
        }
        val originalCall = execute(request)
        if (originalCall.response.status == HttpStatusCode.Unauthorized) {
            // Повторная попытка с авторизацией
            val newAccessToken = mutex.withLock {
                val currentTokens = tokenHolder.getTokens()
                val newTokens = if (currentTokens == tokens) {
                    refresher.refresh(refresh)
                } else {
                    currentTokens
                }
                tokenHolder.setTokens(newTokens)
                newTokens?.accessToken!!
            }
            request.headers {
                set("Authorization", "${AuthScheme.Bearer} $newAccessToken")
            }
            execute(request)
        } else {
            originalCall
        }
    }
}