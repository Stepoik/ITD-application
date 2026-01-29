package com.itd.app.uikit.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Icons.Like: ImageVector
    get() {
        if (_like != null) return _like!!
        
        _like = ImageVector.Builder(
            name = "like",
            defaultWidth = 20.dp,
            defaultHeight = 20.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(
                stroke = SolidColor(Color.Black),
                strokeLineWidth = 2f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round
            ) {
                moveTo(12f, 5.5f)
                curveTo(10.5f, 3.5f, 7.5f, 3f, 5.5f, 4.5f)
                reflectiveCurveTo(2.5f, 9f, 4f, 12f)
                curveToRelative(1.5f, 3f, 8f, 8f, 8f, 8f)
                reflectiveCurveToRelative(6.5f, -5f, 8f, -8f)
                reflectiveCurveToRelative(0f, -6f, -2f, -7.5f)
                reflectiveCurveToRelative(-5f, -1f, -6f, 1f)
            }
        }.build()
        
        return _like!!
    }

private var _like: ImageVector? = null

