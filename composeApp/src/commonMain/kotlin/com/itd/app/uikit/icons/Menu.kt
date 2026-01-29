package com.itd.app.uikit.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Icons.Menu: ImageVector
    get() {
        if (_menu != null) return _menu!!
        
        _menu = ImageVector.Builder(
            name = "menu",
            defaultWidth = 18.dp,
            defaultHeight = 18.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(
                fill = SolidColor(Color.Black)
            ) {
                moveTo(14f, 5f)
                arcTo(2f, 2f, 0f, false, true, 12f, 7f)
                arcTo(2f, 2f, 0f, false, true, 10f, 5f)
                arcTo(2f, 2f, 0f, false, true, 14f, 5f)
                close()
            }
            path(
                fill = SolidColor(Color.Black)
            ) {
                moveTo(14f, 12f)
                arcTo(2f, 2f, 0f, false, true, 12f, 14f)
                arcTo(2f, 2f, 0f, false, true, 10f, 12f)
                arcTo(2f, 2f, 0f, false, true, 14f, 12f)
                close()
            }
            path(
                fill = SolidColor(Color.Black)
            ) {
                moveTo(14f, 19f)
                arcTo(2f, 2f, 0f, false, true, 12f, 21f)
                arcTo(2f, 2f, 0f, false, true, 10f, 19f)
                arcTo(2f, 2f, 0f, false, true, 14f, 19f)
                close()
            }
        }.build()
        
        return _menu!!
    }

private var _menu: ImageVector? = null

