package com.itd.app.uikit.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Icons.Feed: ImageVector
    get() {
        if (_home != null) return _home!!
        
        _home = ImageVector.Builder(
            name = "home",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(
                fill = SolidColor(Color.Black),
                pathFillType = PathFillType.EvenOdd
            ) {
                moveTo(14.595f, 16.187f)
                horizontalLineTo(9.406f)
                arcToRelative(0.657f, 0.657f, 0f, false, true, -0.67f, -0.643f)
                curveToRelative(0f, -0.355f, 0.3f, -0.643f, 0.67f, -0.643f)
                horizontalLineToRelative(5.189f)
                curveToRelative(0.37f, 0f, 0.67f, 0.288f, 0.67f, 0.643f)
                curveToRelative(0f, 0.355f, -0.3f, 0.643f, -0.67f, 0.643f)
                close()
                moveToRelative(3.873f, -9.194f)
                curveToRelative(-0.323f, -0.275f, -0.692f, -0.585f, -1.131f, -0.975f)
                curveToRelative(-0.199f, -0.154f, -0.417f, -0.33f, -0.648f, -0.518f)
                curveTo(15.386f, 4.445f, 13.602f, 3f, 11.975f, 3f)
                curveToRelative(-1.608f, 0f, -3.278f, 1.365f, -4.619f, 2.46f)
                curveToRelative(-0.248f, 0.203f, -0.48f, 0.393f, -0.716f, 0.578f)
                curveToRelative(-0.416f, 0.37f, -0.785f, 0.681f, -1.11f, 0.956f)
                curveTo(3.4f, 8.795f, 3f, 9.267f, 3f, 13.468f)
                curveTo(3f, 21f, 5.267f, 21f, 12f, 21f)
                curveToRelative(6.732f, 0f, 9f, 0f, 9f, -7.532f)
                curveToRelative(0f, -4.201f, -0.4f, -4.674f, -2.532f, -6.475f)
                close()
            }
        }.build()
        
        return _home!!
    }

private var _home: ImageVector? = null

