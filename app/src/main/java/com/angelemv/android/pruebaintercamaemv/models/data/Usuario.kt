package com.angelemv.android.pruebaintercamaemv.models.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "usuario")
data class UsuarioEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "user")val user: String,
    @ColumnInfo(name = "password")val password: String
)
