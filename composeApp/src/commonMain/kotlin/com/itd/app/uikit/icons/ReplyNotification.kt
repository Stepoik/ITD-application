package com.itd.app.uikit.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Icons.ReplyNotification: ImageVector
    get() {
        if (_replynotification != null) return _replynotification!!
        
        _replynotification = ImageVector.Builder(
            name = "replynotification",
            defaultWidth = 10.dp,
            defaultHeight = 10.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(
                fill = SolidColor(Color.Black)
            ) {
                moveTo(10f, 9f)
                verticalLineTo(5f)
                lineToRelative(-7f, 7f)
                lineToRelative(7f, 7f)
                verticalLineToRelative(-4.1f)
                curveToRelative(5f, 0f, 8.5f, 1.6f, 11f, 5.1f)
                curveToRelative(-1f, -5f, -4f, -10f, -11f, -11f)
                close()
            }
        }.build()
        
        return _replynotification!!
    }

private var _replynotification: ImageVector? = null

