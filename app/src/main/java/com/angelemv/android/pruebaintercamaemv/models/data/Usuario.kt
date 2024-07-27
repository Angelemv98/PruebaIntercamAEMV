package com.angelemv.android.pruebaintercamaemv.models.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "usuarios")
data class Usuario(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val username: String,
    val password: String // Encriptado en Base64
)
