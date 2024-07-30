package com.example.books.presentation.ui.screens.home.profileScreen

import com.example.books.data.remote.dto.Book
import com.example.books.data.remote.dto.Comment

data class SavedCommentsListState(
    val isLoading: Boolean = false,
    val commentResponse: List<Comment> = emptyList(),
    val error: String = ""
)
