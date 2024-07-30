package com.example.books.presentation.ui.screens.home.profileScreen

import com.example.books.data.remote.dto.Book

data class SavedBooksListState(
    val isLoading: Boolean = false,
    val booksResponse: List<Book> = emptyList(),
    val error: String = ""
)