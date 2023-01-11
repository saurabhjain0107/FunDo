package com.example.fundo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fundo.model.AuthListener
import com.example.fundo.model.AuthService
import com.example.fundo.model.User

class RegisterViewModel(private val authService: AuthService) : ViewModel() {

    private var _UserRegisterStatus = MutableLiveData<AuthListener>()
    val userRegisterStatus = _UserRegisterStatus as LiveData<AuthListener>

    fun userRegister(user:User){
        authService.userRegister(user){
            if(it.status){
                _UserRegisterStatus.value = it
            }
        }
    }



}