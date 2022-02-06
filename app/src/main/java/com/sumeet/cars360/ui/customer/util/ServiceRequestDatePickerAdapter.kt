package com.sumeet.cars360.ui.customer.util

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.sumeet.cars360.R
import com.sumeet.cars360.databinding.ItemRequestServiceDatePickerBinding

class ServiceRequestDatePickerAdapter(
    private val listener: ServiceRequestDateItemClickListener
) : RecyclerView.Adapter<ServiceRequestDatePickerAdapter.ServiceRequestDatePickerViewHolder>() {

    inner class ServiceRequestDatePickerViewHolder(val binding: ItemRequestServiceDatePickerBinding) :
        RecyclerView.ViewHolder(binding.root)

    private var list = listOf<Pair<Int,String>>()

    fun setStaticList(list: List<Pair<Int,String>>){
        this.list = list
    }

    private var disabledBGColor: Int = 0
    private var disabledTextColor: Int = 0

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ServiceRequestDatePickerViewHolder {
        disabledBGColor = ContextCompat.getColor(parent.context, R.color.gray)
        disabledTextColor = ContextCompat.getColor(parent.context, R.color.white)
        return ServiceRequestDatePickerViewHolder(
            ItemRequestServiceDatePickerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ServiceRequestDatePickerViewHolder, position: Int) {
        if(list[position].second == "Sun"){
            holder.binding.tvDate.setTextColor(disabledTextColor)
            holder.binding.cardView.setCardBackgroundColor(disabledBGColor)
        }
        holder.binding.tvDate.text = list[position].first.toString()
        holder.binding.tvDay.text = list[position].second
    }

    override fun getItemCount(): Int {
        return list.size
    }

}

interface ServiceRequestDateItemClickListener {
    fun onDateItemClicked(date: String)
}