package com.example.books.data.remote.dto

data class Book(
    val description: String,
    val id: String,
    val image_resource: String,
    val title: String,
    val authors:String = " "
)