package com.example.books.domain.use_case.get_book

import com.example.books.common.Resource
import com.example.books.data.remote.dto.Book
import com.example.books.data.remote.dto.BookDetail
import com.example.books.domain.repository.BookRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


class GetBookUseCase @Inject constructor(
    private val repository: BookRepository
) {
    operator fun invoke(id:String): Flow<Resource<BookDetail>> = flow {
        try {
            emit(Resource.Loading())
            val book = repository.get_book(id)
            emit(Resource.Success<BookDetail>(book))
        } catch(e: HttpException) {
            emit(Resource.Error<BookDetail>(e.localizedMessage ?: "An unexpected error occured"))
        } catch(e: IOException) {
            emit(Resource.Error<BookDetail>(e.message.toString()))
        }
    }
}