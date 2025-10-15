package com.graodavilla.app.ui

import androidx.lifecycle.ViewModel
import com.graodavilla.app.data.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

data class CartItem(val product: Product, val qty: Int)
enum class PaymentMethod { Dinheiro, Cartao, Pix }

data class OrderPayload(
    val table: Int,
    val payment: PaymentMethod,
    val items: List<CartItem>,
    val total: Int
)

class CartViewModel : ViewModel() {

    private val _items = MutableStateFlow<List<CartItem>>(emptyList())
    val items: StateFlow<List<CartItem>> = _items

    private val _loyaltyProgress = MutableStateFlow(0f) // 0f..1f (10 passos)
    val loyaltyProgress: StateFlow<Float> = _loyaltyProgress

    fun count(): Int = _items.value.sumOf { it.qty }
    fun total(): Int = _items.value.sumOf { it.product.price * it.qty }

    fun add(p: Product, qty: Int = 1) {
        _items.update { list ->
            val i = list.indexOfFirst { it.product.name == p.name }
            if (i >= 0) list.toMutableList().also { m -> m[i] = m[i].copy(qty = m[i].qty + qty) }
            else list + CartItem(p, qty.coerceAtLeast(1))
        }
    }

    fun inc(p: Product) = _items.update { it.map { c -> if (c.product.name == p.name) c.copy(qty = c.qty + 1) else c } }

    fun dec(p: Product) = _items.update { it.mapNotNull { c ->
        if (c.product.name == p.name) (c.qty - 1).takeIf { q -> q > 0 }?.let { q -> c.copy(qty = q) } else c
    } }

    fun remove(p: Product) = _items.update { it.filterNot { c -> c.product.name == p.name } }

    fun clear() = _items.update { emptyList() }

    //finaliza e atualiza fidelidade se total >= 25 //
    fun placeOrder(payload: OrderPayload): Boolean {
        val qualified = payload.total >= 25
        if (qualified) _loyaltyProgress.update { (it + 0.1f).coerceAtMost(1f) }
        clear()
        return qualified
    }
}
