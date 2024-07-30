package com.example.books.data.remote.dto

import android.provider.ContactsContract.CommonDataKinds.Email

data class LoginResponseDto(
    val access_token: String,
    val token_type: String,
    val email: String,
    val name:String
)