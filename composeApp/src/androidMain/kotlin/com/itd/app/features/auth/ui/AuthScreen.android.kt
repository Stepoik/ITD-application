package com.itd.app.features.auth.ui

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.itd.app.di.DEFAULT_KTOR_QUALIFIER
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.header
import io.ktor.client.request.post
import kotlinx.coroutines.launch
import org.json.JSONObject
import org.koin.compose.getKoin
import org.mozilla.geckoview.GeckoRuntime
import org.mozilla.geckoview.GeckoSession
import org.mozilla.geckoview.GeckoView
import org.mozilla.geckoview.WebExtension

@SuppressLint("WrongThread")
@Composable
actual fun WebView(onToken: (accessToken: String, refreshToken: String) -> Unit, modifier: Modifier) {
    val koin = getKoin()
    val coroutineScope = rememberCoroutineScope()
    val client = koin.get<HttpClient>(DEFAULT_KTOR_QUALIFIER)
    AndroidView(factory = {
        GeckoView(it).apply {
            var port: WebExtension.Port? = null
            val runtime = GeckoRuntime.create(it)
            val session = GeckoSession().apply {
                navigationDelegate = object : GeckoSession.NavigationDelegate {
                    override fun onLocationChange(
                        p0: GeckoSession,
                        p1: String?,
                        p2: List<GeckoSession.PermissionDelegate.ContentPermission?>,
                        p3: Boolean
                    ) {
                        if (p1 != "https://итд.com/login") {
                            port?.postMessage(JSONObject())
                        }
                    }
                }
            }
            session.open(runtime)
            runtime.webExtensionController.installBuiltIn(
                "resource://android/assets/extension/"
            ).accept {
                val portDelegate = object : WebExtension.PortDelegate {
                    override fun onPortMessage(message: Any, port: WebExtension.Port) {
                        val refreshToken = (message as? JSONObject)
                            ?.getJSONArray("cookies")
                            ?.getJSONObject(0)
                            ?.getString("value") ?: return
                        coroutineScope.launch {
                            client.post("https://xn--d1ah4a.com/api/v1/auth/refresh") {
                                header("cookie", "refresh_token=$refreshToken")
                            }.body<Map<String, String>>()["accessToken"]?.let {
                                onToken(it, refreshToken)
                            }
                        }
                    }
                }
                val messageDelegate = object : WebExtension.MessageDelegate {
                    override fun onConnect(newPort: WebExtension.Port) {
                        port = newPort
                        port.setDelegate(portDelegate)
                    }
                }
                it?.setMessageDelegate(
                    messageDelegate,
                    "cookie_bridge"
                )
            }
            setSession(session)
        }
    }, modifier = modifier, update = {
        it.session?.loadUri("https://итд.com/login")
    })
}