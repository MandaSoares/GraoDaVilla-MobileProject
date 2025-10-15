@file:OptIn(ExperimentalMaterial3Api::class)

package com.graodavilla.app.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val CoffeeBg = Color(0xFF2F2F2F)
private val Accent   = Color(0xFF9B7A62)
private val CardBg   = Color(0xFFF1F3F5)
private val TextDark = Color(0xFF1F2937)
private val DashOn   = Color(0xFFFACC15)
private val DashOff  = Color(0xFFD1D5DB)

@Composable
fun ProfileScreen(
    vm: CartViewModel,
    onBack: () -> Unit,
    onSignOut: () -> Unit,
    onOpenLoyaltyInfo: () -> Unit
) {
    val progress = vm.loyaltyProgress.collectAsState().value.coerceIn(0f, 1f)
    val steps = (progress * 10f).toInt()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Meu Perfil", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Voltar", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        },
        containerColor = CoffeeBg
    ) { inner ->
        Column(
            Modifier.fillMaxSize().padding(inner).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Surface(
                color = Color(0xFF3B3B3B),
                shape = MaterialTheme.shapes.large,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(Modifier.fillMaxWidth().padding(16.dp)) {
                    Text("Amanda Silva", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                    Spacer(Modifier.height(4.dp))
                    Text("amanda@email.com", color = Color(0xFFCCCCCC), fontSize = 14.sp)
                }
            }

            Surface(
                color = CardBg,
                shape = MaterialTheme.shapes.large,
                tonalElevation = 1.dp,
                modifier = Modifier.fillMaxWidth().clickable { onOpenLoyaltyInfo() }
            ) {
                Column(Modifier.padding(14.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            Modifier.size(32.dp).clip(CircleShape).background(DashOn.copy(alpha = .9f)),
                            contentAlignment = Alignment.Center
                        ) { Icon(Icons.Filled.StarBorder, contentDescription = null, tint = Color.White) }
                        Spacer(Modifier.width(10.dp))
                        Text("Peça 10 vezes e ganhe R$ 25,00!", color = TextDark, fontWeight = FontWeight.SemiBold)
                    }
                    Spacer(Modifier.height(10.dp))
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        repeat(10) { i ->
                            Box(
                                Modifier.weight(1f).height(6.dp)
                                    .clip(MaterialTheme.shapes.small)
                                    .background(if (i < steps) DashOn else DashOff)
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.weight(1f))

            Button(
                onClick = onSignOut,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Accent)
            ) { Text("Sair da conta") }
        }
    }
}
