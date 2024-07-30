package com.example.books.data.remote.dto

data class BookDetail(
    val comments: List<Comment>,
    val description: String,
    val image_resource: String,
    val title: String,
    val authors:String
)