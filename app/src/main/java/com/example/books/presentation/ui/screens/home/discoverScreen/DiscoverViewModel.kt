package com.example.books.presentation.ui.screens.home.discoverScreen

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.books.common.Resource
import com.example.books.domain.use_case.delete_comment.DeleteCommentUseCase
import com.example.books.domain.use_case.get_book.GetBookUseCase
import com.example.books.domain.use_case.get_books.GetBooksUseCase
import com.example.books.domain.use_case.post_comment.PostCommentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import kotlin.math.ceil

@HiltViewModel
class DiscoverViewModel @Inject constructor(
    private val getBooksUseCase: GetBooksUseCase,
//    private val getBookUseCase: GetBookUseCase,
//    private val postCommentUseCase: PostCommentUseCase,
//    private val deleteCommentUseCase: DeleteCommentUseCase

) : ViewModel() {

    private val _listState = mutableStateOf(BooksListState())
    val listState: State<BooksListState> = _listState

//    private val _detailState = mutableStateOf(BookDetailState())
//    var detailState:State<BookDetailState> = _detailState
//
//    private val _comments = mutableStateOf(emptyList<com.example.books.data.remote.dto.Comment>())
//    var comments = _comments

    private val _searchRequest = mutableStateOf("Such a beautiful day")
    var searchRequest:State<String> = _searchRequest

//    var showOptions = mutableStateOf(-1)


    val currentPage = mutableStateOf(1)
    val totalPages = mutableStateOf(0)
    val start_index = mutableStateOf(0)


    fun setSearchRequest(searchRequest:String){
        _searchRequest.value = searchRequest
    }


//    fun getBook(id:String, getOnlyComments:Boolean = false){
//        getBookUseCase(id).onEach { result ->
//            when (result) {
//                is Resource.Success -> {
//                    if (!getOnlyComments){
//                        _detailState.value = result.data?.let { BookDetailState(booksResponse = it) }!!
//                        _comments.value = result.data.comments ?: emptyList()
//                    }else{
//                        _comments.value = result.data?.comments ?: emptyList()
//                    }
//
//                }
//                is Resource.Error -> {
//                    if (!getOnlyComments){
//                        _detailState.value = BookDetailState(
//                            error = result.message ?: "An unexpected error occured"
//                        )
//                    }
//                }
//                is Resource.Loading -> {
//                    if (!getOnlyComments){
//                        _detailState.value = BookDetailState(isLoading = true, booksResponse = _detailState.value.booksResponse )
//                    }
//
//                }
//            }
//        }.launchIn(viewModelScope)
//
//    }

    fun getBooks(title:String, num_books:Int, start_index:Int) {
        getBooksUseCase(title, num_books, start_index).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _listState.value = result.data?.let { BooksListState(booksResponse = it) }!!
                    Log.d("KAMA", result.data.total.toString())
                    totalPages.value = ceil(result.data.total / 10.0).toInt()

                }
                is Resource.Error -> {
                    _listState.value = BooksListState(
                        error = result.message ?: "An unexpected error occured"
                    )
                    Log.d("KAMA", "error")

                }
                is Resource.Loading -> {
                    _listState.value = BooksListState(isLoading = true)
                    Log.d("KAMA", "loading")

                }
            }
        }.launchIn(viewModelScope)
    }

//    fun postComment(content:String, book_id:String) {
//        postCommentUseCase(content, book_id).onEach { result ->
//            when (result) {
//                is Resource.Success -> {
//                    getBook(book_id, true)
//                    Log.d("JJJ", "POSTED")
//                }
//                is Resource.Error -> {
//                    Log.d("JJJ", "error")
//
//                }
//                is Resource.Loading -> {
//                    Log.d("JJJ", "loading")
//
//                }
//            }
//        }.launchIn(viewModelScope)
//    }
//
//    fun deleteComment(comment_id:Int, book_id: String){
//        deleteCommentUseCase(comment_id).onEach { result ->
//            when (result) {
//                is Resource.Success -> {
//                    getBook(book_id, true)
//                    Log.d("JJJ", "DELETED")
//                }
//                is Resource.Error -> {
//                    Log.d("JJJ", "error")
//
//                }
//                is Resource.Loading -> {
//                    Log.d("JJJ", "loading")
//
//                }
//            }
//        }.launchIn(viewModelScope)
//    }
}