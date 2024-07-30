package com.example.books.presentation.ui.screens.home.discoverScreen

import com.example.books.data.remote.dto.Book
import com.example.books.data.remote.dto.BooksResponseDto

data class BooksListState(
    val isLoading: Boolean = false,
    val booksResponse: BooksResponseDto =  BooksResponseDto( 0, emptyList()),
    val error: String = ""
)