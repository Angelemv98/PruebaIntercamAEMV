package com.angelemv.android.pruebaintercamaemv.models.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favoritos")
data class Favorito(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val cocktailId: String, // ID del cóctel
    val name: String,
    val category: String,
    val image: String
)