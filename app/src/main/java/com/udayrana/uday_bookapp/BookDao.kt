package com.udayrana.uday_bookapp

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface BookDao {
    @Insert
    suspend fun insert(vararg books: Book)

    @Update
    suspend fun update(vararg books: Book)

    @Delete
    suspend fun delete(vararg books: Book)

    @Query("SELECT * FROM book")
    suspend fun getAll(): List<Book>
}