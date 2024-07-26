package com.angelemv.android.pruebaintercamaemv.models.repository

import com.angelemv.android.pruebaintercamaemv.models.data.Usuario
import com.angelemv.android.pruebaintercamaemv.models.interfaces.UsuarioDao


class UsuarioRepository(private val usuarioDao: UsuarioDao) {
    suspend fun insert(usuario: Usuario) {
        usuarioDao.insert(usuario)
    }

    suspend fun getUsuario(username: String, password: String): Usuario? {
        return usuarioDao.getUsuario(username, password)
    }
}