package com.fast.todoapp.ui.main

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import com.fast.todoapp.data.models.TaskEntity
import com.fast.todoapp.databinding.FragmentMainBinding
import com.fast.todoapp.ui.main.adapter.MainAdapter
import com.fast.todoapp.ui.main.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMainBinding.inflate(inflater)
        val adapter = MainAdapter().getAdapter(binding.tasksRecycler, onCheckedListener = {
            val task = it
            task.checked = !task.checked
            viewModel.update(task)

        }, onLongListener = {
            AlertDialog.Builder(requireContext())
                .setTitle("Are you sure you want to delete this task?")
                .setPositiveButton("Yes") { _: DialogInterface, _: Int ->
                    viewModel.delete(it)
                }
                .setNegativeButton("No") { dialogInterface: DialogInterface, _: Int -> dialogInterface.dismiss() }
                .show()
        })
        viewModel.getTasks().observe(viewLifecycleOwner) {
            adapter.submitList(it.sortedByDescending { it.id }.sortedBy { it.checked })
        }
        binding.addImg.setOnClickListener {
            if (binding.editTask.text.isBlank()) {
                Toast.makeText(requireContext(), "Enter text", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.insert(
                    TaskEntity(
                        title = binding.editTask.text.toString(),
                        checked = false
                    )
                )
                binding.editTask.setText("")
                binding.enterLayout.visibility = View.GONE
            }
        }
        binding.editTask.setOnEditorActionListener { _, i, keyEvent ->
            if (i == EditorInfo.IME_ACTION_DONE) {
                if (binding.editTask.text.isBlank()) {
                    Toast.makeText(requireContext(), "Enter text", Toast.LENGTH_SHORT).show()
                } else {
                    viewModel.insert(
                        TaskEntity(
                            title = binding.editTask.text.toString(),
                            checked = false
                        )
                    )
                    binding.editTask.setText("")
                    binding.enterLayout.visibility = View.GONE
                }
            }
            false
        }
        binding.addFab.setOnClickListener {
            if (binding.enterLayout.visibility == View.GONE) {
                binding.enterLayout.visibility = View.VISIBLE
                binding.editTask.requestFocus()
                val imm =
                    requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.showSoftInput(binding.editTask, InputMethodManager.SHOW_IMPLICIT)
            } else {
                binding.enterLayout.visibility = View.GONE
            }
        }
        return binding.root
    }
}