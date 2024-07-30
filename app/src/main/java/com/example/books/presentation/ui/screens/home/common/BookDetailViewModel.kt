package com.example.books.presentation.ui.screens.home.common

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.books.common.Resource
import com.example.books.data.remote.dto.Comment
import com.example.books.domain.use_case.delete_book.DeleteBookUseCase
import com.example.books.domain.use_case.delete_comment.DeleteCommentUseCase
import com.example.books.domain.use_case.delete_saved_comment.DeleteSavedCommentUseCase
import com.example.books.domain.use_case.get_book.GetBookUseCase
import com.example.books.domain.use_case.get_saved_comments.GetSavedCommentsUseCase
import com.example.books.domain.use_case.is_book_saved.IsBookSavedUseCase
import com.example.books.domain.use_case.is_comment_saved.IsCommentSavedUseCase
import com.example.books.domain.use_case.post_comment.PostCommentUseCase
import com.example.books.domain.use_case.save_book.SaveBookUseCase
import com.example.books.domain.use_case.save_comment.SaveCommentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class BookDetailViewModel @Inject constructor(
    private val saveBookUseCase: SaveBookUseCase,
    private val getBookUseCase: GetBookUseCase,
    private val postCommentUseCase: PostCommentUseCase,
    private val deleteCommentUseCase: DeleteCommentUseCase,
    private val isBookSavedUseCase: IsBookSavedUseCase,
    private val deleteBookUseCase: DeleteBookUseCase,
    private val deleteSavedCommentUseCase: DeleteSavedCommentUseCase,
    private val saveCommentUseCase: SaveCommentUseCase,
    private val isCommentSavedUseCase: IsCommentSavedUseCase,
    private val getSavedCommentsUseCase: GetSavedCommentsUseCase

    ) : ViewModel() {
    private val _detailState = mutableStateOf(BookDetailState())
    val detailState: State<BookDetailState> = _detailState

    private val _comments = mutableStateOf(emptyList<Comment>())
    val comments = _comments

    var showOptions = mutableStateOf(-1)

    private val _bookSavedState = mutableStateOf(false)
    val bookSavedState = _bookSavedState

    private val _commentSavedState = mutableStateOf(false)
    val commentSavedState = _commentSavedState

    private val _savedComments = mutableStateOf(emptyList<Comment>())
    val savedComments = _savedComments

    val commentIds = mutableStateOf( mutableSetOf<Int>())



    fun getBook(id:String, getOnlyComments:Boolean = false){
        getBookUseCase(id).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    if (!getOnlyComments){
                        _detailState.value = result.data?.let { BookDetailState(booksResponse = it) }!!
                        _comments.value = result.data.comments ?: emptyList()
                    }else{
                        _comments.value = result.data?.comments ?: emptyList()
                    }
                    Log.d("KILLA", result.data.toString())

                }
                is Resource.Error -> {
                    if (!getOnlyComments){
                        _detailState.value = BookDetailState(
                            error = result.message ?: "An unexpected error occured"
                        )
                    }
                }
                is Resource.Loading -> {
                    if (!getOnlyComments){
                        _detailState.value = BookDetailState(isLoading = true, booksResponse = _detailState.value.booksResponse )
                    }

                }
            }
        }.launchIn(viewModelScope)

    }



    fun postComment(content:String, book_id:String) {
        postCommentUseCase(content, book_id).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    getBook(book_id, true)
                    Log.d("JJJ", "POSTED")
                }
                is Resource.Error -> {
                    Log.d("JJJ", "error")

                }
                is Resource.Loading -> {
                    Log.d("JJJ", "loading")

                }
            }
        }.launchIn(viewModelScope)
    }

    fun deleteComment(comment_id:Int, book_id: String){
        deleteCommentUseCase(comment_id).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    getBook(book_id, true)
                    Log.d("JJJ", "DELETED")
                }
                is Resource.Error -> {
                    Log.d("JJJ", "error")

                }
                is Resource.Loading -> {
                    Log.d("JJJ", "loading")

                }
            }
        }.launchIn(viewModelScope)
    }

    fun saveBook(id:String, title:String, description:String,image_resource:String, authors: String){
        saveBookUseCase(id, title, description, image_resource,authors ).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _bookSavedState.value = true
                }
                is Resource.Error -> {

                }
                is Resource.Loading -> {

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
    fun deleteBook(book_id: String){
        deleteBookUseCase(book_id).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _bookSavedState.value = false
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



    fun isBookSaved(id:String){
        isBookSavedUseCase(id).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _bookSavedState.value = result.data == true
                    Log.d("JJJ", (result.data).toString())
                }

                is Resource.Error -> {

                }
                is Resource.Loading -> {

                }
            }
        }.launchIn(viewModelScope)
    }

//    fun isCommentSaved(id:Int){
//        return id in commentIds.value
//    }

    fun getSavedComments(){
        getSavedCommentsUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _savedComments.value = result.data!!
                    commentIds.value = savedComments.value.map { it.id }.toMutableSet()
                    Log.d("killa", (result.data).toString())

                }
                is Resource.Error -> {

                }
                is Resource.Loading -> {


                }
            }
        }.launchIn(viewModelScope)

    }
}


