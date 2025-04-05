package com.udayrana.uday_bookapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.udayrana.uday_bookapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var bookDao: BookDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = AppDatabase.getInstance(this)
        bookDao = db.bookDao()
    }
}