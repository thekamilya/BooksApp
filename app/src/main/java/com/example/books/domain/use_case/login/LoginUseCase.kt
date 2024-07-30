package com.example.books.domain.use_case.login

import android.util.Log
import com.example.books.common.Resource
import com.example.books.data.remote.dto.BooksResponseDto
import com.example.books.data.remote.dto.LoginResponseDto
import com.example.books.domain.repository.BookRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: BookRepository
) {
    operator fun invoke(email:String, password: String): Flow<Resource<LoginResponseDto>> = flow {
        try {
            emit(Resource.Loading())
            val loginResponse = repository.login(email,password)
            emit(Resource.Success<LoginResponseDto>(loginResponse))
        } catch(e: HttpException) {
            emit(Resource.Error<LoginResponseDto>(e.localizedMessage ?: "An unexpected error occured"))
        } catch(e: IOException) {
            emit(Resource.Error<LoginResponseDto>(e.message.toString()))
        }
    }
}