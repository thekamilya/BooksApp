package com.example.books.presentation.ui.screens.auth.signup

import com.example.books.data.remote.dto.LoginResponseDto
import com.example.books.data.remote.dto.SignupResponseDto

data class SignupState(
    val isLoading: Boolean = false,
    val signupResponse: SignupResponseDto =  SignupResponseDto( "", ""),
    val error: String = ""
)