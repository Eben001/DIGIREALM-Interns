package com.ebenezer.gana.digirealminterns.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ebenezer.gana.digirealminterns.InternApplication
import com.ebenezer.gana.digirealminterns.InternViewModel
import com.ebenezer.gana.digirealminterns.InternViewModelFactory
import com.ebenezer.gana.digirealminterns.data.Intern
import com.ebenezer.gana.digirealminterns.databinding.FragmentAddInternBinding


/**
 * A simple [Fragment] subclass.
 * Use the [AddInternFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddInternFragment : Fragment() {


    private val viewModel: InternViewModel by activityViewModels {
        InternViewModelFactory(
            (activity?.application as InternApplication)
                .database.internsDao()
        )
    }

    private fun bind(intern: Intern) {
        binding.apply {
            internName.setText(intern.internName)
            internSkills.setText(intern.internAcquiredSkills)

            saveAction.setOnClickListener { updateIntern() }
        }
    }

    private fun updateIntern() {
        if (isEntryValid()) {
            viewModel.updateIntern(
                this.navigationArgs.internId,
                this.binding.internName.text.toString(),
                this.binding.internSkills.text.toString()

            )
            val action = AddInternFragmentDirections.actionAddInternFragmentToInternsListFragment()
            findNavController().navigate(action)
        }

    }

    lateinit var intern: Intern


    private val navigationArgs: InternsDetailFragmentArgs by navArgs()


    private var _binding: FragmentAddInternBinding? = null
    private val binding get() = _binding!!


    private fun isEntryValid(): Boolean {
        return viewModel.isEntryValid(
            binding.internName.text.toString(),
            binding.internSkills.text.toString()
        )
    }

    private fun addNewIntern() {
        if (isEntryValid()) {
            viewModel.addNewIntern(
                binding.internName.text.toString(),
                binding.internSkills.text.toString()
            )
        }

        val action = AddInternFragmentDirections.actionAddInternFragmentToInternsListFragment()
        findNavController().navigate(action)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddInternBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = navigationArgs.internId

        if (id > 0) {
            viewModel.retrieveIntern(id).observe(this.viewLifecycleOwner) { selectedIntern ->
                intern = selectedIntern
                bind(intern)
            }
        } else {
            binding.saveAction.setOnClickListener {
                addNewIntern()
            }
        }


    }


    /**
     * Called before fragment is destroyed.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        // Hide keyboard.
        val inputMethodManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as
                InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, 0)
        _binding = null
    }
}