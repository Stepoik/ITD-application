package com.itd.app.uikit.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Icons.Search: ImageVector
    get() {
        if (_search != null) return _search!!
        
        _search = ImageVector.Builder(
            name = "search",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(
                stroke = SolidColor(Color.Black),
                strokeLineWidth = 3f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round
            ) {
                moveToRelative(19.5f, 19.5f)
                lineToRelative(-3f, -3f)
                moveTo(11f, 4.5f)
                arcToRelative(6.5f, 6.5f, 0f, true, true, 0f, 13f)
                arcToRelative(6.5f, 6.5f, 0f, false, true, 0f, -13f)
                close()
            }
        }.build()
        
        return _search!!
    }

private var _search: ImageVector? = null

