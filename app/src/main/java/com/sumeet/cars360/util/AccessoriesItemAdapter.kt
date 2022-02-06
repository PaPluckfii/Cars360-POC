package com.sumeet.cars360.util

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sumeet.cars360.databinding.ItemAccessoriesBinding

class AccessoriesItemAdapter(
    private val listOfAccessories: List<String>,
    private val checkBoxClickListener: CheckBoxClickListener
) : RecyclerView.Adapter<AccessoriesItemAdapter.AccessoriesItemViewHolder>() {

    inner class AccessoriesItemViewHolder(val binding: ItemAccessoriesBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccessoriesItemViewHolder {
        return AccessoriesItemViewHolder(
            ItemAccessoriesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: AccessoriesItemViewHolder, position: Int) {
        holder.binding.apply {
            checkBox.text = listOfAccessories[position]
            checkBox.setOnClickListener {
                checkBoxClickListener.onCheckBoxItemClicked(listOfAccessories[position], checkBox.isChecked)
            }
        }
    }

    override fun getItemCount(): Int {
        return listOfAccessories.size
    }

}

interface CheckBoxClickListener {
    fun onCheckBoxItemClicked(string: String,isChecked: Boolean)
}