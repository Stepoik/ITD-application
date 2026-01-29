package com.itd.app.core.ktor

import com.itd.app.core.utils.Build
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpSend
import io.ktor.client.plugins.plugin
import io.ktor.client.request.headers
import io.ktor.http.HttpHeaders

fun defaultKtorClient(): HttpClient {
    val client = HttpClient(HttpEngineFactory().createEngine()) {
        commonConfig()
    }
    return client.apply {
        userAgentInterceptor()
    }
}

fun HttpClient.userAgentInterceptor() {
    plugin(HttpSend).intercept { request ->
        request.headers {
            append(HttpHeaders.UserAgent, Build.FINGERPRINT)
        }
        execute(request)
    }
}