package com.udayrana.uday_bookapp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Book(
    @PrimaryKey(autoGenerate = true) val uid: Int,
    val title: String,
    val author: String,
    val genre: String,
    val price: Double,
    val quantity: Int,
)
