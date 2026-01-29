package com.itd.app.uikit.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Icons.Close: ImageVector
    get() {
        if (_close != null) return _close!!
        
        _close = ImageVector.Builder(
            name = "close",
            defaultWidth = 14.dp,
            defaultHeight = 14.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(
                stroke = SolidColor(Color.Black),
                strokeLineWidth = 2f,
                strokeLineJoin = StrokeJoin.Miter
            ) {
                moveTo(18f, 6f)
                lineTo(6f, 18f)
            }
            path(
                stroke = SolidColor(Color.Black),
                strokeLineWidth = 2f,
                strokeLineJoin = StrokeJoin.Miter
            ) {
                moveTo(6f, 6f)
                lineTo(18f, 18f)
            }
        }.build()
        
        return _close!!
    }

private var _close: ImageVector? = null

