package com.sumeet.cars360.ui.admin.util

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sumeet.cars360.R
import com.sumeet.cars360.data.local.room.model.CarBrandEntity
import com.sumeet.cars360.data.local.room.model.CustomerEntity
import com.sumeet.cars360.databinding.ItemAllCustomerLayoutBinding
import java.util.*
import kotlin.collections.ArrayList

class AllCustomerRecyclerAdapter(
    private val listOfCustomers: ArrayList<CustomerEntity>,
    private val customerEntityClickListener: CustomerEntityClickListener
) : RecyclerView.Adapter<AllCustomerRecyclerAdapter.ItemAllCustomerViewHolder>() {

    private val initialBrandDataList = ArrayList<CustomerEntity>().apply {
        addAll(listOfCustomers)
    }

    fun getFilter(): Filter {
        return customerFilter
    }

    private var customerFilter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filteredList: ArrayList<CustomerEntity> = ArrayList()
            if (constraint == null || constraint.isEmpty()) {
                initialBrandDataList.let {
                    filteredList.addAll(it)
                }
            } else {
                val query = constraint.toString().trim().lowercase(Locale.getDefault())
                initialBrandDataList.forEach {
                    if (it.name?.lowercase(Locale.ROOT)?.contains(query) == true
                        || it.mobileNumber?.lowercase(Locale.ROOT)?.contains(query) == true
                    ) {
                        filteredList.add(it)
                    }
                }
            }
            Log.d("FilterResultLog", filteredList.size.toString())
            val results = FilterResults()
            results.values = filteredList
            return results
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            if (results?.values is ArrayList<*>) {
                listOfCustomers.clear()
                listOfCustomers.addAll(results.values as ArrayList<CustomerEntity>)
                notifyDataSetChanged()
            }
        }
    }

    inner class ItemAllCustomerViewHolder(val binding: ItemAllCustomerLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemAllCustomerViewHolder {
        return ItemAllCustomerViewHolder(
            ItemAllCustomerLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemAllCustomerViewHolder, position: Int) {
        holder.binding.apply {
            val customer = listOfCustomers[position]
            tvName.text = customer.name
            tvEmailId.text = customer.emailId
            tvMobile.text = customer.mobileNumber

            Glide.with(holder.binding.root).load(customer.userImageUrl)
                .error(R.drawable.ic_dummy_profile_pic)
                .into(ivProfileImage)

            root.setOnClickListener {
                customerEntityClickListener.onCustomerEntityItemClicked(customer)
            }

        }

    }

    override fun getItemCount(): Int {
        return listOfCustomers.size
    }

}