package com.itd.app.uikit.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Icons.Verification: ImageVector
    get() {
        if (_verification != null) return _verification!!
        
        _verification = ImageVector.Builder(
            name = "verification",
            defaultWidth = 16.dp,
            defaultHeight = 16.dp,
            viewportWidth = 36f,
            viewportHeight = 36f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF0080FF))
            ) {
                moveTo(15.13f, 1.848f)
                curveToRelative(1.46f, -2.514f, 5.057f, -2.45f, 6.429f, 0.115f)
                lineToRelative(1.253f, 2.343f)
                arcToRelative(3.677f, 3.677f, 0f, false, false, 3.762f, 1.926f)
                lineToRelative(2.597f, -0.373f)
                curveToRelative(2.842f, -0.408f, 5.036f, 2.493f, 3.92f, 5.182f)
                lineTo(32.07f, 13.5f)
                arcToRelative(3.804f, 3.804f, 0f, false, false, 0.865f, 4.192f)
                lineToRelative(1.906f, 1.833f)
                curveToRelative(2.086f, 2.006f, 1.224f, 5.56f, -1.54f, 6.348f)
                lineToRelative(-2.525f, 0.721f)
                curveToRelative(-1.484f, 0.424f, -2.554f, 1.74f, -2.683f, 3.302f)
                lineToRelative(-0.22f, 2.658f)
                curveToRelative(-0.242f, 2.91f, -3.51f, 4.44f, -5.84f, 2.734f)
                lineToRelative(-2.13f, -1.558f)
                arcToRelative(3.644f, 3.644f, 0f, false, false, -4.21f, -0.075f)
                lineToRelative(-2.181f, 1.482f)
                curveToRelative(-2.387f, 1.622f, -5.601f, -0.023f, -5.743f, -2.94f)
                lineToRelative(-0.129f, -2.664f)
                curveToRelative(-0.076f, -1.566f, -1.1f, -2.919f, -2.568f, -3.395f)
                lineToRelative(-2.499f, -0.81f)
                curveToRelative(-2.735f, -0.887f, -3.474f, -4.469f, -1.32f, -6.4f)
                lineToRelative(1.967f, -1.763f)
                arcToRelative(3.801f, 3.801f, 0f, false, false, 1.008f, -4.16f)
                lineToRelative(-0.935f, -2.492f)
                curveTo(2.27f, 7.785f, 4.563f, 4.963f, 7.39f, 5.472f)
                lineToRelative(2.582f, 0.465f)
                arcToRelative(3.673f, 3.673f, 0f, false, false, 3.826f, -1.791f)
                lineToRelative(1.333f, -2.298f)
                close()
            }
            path(
                stroke = SolidColor(Color(0xFFfff)),
                strokeLineWidth = 3f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round
            ) {
                moveToRelative(24f, 15f)
                lineToRelative(-8f, 8f)
                lineToRelative(-4f, -4f)
            }
        }.build()
        
        return _verification!!
    }

private var _verification: ImageVector? = null

