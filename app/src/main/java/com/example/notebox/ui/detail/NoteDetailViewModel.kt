package com.example.notebox.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notebox.Event
import com.example.notebox.model.NoteModel
import com.example.notebox.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch

import javax.inject.Inject

@HiltViewModel
class NoteDetailViewModel @Inject constructor(
    private val noteRepository: NoteRepository
) : ViewModel() {

    private val _updateStatus = MutableLiveData<Event<Boolean>>()
    val updateStatus: LiveData<Event<Boolean>> get() = _updateStatus

    private val _deleteStatus = MutableLiveData<Event<Boolean>>()
    val deleteStatus: LiveData<Event<Boolean>> get() = _deleteStatus

    fun updateNote(note: NoteModel) {
        viewModelScope.launch {
            try {
                noteRepository.updateNote(note)
                _updateStatus.value = Event(true)
            } catch (e: Exception) {
                _updateStatus.value = Event(false)
            }
        }
    }

    fun deleteNote(note: NoteModel){
        viewModelScope.launch {
            try {
                noteRepository.deleteNote(note)
                _deleteStatus.value = Event(true)
            } catch (e: Exception) {
                _deleteStatus.value = Event(false)
            }
        }
    }
}