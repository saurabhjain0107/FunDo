package com.example.fundo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fundo.model.AuthListener
import com.example.fundo.model.AuthService
import com.example.fundo.model.Notes

class NotesViewModel(private val authService: AuthService) : ViewModel() {


    private var _UserNotesStatus = MutableLiveData<AuthListener>()
    val userNotesStatus = _UserNotesStatus as LiveData<AuthListener>

    fun userNotes(notes : Notes){
        authService.userNotes(notes){
            if(it.status){
                _UserNotesStatus.value = it
            }
        }
    }


}