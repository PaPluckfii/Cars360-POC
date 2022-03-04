package com.sumeet.cars360.ui.admin.util

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sumeet.cars360.R
import com.sumeet.cars360.data.remote.model.service_logs.ServiceLogResponse
import com.sumeet.cars360.databinding.ItemServiceLogLayoutBinding

class ServiceLogRecyclerAdapter(
    private val listOfCars: List<ServiceLogResponse>,
    private val serviceItemClickListener: OnServiceItemClickListener
) : RecyclerView.Adapter<ServiceLogRecyclerAdapter.ItemServiceLogViewHolder>() {

    inner class ItemServiceLogViewHolder(val binding: ItemServiceLogLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemServiceLogViewHolder {
        return ItemServiceLogViewHolder(
            ItemServiceLogLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemServiceLogViewHolder, position: Int) {
        holder.binding.apply {
            val carDetails = listOfCars[position]
//            tvCustomerName.text = carDetails.
//            tvCarCompany.text = carDetails.
//            tvJobId.text = carDetails.
            tvDate.text = carDetails.createdDate
            tvStatus.text = carDetails.statusName

            Glide.with(holder.binding.root).load(carDetails.frontPic)
                .error(R.drawable.ic_dummy_profile_pic)
                .into(ivImage)

            root.setOnClickListener {
                serviceItemClickListener.onServiceItemClicked(carDetails)
            }

        }

    }

    override fun getItemCount(): Int {
        return listOfCars.size
    }

}