package com.example.books.presentation.ui.screens.home.common

import com.example.books.data.remote.dto.Book
import com.example.books.data.remote.dto.BookDetail

data class BookDetailState(
    val isLoading: Boolean = false,
    val booksResponse: BookDetail = BookDetail(emptyList(),"","","", ""),
    val error: String = ""
)
