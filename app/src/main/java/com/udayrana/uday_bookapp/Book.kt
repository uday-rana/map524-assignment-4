package com.udayrana.uday_bookapp

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Book(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    val title: String,
    val author: String,
    val genre: String,
    val price: Double,
    val quantity: Int,
) : Serializable
