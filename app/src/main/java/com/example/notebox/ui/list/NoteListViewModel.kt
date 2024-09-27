package com.example.notebox.ui.list

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
class NoteListViewModel @Inject constructor(private val noteRepository: NoteRepository) :
    ViewModel() {

    private val _noteList = MutableLiveData<List<NoteModel>>()
    val noteList: LiveData<List<NoteModel>> get() = _noteList

    private val _deleteStatus = MutableLiveData<Event<Boolean>>()
    val deleteStatus: LiveData<Event<Boolean>> get() = _deleteStatus

    private val _filteredItems = MutableLiveData<List<NoteModel>?>()
    val filteredItems: LiveData<List<NoteModel>?> get() = _filteredItems

    init {
        getAllList()
    }

    private fun getAllList() {
        viewModelScope.launch {
            val notes = noteRepository.getAllNotes()
            _noteList.value = notes
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

    fun searchItems(query: String) {
        val filteredList = _noteList.value?.filter {
            it.title?.contains(query, ignoreCase = true) ?: false
        }
        _filteredItems.value = filteredList
    }
}