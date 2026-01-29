package com.itd.app.uikit

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun ITDTheme(isDarkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    CompositionLocalProvider(
        LocalColors provides colors,
        LocalTypography provides typography,
        LocalTheme provides isDarkTheme,
        LocalIndication provides ripple(),
        content = content
    )
}

object ITDTheme {
    val colors: Colors
        @Composable
        @ReadOnlyComposable
        get() = LocalColors.current

    val typography: Typography
        @Composable
        @ReadOnlyComposable
        get() = LocalTypography.current
}

data class Typography(
    val titleLarge: TextStyle,     // 24
    val titleMedium: TextStyle,    // 18
    val titleSmall: TextStyle,     // 15-18 (чаще жирный)
    val body: TextStyle,           // 15
    val bodyUi: TextStyle,         // 14
    val caption: TextStyle,        // 13
    val small: TextStyle,          // 12
    val micro: TextStyle,          // 10
    val nano: TextStyle,           // 9
    val button: TextStyle,         // 14/700
    val label: TextStyle,          // 14/500 или 12/500
    val inputLarge: TextStyle      // 18 (modal textarea)
)

data class Colors(
    val background: Color,              // --color-background
    val onBackground: Color,            // --color-text
    val onBackgroundInactive: Color,    // --color-text-muted

    val primary: Color,                 // брендовый (в другом css был #1d9bf0)
    val onPrimary: Color,

    val primaryContainer: Color,
    val onPrimaryContainer: Color,

    val secondary: Color,               // --color-text-secondary

    val surface: Color,                 // --color-card
    val onSurface: Color,               // --color-text

    val surfaceVariant: Color,          // логично маппить на itemBg
    val onSurfaceVariant: Color,        // --color-text-secondary

    val error: Color,
    val onError: Color,
    val errorContainer: Color,

    val divider: Color,                 // --color-border-secondary
    val border: Color,                  // --border-color / --color-border

    val like: Color,
    val repost: Color,

    // ДОБАВЛЕНО из фвцф.css :contentReference[oaicite:1]{index=1}
    val itemBg: Color,                  // --color-item-bg
    val inputBg: Color,                 // --color-input-bg
    val backdrop: Color,                // --backdrop-background
    val tabsBg: Color,                  // --color-tabs-bg
    val mobileNavGlow: Color,           // --color-mobile-nav-glow

    // ДОБАВЛЕНО: степени бордера (тоже есть в css) :contentReference[oaicite:2]{index=2}
    val borderLight: Color,             // --color-border-light
    val borderSecondary: Color          // --color-border-secondary (то же что divider, но иногда удобно отдельно)
)

internal val typography
    @Composable
    get() = Typography(
        // 24 / 600 :contentReference[oaicite:8]{index=8}
        titleLarge = TextStyle(fontSize = 24.sp, lineHeight = 36.sp, fontWeight = FontWeight.SemiBold),

        // 18 / 600-700
        titleMedium = TextStyle(fontSize = 18.sp, lineHeight = 27.sp, fontWeight = FontWeight.SemiBold),

        // Автор/имя часто 15px/600 line-height 1.2
        titleSmall = TextStyle(fontSize = 15.sp, lineHeight = 18.sp, fontWeight = FontWeight.SemiBold),

        // Тело поста: 15px line-height 1.5
        body = TextStyle(fontSize = 15.sp, lineHeight = 22.5.sp, fontWeight = FontWeight.Normal),

        // UI/меню/дропдауны: .875rem (14px)
        bodyUi = TextStyle(fontSize = 14.sp, lineHeight = 21.sp, fontWeight = FontWeight.Normal),

        // Время поста: 13px
        caption = TextStyle(fontSize = 13.sp, lineHeight = 16.sp, fontWeight = FontWeight.Normal),

        // Подписи/хинты: .75rem (12px)
        small = TextStyle(fontSize = 12.sp, lineHeight = 16.sp, fontWeight = FontWeight.Normal),

        // Лейбл в моб.навигации: 10px, weight 500 :contentReference[oaicite:15]{index=15}
        micro = TextStyle(fontSize = 10.sp, lineHeight = 10.sp, fontWeight = FontWeight.Medium),

        // Бейджи: 9px, weight 600 :contentReference[oaicite:16]{index=16}
        nano = TextStyle(fontSize = 9.sp, lineHeight = 9.sp, fontWeight = FontWeight.SemiBold),

        // Кнопки часто 14px + 700
        button = TextStyle(fontSize = 14.sp, lineHeight = 16.sp, fontWeight = FontWeight.Bold),

        // label в формах: .875rem + 500 :contentReference[oaicite:18]{index=18}
        label = TextStyle(fontSize = 14.sp, lineHeight = 16.sp, fontWeight = FontWeight.Medium),

        // modal textarea: 18px :contentReference[oaicite:19]{index=19}
        inputLarge = TextStyle(fontSize = 18.sp, lineHeight = 27.sp, fontWeight = FontWeight.Normal)
    )


private fun c(argb: Long) = Color(argb)

internal val colors = Colors(
    background = c(0xFFFFFFFF),          // --color-background
    onBackground = c(0xFF0F1419),        // --color-text
    onBackgroundInactive = c(0xFF8899A6),// --color-text-muted

    primary = c(0xFF1D9BF0),
    onPrimary = c(0xFFFFFFFF),

    primaryContainer = c(0x1A1D9BF0),    // #1d9bf01a
    onPrimaryContainer = c(0xFF0F1419),

    secondary = c(0xFF536471),           // --color-text-secondary

    surface = c(0xFFFFFFFF),             // --color-card
    onSurface = c(0xFF0F1419),

    surfaceVariant = c(0xFFF7F9FA),      // --color-item-bg
    onSurfaceVariant = c(0xFF536471),

    // в этом файле ошибок/лайков/репостов нет, оставляю безопасные дефолты
    error = c(0xFFEF4444),
    onError = c(0xFFFFFFFF),
    errorContainer = c(0x1AEF4444),

    divider = c(0xFFEBEEF0),             // --color-border-secondary
    border = c(0xFFE1E8ED),              // --border-color

    like = c(0xFFFF5050),
    repost = c(0xFF00BA7C),

    itemBg = c(0xFFF7F9FA),              // --color-item-bg
    inputBg = c(0xFFEEF0F1),             // --color-input-bg
    backdrop = c(0xCC000000),            // --backdrop-background
    tabsBg = c(0xFFEEF0F1),              // --color-tabs-bg
    mobileNavGlow = c(0xFFEEF0F1),       // --color-mobile-nav-glow

    borderLight = c(0xFFF7F9FA),         // --color-border-light
    borderSecondary = c(0xFFEBEEF0)      // --color-border-secondary
)

internal val LocalTypography = staticCompositionLocalOf<Typography> { error("No default values") }
internal val LocalColors = staticCompositionLocalOf<Colors> { error("No default values") }
internal val LocalTheme = staticCompositionLocalOf<Boolean> { error("No default values") }