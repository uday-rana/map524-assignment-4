package com.udayrana.uday_bookapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.udayrana.uday_bookapp.databinding.ActivityAddBookBinding
import kotlinx.coroutines.launch

class AddBookActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddBookBinding
    private lateinit var bookDao: BookDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBookBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bookDao = AppDatabase.getInstance(this).bookDao()

        binding.buttonAddBook.setOnClickListener { addBook() }
    }

    private fun addBook() {
        lifecycleScope.launch {
            // Reset errors
            binding.textInputLayoutTitle.error = ""
            binding.textInputLayoutAuthor.error = ""
            binding.textInputLayoutGenre.error = ""
            binding.textInputLayoutPrice.error = ""
            binding.textInputLayoutQuantity.error = ""

            val titleInput = binding.editTextTitle.text.toString()
            val authorInput = binding.editTextAuthor.text.toString()
            val genreInput = binding.editTextGenre.text.toString()
            val priceInput = binding.editTextPrice.text.toString().toDoubleOrNull()
            val quantityInput = binding.editTextQuantity.text.toString().toIntOrNull()

            var error = false

            if (titleInput.isBlank()) {
                binding.textInputLayoutTitle.error = "Enter title"
                error = true
            }

            if (authorInput.isBlank()) {
                binding.textInputLayoutAuthor.error = "Enter author name"
                error = true
            }

            if (genreInput.isBlank()) {
                binding.textInputLayoutGenre.error = "Enter genre"
                error = true
            }

            if (priceInput == null) {
                binding.textInputLayoutPrice.error = "Enter price"
                error = true
            } else if (priceInput <= 0) {
                binding.textInputLayoutPrice.error = "Price must be greater than 0"
                error = true
            }

            if (quantityInput == null) {
                binding.textInputLayoutQuantity.error = "Enter quantity"
                error = true
            } else if (quantityInput <= 0) {
                binding.textInputLayoutQuantity.error = "Quantity must be greater than 0"
                error = true
            }

            if (error) {
                return@launch
            }

            val newBook =
                Book(
                    title = titleInput,
                    author = authorInput,
                    genre = genreInput,
                    price = requireNotNull(priceInput),
                    quantity = requireNotNull(quantityInput)
                )

            bookDao.insert(newBook)

            Toast.makeText(this@AddBookActivity, "Added $titleInput", Toast.LENGTH_LONG).show()
            finish()
        }
    }
}