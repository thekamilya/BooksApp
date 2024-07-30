package com.example.books.domain.use_case.delete_book

import com.example.books.common.Resource
import com.example.books.domain.repository.BookRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class DeleteBookUseCase @Inject constructor(
    private val repository: BookRepository
) {
    operator fun invoke(id: String): Flow<Resource<String>> = flow {
        try {
            emit(Resource.Loading())
            repository.delete_book(id)
            emit(Resource.Success<String>(""))
        } catch (e: HttpException) {
            emit(Resource.Error<String>(e.localizedMessage ?: "An unexpected error occured"))
        } catch (e: IOException) {
            emit(Resource.Error<String>(e.message.toString()))
        }
    }
}