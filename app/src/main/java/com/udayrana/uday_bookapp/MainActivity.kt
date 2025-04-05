package com.udayrana.uday_bookapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.udayrana.uday_bookapp.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), BookClickListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var bookDao: BookDao
    private lateinit var bookAdapter: BookAdapter
    private lateinit var bookList: MutableList<Book>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bookList = mutableListOf()
        bookAdapter = BookAdapter(bookList, this)
        binding.recyclerViewBookList.adapter = bookAdapter
        binding.recyclerViewBookList.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewBookList.addItemDecoration(
            DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
        )
        bookDao = AppDatabase.getInstance(this).bookDao()
        binding.buttonAddBook.setOnClickListener { addBook() }
        refreshBookRecyclerView()
    }

    override fun onResume() {
        super.onResume()
        refreshBookRecyclerView()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun refreshBookRecyclerView() {
        lifecycleScope.launch {
            val bookListFromRoom = bookDao.getAll().toMutableList()
            bookList.clear()
            bookList.addAll(bookListFromRoom)
            bookAdapter.notifyDataSetChanged()
        }
    }

    private fun addBook() {
        startActivity(Intent(this@MainActivity, BookFormActivity::class.java))
    }

    override fun editBook(book: Book) {
        TODO("Not yet implemented")
    }

    override fun deleteBook(book: Book) {
        MaterialAlertDialogBuilder(this@MainActivity).setTitle("Confirm deletion")
            .setMessage("Are you sure you want to delete ${book.title} by ${book.author}?")
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.cancel()
            }
            .setPositiveButton("Delete") { dialog, _ ->
                lifecycleScope.launch {
                    bookDao.delete(book)
                    Toast.makeText(
                        this@MainActivity,
                        "Deleted ${book.title} by ${book.author}",
                        Toast.LENGTH_LONG
                    )
                        .show()
                    refreshBookRecyclerView()
                }
                dialog.dismiss()
            }
            .show()
    }

    override fun displayBookDetails(book: Book) {
        TODO("Not yet implemented")
    }
}