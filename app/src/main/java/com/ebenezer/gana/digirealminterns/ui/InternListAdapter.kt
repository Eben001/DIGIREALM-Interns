package com.ebenezer.gana.digirealminterns.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ebenezer.gana.digirealminterns.data.Intern
import com.ebenezer.gana.digirealminterns.databinding.InternListItemBinding

class InternListAdapter(private val onItemClicked: (Intern) -> Unit) :
    ListAdapter<Intern, InternListAdapter.InternViewHolder>(DiffCallback) {


    class InternViewHolder(private var binding: InternListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

            fun bind(intern: Intern){
                binding.apply {
                    internName.text = intern.internName
                    internSkills.text = intern.internAcquiredSkills
                }
            }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InternViewHolder {
        return InternViewHolder(
            InternListItemBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun onBindViewHolder(holder: InternViewHolder, position: Int) {
        val current = getItem(position)
        holder.itemView.setOnClickListener {
            onItemClicked(current)
        }
        holder.bind(current)

    }


    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Intern>() {
            override fun areItemsTheSame(oldItem: Intern, newItem: Intern): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Intern, newItem: Intern): Boolean {
                return oldItem.id == newItem.id && oldItem.internName == newItem.internName
            }

        }
    }
}


