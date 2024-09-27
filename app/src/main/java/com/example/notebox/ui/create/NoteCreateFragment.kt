package com.example.notebox.ui.create

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.notebox.databinding.FragmentNoteCreateBinding
import com.example.notebox.model.NoteModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NoteCreateFragment : Fragment() {
    private var _binding: FragmentNoteCreateBinding? = null
    private val binding get() = _binding!!
    private val createViewModel: NoteCreateViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNoteCreateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeData()
        setupViews()
    }

    private fun setupViews() {
        binding.okeyButton.setOnClickListener {
            val title = binding.editTitle.text.toString()
            val description = binding.editDescription.text.toString()
            val note = NoteModel(title = title, description = description)
            createViewModel.insertNote(note)
        }

        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun observeData() {
        createViewModel.status.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { isSuccess ->
                if (isSuccess) {
                    Toast.makeText(requireContext(), "Kaydedildi.", Toast.LENGTH_SHORT).show()
                    navigateToListFragment()
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Not eklenirken bir hata oluştu.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun navigateToListFragment() {
        val action = NoteCreateFragmentDirections.actionNoteCreateFragmentToNoteListFragment()
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}