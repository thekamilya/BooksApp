package com.example.books.domain.use_case.is_comment_saved

import com.example.books.common.Resource
import com.example.books.domain.repository.BookRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class IsCommentSavedUseCase @Inject constructor(
    private val repository: BookRepository
) {
    operator fun invoke(id:Int): Flow<Resource<Boolean>> = flow {
        try {
            emit(Resource.Loading())
            var isSaved = repository.is_comment_saved(id)
            emit(Resource.Success<Boolean>(isSaved))
        } catch(e: HttpException) {
            emit(Resource.Error<Boolean>(e.localizedMessage ?: "An unexpected error occured"))
        } catch(e: IOException) {
            emit(Resource.Error<Boolean>(e.message.toString()))
        }
    }
}