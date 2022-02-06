package com.sumeet.cars360.ui.customer.util

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sumeet.cars360.databinding.ItemFaqLayoutBinding

class FAQRecyclerAdapter(
    private val listOfFAQs: List<Pair<String, String>>
) : RecyclerView.Adapter<FAQRecyclerAdapter.FAQViewHolder>() {

    inner class FAQViewHolder(val binding: ItemFaqLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FAQViewHolder {
        return FAQViewHolder(
            ItemFaqLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: FAQViewHolder, position: Int) {
        holder.binding.tvQuestion.text = listOfFAQs[position].first
        holder.binding.tvAnswer.text = listOfFAQs[position].second
    }

    override fun getItemCount(): Int {
        return listOfFAQs.size
    }

}