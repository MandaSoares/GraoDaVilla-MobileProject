package com.graodavilla.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.graodavilla.app.data.Product
import com.graodavilla.app.data.ProductRepository
import com.graodavilla.app.ui.AuthScreen
import com.graodavilla.app.ui.CartScreen
import com.graodavilla.app.ui.HomeScreen
import com.graodavilla.app.ui.LoyaltyInfoScreen
import com.graodavilla.app.ui.OrderConfirmedScreen
import com.graodavilla.app.ui.ProductDetailScreen
import com.graodavilla.app.ui.ProfileScreen
import com.graodavilla.app.ui.CartViewModel
import com.graodavilla.app.auth.AuthViewModel
import com.graodavilla.app.ui.theme.GraoDaVillaTheme
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GraoDaVillaTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    AppNav()
                }
            }
        }
    }
}

@Composable
private fun AppNav() {
    val nav = rememberNavController()

    val cartVm: CartViewModel = viewModel()
    val authVm: AuthViewModel = viewModel()

    val cartItems = cartVm.items.collectAsState(initial = emptyList())
    val cartBadgeCount = cartItems.value.size

    NavHost(navController = nav, startDestination = "auth") {
        composable("auth") {
            AuthScreen(
                vm = authVm,
                onAuthenticated = {
                    nav.navigate("home") { popUpTo("auth") { inclusive = true } }
                }
            )
        }

        composable("home") {
            HomeScreen(
                onOpenDetail = { product ->
                    val encoded = URLEncoder.encode(product.name, StandardCharsets.UTF_8.toString())
                    nav.navigate("detail/$encoded")
                },
                onOpenCart    = { nav.navigate("cart") },
                onOpenProfile = { nav.navigate("profile") },
                cartCount     = cartBadgeCount
            )
        }

        composable(
            route = "detail/{name}",
            arguments = listOf(navArgument("name") { type = NavType.StringType })
        ) { backStack ->
            val name = backStack.arguments?.getString("name").orEmpty()
            val all: List<Product> =
                ProductRepository.getHotDrinks() +
                        ProductRepository.getSnacks() +
                        ProductRepository.getDesserts()
            val product = all.firstOrNull { it.name == name } ?: all.first()

            ProductDetailScreen(
                product = product,
                onBack = { nav.popBackStack() },
                onAddToCart = { p, qty ->
                    cartVm.add(p, qty)
                    nav.popBackStack()
                }
            )
        }

        composable("cart") {
            CartScreen(
                vm = cartVm,
                onBack = { nav.popBackStack() },
                onSendFinished = { qualified ->
                    nav.navigate("order-confirmed/$qualified") {
                        popUpTo("cart") { inclusive = true }
                    }
                }
            )
        }

        composable("profile") {
            ProfileScreen(
                vm = cartVm,
                onBack = { nav.popBackStack() },
                onSignOut = {
                    authVm.signOut()
                    cartVm.clear()
                    nav.navigate("auth") { popUpTo("home") { inclusive = true } }
                },
                onOpenLoyaltyInfo = { nav.navigate("loyalty-info") }
            )
        }

        composable("loyalty-info") {
            LoyaltyInfoScreen(onBack = { nav.popBackStack() })
        }

        composable(
            route = "order-confirmed/{q}",
            arguments = listOf(navArgument("q") { type = NavType.BoolType })
        ) { backStack ->
            val qualified = backStack.arguments?.getBoolean("q") ?: false
            OrderConfirmedScreen(
                qualified = qualified,
                onGoHome = {
                    nav.navigate("home") { popUpTo("home") { inclusive = true } }
                }
            )
        }
    }
}
