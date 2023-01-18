package com.example.fundo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fundo.model.*

class NotesViewModel(private val noteService: NoteService) : ViewModel() {


    private var _UserNotesStatus = MutableLiveData<NoteListener>()
    val userNotesStatus = _UserNotesStatus as LiveData<NoteListener>

    fun userNotes(notes : Notes){
        noteService.userNotes(notes) {
            if(it.status){
                _UserNotesStatus.value = it
            }
        }
    }


}