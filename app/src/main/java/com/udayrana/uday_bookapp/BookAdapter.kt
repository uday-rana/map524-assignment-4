package com.udayrana.uday_bookapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.udayrana.uday_bookapp.databinding.BookItemBinding

class BookAdapter(
    private val bookList: MutableList<Book>,
    private val bookClickListener: BookClickListener
) :
    RecyclerView.Adapter<BookAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: BookItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = BookItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return bookList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val book = bookList[position]

        holder.binding.textViewBookTitle.text = book.title
        holder.binding.textViewBookAuthor.text = book.author

        holder.binding.imageViewDelete.setOnClickListener { bookClickListener.deleteBook(book) }
        holder.binding.imageViewEdit.setOnClickListener { bookClickListener.editBook(book) }
        holder.binding.linearLayoutItemText.setOnClickListener {
            bookClickListener.displayBookDetails(
                book
            )
        }
    }
}