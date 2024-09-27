package com.example.notebox.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.notebox.databinding.FragmentNoteDetailBinding
import com.example.notebox.model.NoteModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NoteDetailFragment : Fragment() {
    private var _binding: FragmentNoteDetailBinding? = null
    private val binding get() = _binding!!
    private val detailViewModel: NoteDetailViewModel by viewModels()
    private val args by navArgs<NoteDetailFragmentArgs>()
    private lateinit var note: NoteModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNoteDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeData()
        setupViews()
    }

    private fun setupViews() {
        note = args.noteModel

        with(binding) {
            note.let {
                binding.editTitle.setText(note.title)
                binding.editDescription.setText(note.description)
            }

            okeyButton.setOnClickListener {
                //update
                updateNote()
            }

            deleteButton.setOnClickListener {
                //delete
                deleteNote()
            }

            backButton.setOnClickListener {
                findNavController().navigateUp()
            }

            val onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
                okeyButton.visibility = if (hasFocus) View.VISIBLE else View.GONE
            }

            editTitle.onFocusChangeListener = onFocusChangeListener
            editDescription.onFocusChangeListener = onFocusChangeListener
        }
    }

    private fun updateNote() {
        val updatedNote = note.copy(
            title = binding.editTitle.text.toString(),
            description = binding.editDescription.text.toString()
        )
        detailViewModel.updateNote(updatedNote)
        navigateToListFragment()
    }

    private fun deleteNote() {
        detailViewModel.deleteNote(note)
        navigateToListFragment()
    }

    private fun observeData() {
        detailViewModel.updateStatus.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { isSuccess ->
                if (isSuccess) {
                    navigateToListFragment()
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Not güncellenirken bir hata oluştu.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        detailViewModel.deleteStatus.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { isSuccess ->
                if (isSuccess) {
                    navigateToListFragment()
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Not silinirken bir hata oluştu.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun navigateToListFragment() {
        val action = NoteDetailFragmentDirections.actionNoteDetailFragmentToNoteListFragment()
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}