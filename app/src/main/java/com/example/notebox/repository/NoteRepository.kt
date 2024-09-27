package com.example.notebox.repository

import com.example.notebox.model.NoteModel

interface NoteRepository {
    suspend fun insertNote(noteModel: NoteModel)
    suspend fun deleteNote(noteModel: NoteModel)
    suspend fun updateNote(noteModel: NoteModel)
    suspend fun getAllNotes(): List<NoteModel>
}