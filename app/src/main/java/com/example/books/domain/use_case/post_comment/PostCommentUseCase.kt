package com.example.books.domain.use_case.post_comment

import com.example.books.common.Resource
import com.example.books.data.remote.CommentRequest
import com.example.books.data.remote.dto.Book
import com.example.books.domain.repository.BookRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class PostCommentUseCase @Inject constructor(
    private val repository: BookRepository
) {
    operator fun invoke(content:String, book_id:String): Flow<Resource<String>> = flow {
        try {
            emit(Resource.Loading())
            repository.post_comment(CommentRequest(content=content, book_id=book_id))
            emit(Resource.Success<String>(""))
        } catch(e: HttpException) {
            emit(Resource.Error<String>(e.localizedMessage ?: "An unexpected error occured"))
        } catch(e: IOException) {
            emit(Resource.Error<String>(e.message.toString()))
        }
    }
}