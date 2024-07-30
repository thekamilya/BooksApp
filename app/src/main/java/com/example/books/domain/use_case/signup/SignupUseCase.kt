package com.example.books.domain.use_case.signup

import com.example.books.common.Resource
import com.example.books.data.remote.CommentRequest
import com.example.books.data.remote.SignupRequest
import com.example.books.data.remote.dto.SignupResponseDto
import com.example.books.domain.repository.BookRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class SignupUseCase @Inject constructor(
    private val repository: BookRepository
) {
    operator fun invoke(name:String, email:String, password: String): Flow<Resource<SignupResponseDto>> = flow {
        try {
            emit(Resource.Loading())
            val signupResponse = repository.sigup(name = name, email = email , password = password)
            emit(Resource.Success<SignupResponseDto>(signupResponse))
        } catch(e: HttpException) {
            emit(Resource.Error<SignupResponseDto>(e.localizedMessage ?: "An unexpected error occured"))
        } catch(e: IOException) {
            emit(Resource.Error<SignupResponseDto>(e.message.toString()))
        }
    }
}