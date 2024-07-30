package com.example.books.data.remote.dto

data class BooksResponseDto (
    val total: Int,
    val list:List<Book>
)