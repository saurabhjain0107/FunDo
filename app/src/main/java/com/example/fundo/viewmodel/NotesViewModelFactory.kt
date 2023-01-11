package com.example.fundo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.fundo.model.AuthService

class NotesViewModelFactory(private val authService: AuthService) : ViewModelProvider.Factory  {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NotesViewModel(authService) as T
    }
}