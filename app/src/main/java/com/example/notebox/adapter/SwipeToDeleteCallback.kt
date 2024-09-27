package com.example.notebox.adapter

import android.graphics.Canvas
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.notebox.R
import com.example.notebox.ui.list.NoteListViewModel

class SwipeToDeleteCallback(
    private val adapter: NoteAdapter,
    private val viewModel: NoteListViewModel
) : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        val itemView = viewHolder.itemView
        val itemHeight = itemView.height

        val trashIcon = ContextCompat.getDrawable(recyclerView.context, R.drawable.ic_delete)
        val iconMargin = ((itemHeight - trashIcon?.intrinsicHeight!!) ?: 0) / 2
        val iconTop = itemView.top + iconMargin
        val iconBottom = iconTop + (trashIcon.intrinsicHeight ?: 0)

        val offset = 80

        trashIcon.setBounds(
            itemView.right - trashIcon.intrinsicWidth - offset,
            iconTop,
            itemView.right - offset,
            iconBottom
        )
        trashIcon.draw(c)

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position = viewHolder.adapterPosition
        adapter.removeItem(position, viewModel) // ViewModel'i geçir
    }
}