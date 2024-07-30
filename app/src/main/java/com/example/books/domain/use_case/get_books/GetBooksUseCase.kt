package com.example.books.domain.use_case.get_books

import android.util.Log
import com.example.books.common.Resource
import com.example.books.data.remote.dto.Book
import com.example.books.data.remote.dto.BooksResponseDto
import com.example.books.domain.repository.BookRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import retrofit2.HttpException
import java.io.IOException

class GetBooksUseCase @Inject constructor(
    private val repository: BookRepository
) {
    operator fun invoke(title:String, num_books:Int, start_index:Int): Flow<Resource<BooksResponseDto>> = flow {
        try {
            emit(Resource.Loading())
            val books = repository.get_books(title, num_books, start_index)
            Log.d("KAMA", books.toString())
            emit(Resource.Success<BooksResponseDto>(books))
        } catch(e: HttpException) {
            emit(Resource.Error<BooksResponseDto>(e.localizedMessage ?: "An unexpected error occured"))
        } catch(e: IOException) {
            emit(Resource.Error<BooksResponseDto>(e.message.toString()))
        }
    }
}