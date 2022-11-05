package com.example.moneymanager.ui.fragment

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.moneymanager.MoneyManagerApplication
import com.example.moneymanager.database.Item
import com.example.moneymanager.databinding.FragmentAddItemBinding
import com.example.moneymanager.viewModel.MoneyManagerViewModel
import com.example.moneymanager.viewModel.MoneyManagerViewModelFactory

class AddItemFragment : Fragment() {

    private val viewModel: MoneyManagerViewModel by activityViewModels {
        MoneyManagerViewModelFactory(
            (activity?.application as MoneyManagerApplication).database
                .itemDao()
        )
    }

    lateinit var item: Item

    private var _binding: FragmentAddItemBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun isEntryValid(): Boolean {
        return viewModel.isEntryValid(
            binding.notesInput.toString(),
            binding.dateInput.toString(),
            binding.amountInput.toString()
        )
    }

    /*
    private fun bind(item: Item) {
        binding.apply {
            notesInput.setText(item.activity, TextView.BufferType.SPANNABLE)
            dateInput.setText(item.date, TextView.BufferType.SPANNABLE)
            amountInput.setText(item.amount.toString(), TextView.BufferType.SPANNABLE)
        }
    }
     */

    private fun addNewItem() {
        if (isEntryValid()) {
            viewModel.addNewItem(
                binding.notesInput.toString(),
                binding.dateInput.toString(),
                binding.amountInput.toString()
            )
            val action = AddItemFragmentDirections.actionAddItemFragmentToListItemFragment()
            findNavController().navigate(action)
        }
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.saveButton.setOnClickListener {
            addNewItem()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        val inputMethodManager = requireActivity().getSystemService(INPUT_METHOD_SERVICE) as
                InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, 0)
        _binding = null
    }
}