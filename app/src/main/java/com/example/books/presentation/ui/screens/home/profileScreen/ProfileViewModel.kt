package com.example.books.presentation.ui.screens.home.profileScreen

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.books.common.Resource
import com.example.books.data.remote.dto.Comment
import com.example.books.domain.use_case.delete_saved_comment.DeleteSavedCommentUseCase
import com.example.books.domain.use_case.get_saved_books.GetSavedBooksUseCase
import com.example.books.domain.use_case.get_saved_comments.GetSavedCommentsUseCase
import com.example.books.domain.use_case.save_comment.SaveCommentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel  @Inject constructor(
    private val getSavedBooksUseCase: GetSavedBooksUseCase,
    private val deleteSavedCommentUseCase: DeleteSavedCommentUseCase,
    private val saveCommentUseCase: SaveCommentUseCase,
    private val getSavedCommentsUseCase: GetSavedCommentsUseCase

    ) : ViewModel() {

    private val _bookListState = mutableStateOf(SavedBooksListState())
    val bookListState: State<SavedBooksListState> = _bookListState

    private val _commentListState = mutableStateOf(SavedCommentsListState())
    val commentListState: State<SavedCommentsListState> = _commentListState

    private val _savedComments = mutableStateOf(emptyList<Comment>())
    val savedComments = _savedComments

    val commentIds = mutableStateOf( mutableSetOf<Int>())

    fun getSavedBooks() {
        getSavedBooksUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _bookListState.value = result.data?.let { SavedBooksListState(booksResponse = it) }!!
                    Log.d("KAMA", result.data.toString())

                }
                is Resource.Error -> {
                    _bookListState.value = SavedBooksListState(
                        error = result.message ?: "An unexpected error occured"
                    )
                    Log.d("KAMA", "error")

                }
                is Resource.Loading -> {
                    _bookListState.value = SavedBooksListState(isLoading = true)
                    Log.d("KAMA", "loading")

                }
            }
        }.launchIn(viewModelScope)
    }
    fun saveComment(id:Int, content:String, book_id :String,email:String ){
        saveCommentUseCase(id, content, book_id, email ).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    getSavedComments()
                }
                is Resource.Error -> {

                }
                is Resource.Loading -> {

                }
            }
        }.launchIn(viewModelScope)
    }

    fun deleteSavedComment(comment_id: Int){
        deleteSavedCommentUseCase(comment_id).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    getSavedComments()
                }
                is Resource.Error -> {


                }
                is Resource.Loading -> {


                }
            }
        }.launchIn(viewModelScope)
    }

    fun getSavedComments(){
        getSavedCommentsUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _commentListState.value = result.data?.let { SavedCommentsListState(commentResponse = it) }!!
                    _savedComments.value = result.data!!
                    commentIds.value = savedComments.value.map { it.id }.toMutableSet()
                    Log.d("killa", (result.data).toString())

                }
                is Resource.Error -> {
                    _commentListState.value = SavedCommentsListState(
                        error = result.message ?: "An unexpected error occured"
                    )

                }
                is Resource.Loading -> {
                    _commentListState.value = SavedCommentsListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)

    }
}
