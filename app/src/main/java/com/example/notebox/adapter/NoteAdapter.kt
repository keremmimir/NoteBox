package com.example.notebox.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.notebox.model.NoteModel
import com.example.notebox.databinding.ListRowBinding
import com.example.notebox.ui.list.NoteListViewModel

class NoteAdapter(
    private val onDetailToggle: (NoteModel) -> Unit
) :
    ListAdapter<NoteModel, NoteAdapter.Holder>(
        DiffCallback()
    ) {

    inner class Holder(val binding: ListRowBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(noteModel: NoteModel) {
            with(binding) {
                titleText.text = noteModel.title
                descriptionText.text = noteModel.description
            }

            itemView.setOnClickListener {
                onDetailToggle(noteModel)
            }
        }
    }

    fun removeItem(position: Int, viewModel: NoteListViewModel) {
        val note = getItem(position)
        viewModel.deleteNote(note)


        val currentList = currentList.toMutableList()
        currentList.removeAt(position)
        submitList(currentList)
        notifyItemRemoved(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ListRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffCallback : DiffUtil.ItemCallback<NoteModel>() {
        override fun areItemsTheSame(oldItem: NoteModel, newItem: NoteModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: NoteModel, newItem: NoteModel): Boolean {
            return oldItem == newItem
        }
    }
}