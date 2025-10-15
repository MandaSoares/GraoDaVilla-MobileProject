package com.graodavilla.app.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val CoffeeBg = Color(0xFF2F2F2F)
private val Accent   = Color(0xFF9B7A62)

@Composable
fun OrderConfirmedScreen(
    qualified: Boolean,
    onGoHome: () -> Unit
) {
    Scaffold(containerColor = CoffeeBg) { inner ->
        Column(
            Modifier.fillMaxSize().padding(inner).padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                Modifier.size(96.dp).clip(CircleShape).background(Accent),
                contentAlignment = Alignment.Center
            ) { Icon(Icons.Filled.Check, contentDescription = null, tint = Color.White, modifier = Modifier.size(56.dp)) }
            Spacer(Modifier.height(16.dp))
            Text("Pedido Confirmado", color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.height(24.dp))
            Button(onClick = onGoHome, modifier = Modifier.fillMaxWidth()) { Text("Voltar ao cardápio") }
        }
    }
}
