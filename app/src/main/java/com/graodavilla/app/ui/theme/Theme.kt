package com.graodavilla.app.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.material3.Typography

//paleta de cores
val CoffeeBg=Color(0xFF3B3B3B)  //fundo cinza escuro
val CardBg=Color(0xFF4A4A4A)  //cards
val AccentBeige=Color(0xFFC9A78F)  //barra inferior
val DividerGray=Color(0xFF9C9C9C)  //divisores
val TextPrimary=Color(0xFFFFFFFF)
val TextSecondary=Color(0xFFE0E0E0)

private val darkScheme = darkColorScheme(
    primary = AccentBeige,
    background = CoffeeBg,
    surface = CoffeeBg,
    surfaceVariant = CardBg,
    onPrimary = Color.Black,
    onBackground = TextPrimary,
    onSurface = TextPrimary
)

val GVTypography = Typography(
    titleLarge = Typography().titleLarge.copy(fontWeight = FontWeight.Bold),
    bodyMedium = Typography().bodyMedium
)

@Composable
fun GraoDaVillaTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = darkScheme,
        typography  = GVTypography,
        content = content
    )
}