package com.example.fundo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fundo.model.AuthListener
import com.example.fundo.model.AuthService
import com.example.fundo.model.User

class LoginViewModel(private val authService: AuthService) : ViewModel() {


    private var _UserLoginStatus = MutableLiveData<AuthListener>()
    val userLoginStatus = _UserLoginStatus as LiveData<AuthListener>


    fun userLogin(user: User){
        authService.userLogin(user){
            if(it.status){
                _UserLoginStatus.value = it
            }
        }
    }

}