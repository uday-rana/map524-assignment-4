package com.udayrana.uday_bookapp

interface BookClickListener {
    fun displayBookDetails(book: Book)
    fun editBook(book: Book)
    fun deleteBook(book: Book)
}