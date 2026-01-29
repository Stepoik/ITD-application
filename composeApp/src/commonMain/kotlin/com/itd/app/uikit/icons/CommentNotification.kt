package com.itd.app.uikit.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Icons.CommentNotification: ImageVector
    get() {
        if (_commentnotification != null) return _commentnotification!!
        
        _commentnotification = ImageVector.Builder(
            name = "commentnotification",
            defaultWidth = 10.dp,
            defaultHeight = 10.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(
                fill = SolidColor(Color.Black)
            ) {
                moveTo(21.99f, 4f)
                curveToRelative(0f, -1.1f, -0.89f, -2f, -1.99f, -2f)
                horizontalLineTo(4f)
                curveToRelative(-1.1f, 0f, -2f, 0.9f, -2f, 2f)
                verticalLineToRelative(12f)
                curveToRelative(0f, 1.1f, 0.9f, 2f, 2f, 2f)
                horizontalLineToRelative(14f)
                lineToRelative(4f, 4f)
                lineToRelative(-0.01f, -18f)
                close()
            }
        }.build()
        
        return _commentnotification!!
    }

private var _commentnotification: ImageVector? = null

