package com.example.books.domain.use_case.get_trending_books

import android.util.Log
import com.example.books.common.Resource
import com.example.books.data.remote.dto.Book
import com.example.books.data.remote.dto.BooksResponseDto
import com.example.books.domain.repository.BookRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetTrendingBooksUseCase @Inject constructor(
    private val repository: BookRepository
) {
    operator fun invoke(subject:String, num_books:Int = 10, is_magazine:Boolean = false): Flow<Resource<List<Book>>> = flow {
        try {
            emit(Resource.Loading())
            val books = repository.get_trending_books(subject, num_books, is_magazine)
            Log.d("KAMA", books.toString())
            emit(Resource.Success<List<Book>>(books))
        } catch(e: HttpException) {
            emit(Resource.Error<List<Book>>(e.localizedMessage ?: "An unexpected error occured"))
        } catch(e: IOException) {
            emit(Resource.Error<List<Book>>(e.message.toString()))
        }
    }
}