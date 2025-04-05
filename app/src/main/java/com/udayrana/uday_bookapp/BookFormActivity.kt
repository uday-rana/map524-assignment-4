package com.udayrana.uday_bookapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.udayrana.uday_bookapp.databinding.ActivityBookFormBinding
import kotlinx.coroutines.launch

class BookFormActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBookFormBinding
    private lateinit var bookDao: BookDao
    private var bookToEdit: Book? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bookDao = AppDatabase.getInstance(this).bookDao()
        binding.buttonSubmitForm.setOnClickListener { submitForm() }
        binding.buttonCancel.setOnClickListener { finish() }

        bookToEdit = intent.getSerializableExtra("book") as? Book

        if (bookToEdit != null) {
            binding.editTextTitle.setText(bookToEdit!!.title)
            binding.editTextAuthor.setText(bookToEdit!!.author)
            binding.editTextGenre.setText(bookToEdit!!.genre)
            binding.editTextPrice.setText(bookToEdit!!.price.toString())
            binding.editTextQuantity.setText(bookToEdit!!.quantity.toString())

            binding.materialToolbar.title = "Edit book"
        } else {
            binding.materialToolbar.title = "Add a book"
        }
    }

    private fun submitForm() {
        lifecycleScope.launch {
            // Reset errors
            binding.textInputLayoutTitle.error = ""
            binding.textInputLayoutAuthor.error = ""
            binding.textInputLayoutGenre.error = ""
            binding.textInputLayoutPrice.error = ""
            binding.textInputLayoutQuantity.error = ""

            val titleInput = binding.editTextTitle.text.toString().trim()
            val authorInput = binding.editTextAuthor.text.toString().trim()
            val genreInput = binding.editTextGenre.text.toString().trim()
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

            val book = Book(
                uid = bookToEdit?.uid ?: 0,
                title = titleInput,
                author = authorInput,
                genre = genreInput,
                price = priceInput!!,
                quantity = quantityInput!!
            )
            val toastVerb: String

            if (bookToEdit != null) {
                bookDao.update(book)
                toastVerb = "Updated"
            } else {
                bookDao.insert(book)
                toastVerb = "Added"
            }

            Toast.makeText(
                this@BookFormActivity,
                toastVerb + " ${book.title} by ${book.author}",
                Toast.LENGTH_LONG
            ).show()
            finish()
        }
    }
}