package com.example.fundo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.fundo.model.AuthService
import com.example.fundo.model.NoteService

class NotesViewModelFactory(private val noteService: NoteService) : ViewModelProvider.Factory  {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NotesViewModel(noteService) as T
    }
}