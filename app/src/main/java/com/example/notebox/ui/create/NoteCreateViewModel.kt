package com.example.notebox.ui.create

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
class NoteCreateViewModel @Inject constructor(private val noteRepository: NoteRepository) : ViewModel() {

    private val _status = MutableLiveData<Event<Boolean>>()
    val status: LiveData<Event<Boolean>> get() = _status

    fun insertNote(note: NoteModel) {
        viewModelScope.launch {
            try {
                noteRepository.insertNote(note)
                _status.value = Event(true)
            } catch (e: Exception) {
                _status.value = Event(false)
            }
        }
    }
}