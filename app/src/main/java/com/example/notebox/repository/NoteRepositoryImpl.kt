package com.example.notebox.repository

import com.example.notebox.database.NoteDao
import com.example.notebox.model.NoteModel
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
    private val noteDao: NoteDao
) : NoteRepository {

    override suspend fun insertNote(noteModel: NoteModel) {
        noteDao.insertNote(noteModel)
    }

    override suspend fun deleteNote(noteModel: NoteModel) {
        noteDao.deleteNote(noteModel)
    }

    override suspend fun updateNote(noteModel: NoteModel) {
        noteDao.updateNote(noteModel)
    }

    override suspend fun getAllNotes(): List<NoteModel> {
        return noteDao.getAllNote()
    }
}