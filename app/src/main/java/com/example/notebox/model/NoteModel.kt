package com.example.notebox.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Entity(tableName = "notes")
@Parcelize
data class NoteModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String?,
    val description: String?
) : Parcelable
