package com.example.moneymanager.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moneymanager.MoneyManagerApplication
import com.example.moneymanager.database.Item
import com.example.moneymanager.databinding.FragmentListItemBinding
import com.example.moneymanager.databinding.ListItemBinding
import com.example.moneymanager.ui.adapter.ListItemAdapter
import com.example.moneymanager.viewModel.MoneyManagerViewModel
import com.example.moneymanager.viewModel.MoneyManagerViewModelFactory

class ListItemFragment : Fragment() {


    private val viewModel: MoneyManagerViewModel by activityViewModels {
        MoneyManagerViewModelFactory(
            (activity?.application as MoneyManagerApplication).database
                .itemDao()
        )
    }

    private var _binding2: ListItemBinding? = null
    private val binding2 get() = _binding2!!

    private var _binding: FragmentListItemBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.layoutManager = LinearLayoutManager(this.context)
        binding.recyclerView.adapter = ListItemAdapter()

        viewModel.allItems.observe(this.viewLifecycleOwner) { items ->
            items.let {
                ListItemAdapter().submitList(it)
            }
        }

        binding.addButton.setOnClickListener {
            val action = ListItemFragmentDirections.actionListItemFragmentToAddItemFragment()
            this.findNavController().navigate(action)
        }
    }
}