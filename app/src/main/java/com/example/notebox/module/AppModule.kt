package com.example.notebox.module

import android.content.Context
import androidx.room.Room
import com.example.notebox.database.NoteDao
import com.example.notebox.database.NoteDatabase
import com.example.notebox.repository.NoteRepository
import com.example.notebox.repository.NoteRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): NoteDatabase {
        return Room.databaseBuilder(
            appContext,
            NoteDatabase::class.java,
            "note_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideNoteDao(database: NoteDatabase): NoteDao {
        return database.noteDao()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(
        noteDao: NoteDao
    ): NoteRepository {
        return NoteRepositoryImpl(noteDao)
    }
}