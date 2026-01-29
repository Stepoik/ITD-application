package com.itd.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.arkivanov.decompose.defaultComponentContext
import com.arkivanov.decompose.retainedComponent
import com.itd.app.features.root.RootComponent
import org.koin.android.ext.android.getKoin

class MainActivity : ComponentActivity() {
    private val rootComponentFactory = getKoin().get<RootComponent.Factory>()

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        val rootComponent = retainedComponent {
            rootComponentFactory.create(defaultComponentContext())
        }
        setContent {
            App(rootComponent)
        }
    }
}