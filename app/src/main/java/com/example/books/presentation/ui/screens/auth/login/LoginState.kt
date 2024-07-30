package com.example.books.presentation.ui.screens.auth.login

import com.example.books.data.remote.dto.BooksResponseDto
import com.example.books.data.remote.dto.LoginResponseDto

data class LoginState(
    val isLoading: Boolean = false,
    val loginResponse: LoginResponseDto =  LoginResponseDto( "", "", "", ""),
    val error: String = ""
)