package com.example.books.domain.use_case.get_saved_comments

import com.example.books.common.Resource
import com.example.books.data.remote.dto.Book
import com.example.books.data.remote.dto.Comment
import com.example.books.domain.repository.BookRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetSavedCommentsUseCase @Inject constructor(
    private val repository: BookRepository
) {
    operator fun invoke(): Flow<Resource<List<Comment>>> = flow {
        try {
            emit(Resource.Loading())
            val books = repository.get_saved_comments()
            emit(Resource.Success<List<Comment>>(books))
        } catch(e: HttpException) {
            emit(Resource.Error<List<Comment>>(e.localizedMessage ?: "An unexpected error occured"))
        } catch(e: IOException) {
            emit(Resource.Error<List<Comment>>(e.message.toString()))
        }
    }
}