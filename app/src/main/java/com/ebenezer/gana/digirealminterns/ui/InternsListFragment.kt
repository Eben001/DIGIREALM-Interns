package com.ebenezer.gana.digirealminterns.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ebenezer.gana.digirealminterns.InternApplication
import com.ebenezer.gana.digirealminterns.InternViewModel
import com.ebenezer.gana.digirealminterns.InternViewModelFactory
import com.ebenezer.gana.digirealminterns.R
import com.ebenezer.gana.digirealminterns.databinding.FragmentInternsListBinding
import com.ebenezer.gana.digirealminterns.ui.adapter.InternListAdapter


class InternsListFragment : Fragment() {

    private val viewModel: InternViewModel by activityViewModels {
        InternViewModelFactory(
            (activity?.application as InternApplication)
                .database.internsDao()
        )
    }
    private var _binding: FragmentInternsListBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentInternsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = InternListAdapter {
            val action =
                InternsListFragmentDirections.actionInternsListFragmentToInternsDetailFragment(
                    it.id
                )
            this.findNavController().navigate(action)
        }

        binding.recyclerView.adapter = adapter
        viewModel.allInterns.observe(this.viewLifecycleOwner) { interns ->
            interns.let {
                adapter.submitList(it)
            }
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(this.context)

        binding.floatingActionButton.setOnClickListener {
            val action = InternsListFragmentDirections.actionInternsListFragmentToAddIntern(
                getString(R.string.add_new_intern)
            )
            this.findNavController().navigate(action)

        }

    }


}