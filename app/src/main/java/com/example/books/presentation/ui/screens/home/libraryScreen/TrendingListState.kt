package com.example.books.presentation.ui.screens.home.libraryScreen

import com.example.books.data.remote.dto.Book
import com.example.books.data.remote.dto.BooksResponseDto

data class TrendingListState(
    val isLoading: Boolean = false,
    val booksResponse: List<Book> =  emptyList(),
    val error: String = ""
)
