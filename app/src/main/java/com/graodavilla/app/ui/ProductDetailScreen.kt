package com.graodavilla.app.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.graodavilla.app.data.Product

private val CoffeeBg = Color(0xFF2F2F2F)
private val Accent   = Color(0xFF9B7A62)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    product: Product,
    onBack: () -> Unit,
    onAddToCart: (Product, Int) -> Unit
) {
    var qty by remember { mutableIntStateOf(1) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(product.name, color = Color.White) },
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
        Column(Modifier.fillMaxSize().padding(inner)) {
            Image(
                painterResource(product.imageResId),
                contentDescription = product.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth().height(190.dp)
            )

            Column(Modifier.padding(16.dp)) {
                Text(product.name, color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.SemiBold)
                Text("R$${product.price}", color = Color.White, fontSize = 18.sp)
                Spacer(Modifier.height(10.dp))
                Text(
                    product.description ?: "Delicioso item do nosso cardápio.",
                    color = Color(0xFFDDDDDD), fontSize = 14.sp, lineHeight = 18.sp
                )
            }

            Spacer(Modifier.weight(1f))

            Surface(
                color = Color(0xFF3B3B3B),
                modifier = Modifier.fillMaxWidth().padding(12.dp),
                shape = RoundedCornerShape(14.dp)
            ) {
                Row(
                    Modifier.fillMaxWidth().padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(Modifier.weight(1f)) {
                        Text("Total: R$${product.price * qty},00", color = Color.White, fontSize = 18.sp)
                        Spacer(Modifier.height(8.dp))
                        QuantityStepper(qty, onInc = { qty++ }, onDec = { if (qty > 1) qty-- })
                    }
                    Spacer(Modifier.width(12.dp))
                    Button(onClick = { onAddToCart(product, qty) },
                        colors = ButtonDefaults.buttonColors(containerColor = Accent)) {
                        Text("Adicionar")
                    }
                }
            }
        }
    }
}

@Composable
private fun QuantityStepper(qty: Int, onInc: () -> Unit, onDec: () -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        IconButton(onClick = onDec) { Text("-") }
        Text(qty.toString(), color = Color.White, fontSize = 16.sp, modifier = Modifier.widthIn(min = 24.dp))
        IconButton(onClick = onInc) { Text("+") }
    }
}
