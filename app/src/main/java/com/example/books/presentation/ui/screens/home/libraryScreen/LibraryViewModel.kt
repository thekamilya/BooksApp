package com.example.books.presentation.ui.screens.home.libraryScreen

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.books.common.Resource
import com.example.books.domain.use_case.get_trending_books.GetTrendingBooksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@HiltViewModel
class LibraryViewModel @Inject constructor(
    private val getTrendingBooksUseCase: GetTrendingBooksUseCase,
    ) : ViewModel() {

    private val _ganreListState = mutableStateOf(TrendingListState())
    val ganreListState: State<TrendingListState> = _ganreListState

    private val _magazineListState  = mutableStateOf(TrendingListState())
    val magazineListState: State<TrendingListState> = _magazineListState

    fun getBooks(subject:String) {
        getTrendingBooksUseCase(subject).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _ganreListState.value = result.data?.let { TrendingListState(booksResponse = it) }!!
                }
                is Resource.Error -> {
                    _ganreListState.value = TrendingListState(
                        error = result.message ?: "An unexpected error occured"
                    )
                    Log.d("LAB", "error")

                }
                is Resource.Loading -> {
                    _ganreListState.value = TrendingListState(isLoading = true)
                    Log.d("LAB", "loading")

                }
            }
        }.launchIn(viewModelScope)
    }

    fun getMagazines(){
        getTrendingBooksUseCase("", 4,true).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _magazineListState.value = result.data?.let { TrendingListState(booksResponse = it) }!!
                }
                is Resource.Error -> {
                    _magazineListState.value = TrendingListState(
                        error = result.message ?: "An unexpected error occured"
                    )
                    Log.d("LAB", "error")

                }
                is Resource.Loading -> {
                    _magazineListState.value = TrendingListState(isLoading = true)
                    Log.d("LAB", "loading")

                }
            }
        }.launchIn(viewModelScope)
    }

}