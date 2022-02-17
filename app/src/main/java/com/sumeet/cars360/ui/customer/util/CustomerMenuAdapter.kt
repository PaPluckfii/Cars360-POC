package com.sumeet.cars360.ui.customer.util

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sumeet.cars360.databinding.ItemCustomerMenuLayoutBinding

class CustomerMenuAdapter(
    private val listener: CustomerMenuListener
): RecyclerView.Adapter<CustomerMenuAdapter.CustomerMenuViewHolder>(){

    var menuList = listOf<Pair<Drawable?,String>>()

    inner class CustomerMenuViewHolder(val binding: ItemCustomerMenuLayoutBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerMenuViewHolder {
        val binding = ItemCustomerMenuLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CustomerMenuViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CustomerMenuViewHolder, position: Int) {
        holder.binding.apply {
            ivIcon.setImageDrawable(menuList[position].first)
            tvMenuText.text = menuList[position].second

            llPeriodicServices.setOnClickListener {
                listener.onCustomerMenuClicked(menuList[position].second)
            }

        }
    }

    override fun getItemCount(): Int {
        return menuList.size
    }

}

interface CustomerMenuListener{
    fun onCustomerMenuClicked(menuString: String)
}