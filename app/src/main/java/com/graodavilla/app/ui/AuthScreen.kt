package com.graodavilla.app.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.graodavilla.app.R
import com.graodavilla.app.auth.AuthViewModel
import androidx.compose.foundation.BorderStroke

private val CoffeeBg = Color(0xFF2F2F2F)
private val Beige    = Color(0xFFD4B6A0)

@Composable
fun AuthScreen(
    vm: AuthViewModel,
    onAuthenticated: () -> Unit
) {
    val user = vm.currentUser
    if (user != null) {
        LaunchedEffect(Unit) { onAuthenticated() }
        return
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(CoffeeBg)
            .padding(20.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.logo_graodavilla),
                contentDescription = "Logo",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(110.dp)
                    .clip(CircleShape)
            )

            Spacer(Modifier.height(18.dp))
            Text("Bem-vindo(a) ao Grão da Villa", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
            Text("Entre para começar seu pedido", color = Color(0xFFDDDDDD), fontSize = 14.sp)

            Spacer(Modifier.height(22.dp))

            var isLogin by remember { mutableStateOf(true) }
            AuthTabs(isLogin = isLogin, onChange = { isLogin = it })

            Spacer(Modifier.height(16.dp))

            if (isLogin) {
                LoginForm(vm = vm)
            } else {
                SignUpForm(vm = vm)
            }

            Spacer(Modifier.height(10.dp))

            AnimatedVisibility(visible = vm.errorMessage != null) {
                Text(vm.errorMessage ?: "", color = Color(0xFFFFD8D8))
            }

            Spacer(Modifier.height(14.dp))

            OutlinedButton(
                onClick = { vm.loginWithGoogle(); if (vm.currentUser != null) onAuthenticated() },
                shape = RoundedCornerShape(12.dp),
                border = ButtonDefaults.outlinedButtonBorder,
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_google),
                    contentDescription = "Google",
                    tint = Color.Unspecified,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(Modifier.width(8.dp))
                Text("Entrar com Google")
            }
        }
    }
}

@Composable
private fun AuthTabs(isLogin: Boolean, onChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = { onChange(true) },
            colors = if (isLogin)
                ButtonDefaults.buttonColors(containerColor = Color(0xFF8D6E63), contentColor = Color.White)
            else
                ButtonDefaults.buttonColors(containerColor = Color.Transparent, contentColor = Color(0xFF8D6E63)),
            border = if (!isLogin) BorderStroke(1.dp, Color(0xFF8D6E63)) else null,
            shape = RoundedCornerShape(topStart = 12.dp, bottomStart = 12.dp),
            modifier = Modifier.weight(1f)
        ) { Text("Login") }

        Button(
            onClick = { onChange(false) },
            colors = if (!isLogin)
                ButtonDefaults.buttonColors(containerColor = Color(0xFF8D6E63), contentColor = Color.White)
            else
                ButtonDefaults.buttonColors(containerColor = Color.Transparent, contentColor = Color(0xFF8D6E63)),
            border = if (isLogin) BorderStroke(1.dp, Color(0xFF8D6E63)) else null,
            shape = RoundedCornerShape(topEnd = 12.dp, bottomEnd = 12.dp),
            modifier = Modifier.weight(1f)
        ) { Text("Cadastro") }
    }
}

@Composable private fun LoginForm(vm: AuthViewModel) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        AuthField(value = email, onValueChange = { email = it }, label = "E-mail", leading = { Icon(Icons.Default.Email, null, tint = Color.White) })
        Spacer(Modifier.height(10.dp))
        AuthField(value = password, onValueChange = { password = it }, label = "Senha",
            leading = { Icon(Icons.Default.Lock, null, tint = Color.White) }, isPassword = true)
        Spacer(Modifier.height(16.dp))
        Button(
            onClick = { vm.loginWithEmail(email, password) },
            colors = ButtonDefaults.buttonColors(containerColor = Beige, contentColor = Color.Black),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) { Text("Entrar") }
    }
}

@Composable private fun SignUpForm(vm: AuthViewModel) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        AuthField(value = name, onValueChange = { name = it }, label = "Nome", leading = { Icon(Icons.Default.Person, null, tint = Color.White) })
        Spacer(Modifier.height(10.dp))
        AuthField(value = email, onValueChange = { email = it }, label = "E-mail", leading = { Icon(Icons.Default.Email, null, tint = Color.White) })
        Spacer(Modifier.height(10.dp))
        AuthField(value = password, onValueChange = { password = it }, label = "Senha",
            leading = { Icon(Icons.Default.Lock, null, tint = Color.White) }, isPassword = true)
        Spacer(Modifier.height(16.dp))
        Button(
            onClick = { vm.signUp(name, email, password) },
            colors = ButtonDefaults.buttonColors(containerColor = Beige, contentColor = Color.Black),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) { Text("Cadastrar") }
    }
}

@Composable
private fun AuthField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    leading: @Composable (() -> Unit)? = null,
    isPassword: Boolean = false
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, color = Color(0xFFEEEEEE)) },
        leadingIcon = leading,
        singleLine = true,
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,
            focusedBorderColor = Color.White,
            unfocusedBorderColor = Color(0xFFBDBDBD),
            focusedLeadingIconColor = Color.White,
            unfocusedLeadingIconColor = Color(0xFFCCCCCC),
            focusedLabelColor = Color.White
        ),
        modifier = Modifier.fillMaxWidth()
    )
}
