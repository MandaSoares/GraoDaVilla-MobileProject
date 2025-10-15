package com.graodavilla.app.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
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

private val CoffeeBg = Color(0xFF2F2F2F)
private val Accent   = Color(0xFF9B7A62)
private val CardBg   = Color(0xFF3B3B3B)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    vm: CartViewModel,
    onBack: () -> Unit,
    onSendFinished: (qualified: Boolean) -> Unit
) {
    val items by vm.items.collectAsState()
    val total = items.sumOf { it.product.price * it.qty }
    var showCheckout by remember { mutableStateOf(false) }

    if (showCheckout) {
        CheckoutDialog(
            total = total,
            onDismiss = { showCheckout = false },
            onConfirm = { table, method ->
                val qualified = vm.placeOrder(
                    OrderPayload(table, method, items, total)
                )
                showCheckout = false
                onSendFinished(qualified)
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Seu Carrinho", color = Color.White) },
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
            Modifier.fillMaxSize().padding(inner).padding(horizontal = 16.dp)
        ) {
            LazyColumn(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(items) { item ->
                    Row(
                        Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(item.product.imageResId),
                            contentDescription = item.product.name,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.size(64.dp)
                        )
                        Spacer(Modifier.width(12.dp))
                        Column(Modifier.weight(1f)) {
                            Text(item.product.name, color = Color.White, fontWeight = FontWeight.SemiBold)
                            Text("R$${item.product.price}", color = Color(0xFFCCCCCC), fontSize = 12.sp)
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                IconButton(onClick = { vm.dec(item.product) }) { Text("-") }
                                Text(item.qty.toString(), color = Color.White, modifier = Modifier.width(24.dp))
                                IconButton(onClick = { vm.inc(item.product) }) { Text("+") }
                            }
                        }
                        IconButton(onClick = { vm.remove(item.product) }) {
                            Icon(Icons.Filled.Delete, "Remover", tint = Color(0xFFE57373))
                        }
                    }
                }
            }

            Spacer(Modifier.height(8.dp))

            Surface(color = CardBg, shape = RoundedCornerShape(16.dp), modifier = Modifier.fillMaxWidth()) {
                Row(
                    Modifier.fillMaxWidth().padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Total: R$${total},00", color = Color.White, fontSize = 18.sp, modifier = Modifier.weight(1f))
                    Button(
                        enabled = total > 0,
                        onClick = { showCheckout = true },
                        colors = ButtonDefaults.buttonColors(containerColor = Accent)
                    ) { Text("Enviar") }
                }
            }
            Spacer(Modifier.height(10.dp))
        }
    }
}

@Composable
private fun CheckoutDialog(
    total: Int,
    onDismiss: () -> Unit,
    onConfirm: (table: Int, payment: PaymentMethod) -> Unit
) {
    var table by remember { mutableIntStateOf(1) }
    var method by remember { mutableStateOf(PaymentMethod.Dinheiro) }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = { TextButton(onClick = { onConfirm(table, method) }) { Text("Confirmar") } },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Cancelar") } },
        title = { Text("Finalizar pedido", color = Color.White) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text("Total: R$${total},00", color = Color.White)

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Mesa:", color = Color.White)
                    Spacer(Modifier.width(8.dp))
                    IconButton(onClick = { if (table > 1) table-- }) { Text("-") }
                    Text("$table", color = Color.White)
                    IconButton(onClick = { table++ }) { Text("+") }
                }

                Text("Pagamento:", color = Color.White)
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    FilterChip(selected = method == PaymentMethod.Dinheiro, onClick = { method = PaymentMethod.Dinheiro }, label = { Text("Dinheiro") })
                    FilterChip(selected = method == PaymentMethod.Cartao,   onClick = { method = PaymentMethod.Cartao },   label = { Text("Cartão") })
                    FilterChip(selected = method == PaymentMethod.Pix,      onClick = { method = PaymentMethod.Pix },      label = { Text("Pix") })
                }

                if (total >= 25) {
                    Text(
                        "Este pedido será contabilizado no programa de fidelidade!",
                        color = Color(0xFF9B7A62),
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp
                    )
                }
            }
        },
        containerColor = CoffeeBg
    )
}
