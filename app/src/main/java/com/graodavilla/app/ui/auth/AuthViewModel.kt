package com.graodavilla.app.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

data class AuthUser(val name: String, val email: String)

class AuthViewModel : ViewModel() {
    var currentUser by mutableStateOf<AuthUser?>(null)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    var isLoading by mutableStateOf(false)
        private set

    fun loginWithEmail(email: String, password: String) {
        errorMessage = null; isLoading = true
        val ok = email.contains("@") && password.length >= 4
        if (ok) currentUser = AuthUser(email.substringBefore("@").replaceFirstChar { it.uppercase() }, email)
        else errorMessage = "E-mail ou senha inválidos"
        isLoading = false
    }

    fun signUp(name: String, email: String, password: String) {
        errorMessage = null; isLoading = true
        val ok = name.isNotBlank() && email.contains("@") && password.length >= 4
        if (ok) currentUser = AuthUser(name.trim(), email.trim())
        else errorMessage = "Preencha nome, e-mail e senha válidos"
        isLoading = false
    }

    fun loginWithGoogle() {
        errorMessage = null; isLoading = true
        currentUser = AuthUser("Usuário Google", "user@gmail.com")
        isLoading = false
    }

    fun signOut() {
        currentUser = null
        errorMessage = null
        isLoading = false
    }
}
