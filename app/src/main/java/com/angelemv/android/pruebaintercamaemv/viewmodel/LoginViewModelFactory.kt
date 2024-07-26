package com.angelemv.android.pruebaintercamaemv.viewmodel

import LoginViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.angelemv.android.pruebaintercamaemv.models.interfaces.UsuarioDao

class LoginViewModelFactory(private val usuarioDao: UsuarioDao): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(usuarioDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")

    }
}