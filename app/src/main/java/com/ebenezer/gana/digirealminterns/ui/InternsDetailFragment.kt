package com.ebenezer.gana.digirealminterns.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ebenezer.gana.digirealminterns.InternApplication
import com.ebenezer.gana.digirealminterns.InternViewModel
import com.ebenezer.gana.digirealminterns.InternViewModelFactory
import com.ebenezer.gana.digirealminterns.R
import com.ebenezer.gana.digirealminterns.data.Intern
import com.ebenezer.gana.digirealminterns.databinding.FragmentInternsDetailBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder


/**
 * A simple [Fragment] subclass.
 * Use the [InternsDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class InternsDetailFragment : Fragment() {

    private val viewModel: InternViewModel by activityViewModels {
        InternViewModelFactory(
            (activity?.application as InternApplication)
                .database.internsDao()
        )
    }

    private fun bind(intern: Intern){
        binding.apply {
            internName.text = intern.internName
            internSkills.text = intern.internAcquiredSkills

            editIntern.setOnClickListener { editIntern() }
            delete.setOnClickListener { showConfirmationDialog() }

        }
    }

    lateinit var intern: Intern
    private val navigationArgs:InternsDetailFragmentArgs by navArgs()

    private var _binding:FragmentInternsDetailBinding? = null
    private val binding get() = _binding!!




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentInternsDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = navigationArgs.internId
        viewModel.retrieveIntern(id).observe(this.viewLifecycleOwner){selectedIntern->
            intern = selectedIntern
            bind(intern)
        }
    }


    private fun editIntern(){
        val action = InternsDetailFragmentDirections.actionInternsDetailFragmentToAddInternFragment(
            getString(R.string.edit_intern),
            intern.id
        )
        this.findNavController().navigate(action)

    }

    private fun deleteIntern(){
        viewModel.delete(intern)
        findNavController().navigateUp()
    }


    /**
     * Displays an alert dialog to get the user's confirmation before deleting the item.
     */
    private fun showConfirmationDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(android.R.string.dialog_alert_title))
            .setMessage(getString(R.string.delete_question))
            .setCancelable(false)
            .setNegativeButton(getString(R.string.no)) { _, _ -> }
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                deleteIntern()
            }
            .show()
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}