package com.itd.app.uikit.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Icons.Notification: ImageVector
    get() {
        if (_notification != null) return _notification!!
        
        _notification = ImageVector.Builder(
            name = "notification",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(
                fill = SolidColor(Color.Black),
                pathFillType = PathFillType.EvenOdd
            ) {
                moveTo(19.742f, 13.807f)
                curveToRelative(-0.86f, -1.832f, -0.837f, -2.52f, -0.798f, -3.773f)
                curveToRelative(0.01f, -0.296f, 0.02f, -0.617f, 0.02f, -0.986f)
                curveToRelative(0f, -2.926f, -2.16f, -7.048f, -6.963f, -7.048f)
                curveToRelative(-4.804f, 0f, -6.965f, 4.122f, -6.965f, 7.048f)
                curveToRelative(0f, 0.368f, 0.01f, 0.69f, 0.02f, 0.986f)
                curveToRelative(0.04f, 1.252f, 0.062f, 1.941f, -0.807f, 3.797f)
                curveToRelative(-0.372f, 0.928f, -0.327f, 1.73f, 0.135f, 2.382f)
                curveTo(5.492f, 17.783f, 8.7f, 18f, 12f, 18f)
                curveToRelative(3.3f, 0f, 6.508f, -0.216f, 7.616f, -1.787f)
                curveToRelative(0.463f, -0.653f, 0.508f, -1.454f, 0.125f, -2.406f)
                close()
                moveTo(15.056f, 19.005f)
                curveToRelative(-1.848f, 0.193f, -3.852f, 0.192f, -6.13f, -0.002f)
                curveToRelative(-0.34f, -0.026f, -0.678f, 0.143f, -0.835f, 0.437f)
                arcToRelative(0.763f, 0.763f, 0f, false, false, 0.125f, 0.893f)
                curveTo(9.236f, 21.407f, 10.578f, 22f, 11.994f, 22f)
                horizontalLineToRelative(0.002f)
                curveToRelative(1.42f, 0f, 2.765f, -0.592f, 3.788f, -1.667f)
                arcToRelative(0.765f, 0.765f, 0f, false, false, 0.122f, -0.9f)
                curveToRelative(-0.162f, -0.294f, -0.495f, -0.458f, -0.85f, -0.428f)
                close()
            }
        }.build()
        
        return _notification!!
    }

private var _notification: ImageVector? = null

