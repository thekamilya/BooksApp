package com.example.books.presentation.ui.screens.auth.login

import android.content.SharedPreferences
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.books.common.Constants.EMAIL
import com.example.books.common.Constants.NAME
import com.example.books.common.Resource
import com.example.books.domain.use_case.login.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    val sharedPrefs: SharedPreferences,
    val loginUseCase: LoginUseCase):ViewModel() {

    private val _loginState = mutableStateOf(LoginState())
    var loginState = _loginState


    fun login(email:String, password:String){

        loginUseCase(email, password).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    sharedPrefs.edit().putString("token", result.data?.access_token)
                        .apply()
                    sharedPrefs.edit().putString("email", result.data?.email)
                        .apply()
                    sharedPrefs.edit().putString("name", result.data?.name)
                        .apply()
                    EMAIL = email
                    NAME = result.data?.name.toString()
                    Log.d("KAMA",result.data?.email.toString() )

                    _loginState.value = result.data?.let { LoginState(loginResponse = it) }!!

                }
                is Resource.Error -> {
                    _loginState.value = LoginState(
                        error = result.message ?: "An unexpected error occured"
                    )
                }
                is Resource.Loading -> {
                    _loginState.value = LoginState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)

    }

}