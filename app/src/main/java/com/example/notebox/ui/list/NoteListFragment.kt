package com.example.notebox.ui.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.notebox.adapter.SwipeToDeleteCallback
import com.example.notebox.adapter.NoteAdapter
import com.example.notebox.databinding.FragmentNoteListBinding
import com.example.notebox.model.NoteModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NoteListFragment : Fragment() {
    private var _binding: FragmentNoteListBinding? = null
    private val binding get() = _binding!!
    private val listViewModel: NoteListViewModel by viewModels()
    private lateinit var adapter: NoteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNoteListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeData()
        setupViews()
    }

    private fun setupViews() {
        adapter = NoteAdapter(
            onDetailToggle = { noteModel ->
                navigateToDetailFragment(noteModel)
            }
        )

        binding.recylerview.adapter = adapter

        binding.floatingActionButton.setOnClickListener {
            navigateToCreateFragment()
        }

        searchBox()

        val itemTouchHelper = ItemTouchHelper(SwipeToDeleteCallback(adapter, listViewModel))
        itemTouchHelper.attachToRecyclerView(binding.recylerview)
    }

    private fun observeData() {
        listViewModel.noteList.observe(viewLifecycleOwner) { noteList ->
            adapter.submitList(noteList)
        }

        listViewModel.filteredItems.observe(viewLifecycleOwner) { filteredItems ->
            adapter.submitList(filteredItems)
        }
    }

    private fun searchBox() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    listViewModel.searchItems(it)
                }
                return true
            }
        })
    }

    private fun navigateToCreateFragment() {
        val action = NoteListFragmentDirections.actionNoteListFragmentToNoteCreateFragment()
        findNavController().navigate(action)
    }

    private fun navigateToDetailFragment(note: NoteModel) {
        val action = NoteListFragmentDirections.actionNoteListFragmentToNoteDetailFragment(note)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}