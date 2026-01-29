package com.itd.app.uikit.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Icons.Comment: ImageVector
    get() {
        if (_comment != null) return _comment!!
        
        _comment = ImageVector.Builder(
            name = "comment",
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
                moveTo(21f, 11.5f)
                arcToRelative(8.38f, 8.38f, 0f, false, true, -0.9f, 3.8f)
                arcToRelative(8.5f, 8.5f, 0f, false, true, -7.6f, 4.7f)
                arcToRelative(8.38f, 8.38f, 0f, false, true, -3.8f, -0.9f)
                lineTo(3f, 21f)
                lineToRelative(1.9f, -5.7f)
                arcToRelative(8.38f, 8.38f, 0f, false, true, -0.9f, -3.8f)
                arcToRelative(8.5f, 8.5f, 0f, false, true, 4.7f, -7.6f)
                arcToRelative(8.38f, 8.38f, 0f, false, true, 3.8f, -0.9f)
                horizontalLineToRelative(0.5f)
                arcToRelative(8.48f, 8.48f, 0f, false, true, 8f, 8f)
                verticalLineToRelative(0.5f)
                close()
            }
        }.build()
        
        return _comment!!
    }

private var _comment: ImageVector? = null

