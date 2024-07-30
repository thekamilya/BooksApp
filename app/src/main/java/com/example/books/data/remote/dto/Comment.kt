package com.example.books.data.remote.dto

data class Comment(
    val id:Int,
    val email:String,
    val book_id: String,
    val content: String
)