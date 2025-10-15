package com.graodavilla.app.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val CoffeeBg = Color(0xFF2F2F2F)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoyaltyInfoScreen(onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Programa de Fidelidade", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        },
        containerColor = CoffeeBg
    ) { inner ->
        Column(
            Modifier.fillMaxSize().padding(inner).padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                "A cada 10 pedidos de no mínimo 25,00 ganhe um desconto de 25,00",
                color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.SemiBold
            )

            Text("Validade", color = Color.White, fontWeight = FontWeight.SemiBold)
            Text("Complete e use o desconto em até 60 dias.", color = Color(0xFFDDDDDD))

            Text("Regras", color = Color.White, fontWeight = FontWeight.SemiBold)
            listOf(
                "O desconto é aplicado automaticamente na compra seguinte após concluir o programa e pode ser usado em até 60 dias.",
                "O desconto não é cumulativo com outros descontos ou cupons.",
                "Para contar no programa, o usuário deve estar logado no checkout.",
                "Apenas pedidos válidos e finalizados são contabilizados.",
                "Pedidos válidos devem ter pelo menos 15 horas de intervalo.",
                "São válidos pedidos feitos por Delivery."
            ).forEach { Text("• $it", color = Color(0xFFDDDDDD)) }

            Spacer(Modifier.weight(1f))
            Button(onClick = onBack, modifier = Modifier.fillMaxWidth()) { Text("Entendi") }
            Spacer(Modifier.height(10.dp))
        }
    }
}
