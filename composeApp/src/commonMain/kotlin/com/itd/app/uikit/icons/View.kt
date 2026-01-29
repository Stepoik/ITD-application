package com.itd.app.uikit.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Icons.View: ImageVector
    get() {
        if (_view != null) return _view!!
        
        _view = ImageVector.Builder(
            name = "view",
            defaultWidth = 18.dp,
            defaultHeight = 18.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(
                stroke = SolidColor(Color.Black),
                strokeLineWidth = 2f,
                strokeLineJoin = StrokeJoin.Miter
            ) {
                moveTo(1f, 12f)
                reflectiveCurveToRelative(4f, -8f, 11f, -8f)
                reflectiveCurveToRelative(11f, 8f, 11f, 8f)
                reflectiveCurveToRelative(-4f, 8f, -11f, 8f)
                reflectiveCurveToRelative(-11f, -8f, -11f, -8f)
                close()
            }
            path(
                stroke = SolidColor(Color.Black),
                strokeLineWidth = 2f,
                strokeLineJoin = StrokeJoin.Miter
            ) {
                moveTo(15f, 12f)
                arcTo(3f, 3f, 0f, false, true, 12f, 15f)
                arcTo(3f, 3f, 0f, false, true, 9f, 12f)
                arcTo(3f, 3f, 0f, false, true, 15f, 12f)
                close()
            }
        }.build()
        
        return _view!!
    }

private var _view: ImageVector? = null

