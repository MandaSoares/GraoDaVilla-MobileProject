package com.graodavilla.app.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.graodavilla.app.R
import com.graodavilla.app.data.Product
import com.graodavilla.app.data.ProductRepository

private val CoffeeBg    = Color(0xFF2F2F2F)
private val CardBg      = Color(0xFF3B3B3B)
private val DividerGray = Color(0xFF8E8E8E)

@Composable
fun HomeScreen(
    onOpenDetail: (Product) -> Unit,
    onOpenCart: () -> Unit,
    onOpenProfile: () -> Unit,
    cartCount: Int,
    modifier: Modifier = Modifier
) {
    Column(
        modifier
            .fillMaxSize()
            .background(CoffeeBg)
            .padding(horizontal = 16.dp)
    ) {
        HeaderBar(cartCount, onOpenCart, onOpenProfile)
        Spacer(Modifier.height(8.dp))
        SectionHeader("Bebidas quentes")
        ProductRow(ProductRepository.getHotDrinks(), onOpenDetail)
        Spacer(Modifier.height(16.dp))
        SectionHeader("Salgados")
        ProductRow(ProductRepository.getSnacks(), onOpenDetail)
        Spacer(Modifier.height(16.dp))
        SectionHeader("Doces")
        ProductRow(ProductRepository.getDesserts(), onOpenDetail)
        Spacer(Modifier.height(8.dp))
    }
}

@Composable
private fun HeaderBar(
    cartCount: Int,
    onCartClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    Row(
        Modifier.fillMaxWidth().padding(top = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(color = Color(0xFF9B7A62), shape = CircleShape) {
            IconButton(onClick = onProfileClick) {
                Icon(Icons.Filled.Person, contentDescription = "Perfil", tint = Color.White)
            }
        }
        Box(Modifier.weight(1f), contentAlignment = Alignment.Center) {
            Image(
                painterResource(R.drawable.logo_graodavilla),
                contentDescription = "Grão da Villa",
                modifier = Modifier.size(72.dp).clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        }
        BadgedBox(badge = { if (cartCount > 0) Badge { Text("$cartCount") } }) {
            IconButton(onClick = onCartClick) {
                Icon(Icons.Filled.ShoppingCart, contentDescription = "Carrinho", tint = Color.White)
            }
        }
    }
}

@Composable private fun SectionHeader(title: String) {
    Row(
        Modifier.fillMaxWidth().padding(top = 10.dp, bottom = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(title, color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.SemiBold)
        Spacer(Modifier.width(8.dp))
        Divider(Modifier.weight(1f), color = DividerGray.copy(alpha = .6f), thickness = 1.dp)
    }
}

@Composable private fun ProductRow(items: List<Product>, onClick: (Product) -> Unit) {
    LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        items(items) { p -> ProductCard(p) { onClick(p) } }
    }
}

@Composable private fun ProductCard(p: Product, onClick: () -> Unit) {
    Surface(
        color = CardBg,
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.width(200.dp).clickable(onClick = onClick)
    ) {
        Column(Modifier.padding(10.dp)) {
            Image(
                painterResource(p.imageResId),
                contentDescription = p.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier.height(125.dp).fillMaxWidth().clip(RoundedCornerShape(12.dp))
            )
            Spacer(Modifier.height(10.dp))
            Text(p.name, color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
            Text("R$${p.price}", color = Color(0xFFDEDEDE), fontSize = 14.sp)
        }
    }
}

