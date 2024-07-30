package com.example.books.domain.use_case.save_comment

import com.example.books.common.Resource
import com.example.books.data.remote.BookRequest
import com.example.books.data.remote.CommentRequest
import com.example.books.domain.repository.BookRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class SaveCommentUseCase@Inject constructor(
    private val repository: BookRepository
) {
    operator fun invoke(id:Int, content:String, book_id :String,email:String ): Flow<Resource<Boolean>> = flow {
        try {
            emit(Resource.Loading())
            repository.save_comment(CommentRequest(id = id, content = content, book_id = book_id, email= email))
            emit(Resource.Success<Boolean>(true))
        } catch(e: HttpException) {
            emit(Resource.Error<Boolean>(e.localizedMessage ?: "An unexpected error occured"))
        } catch(e: IOException) {
            emit(Resource.Error<Boolean>(e.message.toString()))
        }
    }
}