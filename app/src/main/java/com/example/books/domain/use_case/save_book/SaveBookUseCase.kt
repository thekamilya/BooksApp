package com.example.books.domain.use_case.save_book

import com.example.books.common.Resource
import com.example.books.data.remote.BookRequest
import com.example.books.data.remote.CommentRequest
import com.example.books.domain.repository.BookRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class SaveBookUseCase @Inject constructor(
    private val repository: BookRepository
) {
    operator fun invoke(id:String, title:String, description:String,image_resource:String, authors:String ): Flow<Resource<Boolean>> = flow {
        try {
            emit(Resource.Loading())
            repository.save_book(BookRequest(id = id, title = title, description = description, image_resource= image_resource, authors = authors))
            emit(Resource.Success<Boolean>(true))
        } catch(e: HttpException) {
            emit(Resource.Error<Boolean>(e.localizedMessage ?: "An unexpected error occured"))
        } catch(e: IOException) {
            emit(Resource.Error<Boolean>(e.message.toString()))
        }
    }
}