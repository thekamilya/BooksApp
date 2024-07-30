package com.example.books.presentation.ui.screens.auth.signup

import android.content.SharedPreferences
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.books.common.Constants
import com.example.books.common.Resource
import com.example.books.domain.use_case.login.LoginUseCase
import com.example.books.domain.use_case.signup.SignupUseCase
import com.example.books.presentation.ui.screens.auth.login.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    val sharedPrefs: SharedPreferences,
    val signupUseCase: SignupUseCase
):ViewModel() {
    private val _signupState = mutableStateOf(SignupState())
    var signupState = _signupState



    fun signup(name:String, email:String, password:String){

        signupUseCase(name, email, password).onEach { result ->
            when (result) {
                is Resource.Success -> {

                    _signupState.value = result.data?.let { SignupState(signupResponse = it) }!!

                }
                is Resource.Error -> {
                    _signupState.value = SignupState(
                        error = result.message ?: "An unexpected error occured"
                    )
                }
                is Resource.Loading -> {
                    _signupState.value = SignupState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)

    }

}