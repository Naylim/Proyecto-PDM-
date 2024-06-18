package com.example.segundoexamen.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shows")
data class Show(
    @PrimaryKey val id: Int,
    val name: String,
    val rating: String,
    val image: String,
    val genres: String,
    val startDate: String,
    val region: String,
    val language: String,
    val description: String
)