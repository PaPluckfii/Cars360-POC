package com.sumeet.cars360.ui.admin

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sumeet.cars360.R
import com.sumeet.cars360.data.remote.model.user.UserResponse
import com.sumeet.cars360.databinding.ItemAllCustomerLayoutBinding

class AllCustomerRecyclerAdapter(
    private val listOfCars: List<UserResponse>,
    private val allCustomerItemClickListener: OnAllCustomerItemClickListener
) : RecyclerView.Adapter<AllCustomerRecyclerAdapter.ItemAllCustomerViewHolder>() {

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
            val carDetails = listOfCars[position]
            tvName.text = carDetails.name
            tvEmailId.text = carDetails.email
            tvMobile.text = carDetails.mobile

            Glide.with(holder.binding.root).load(carDetails.profileImage)
                .error(R.drawable.ic_dummy_profile_pic)
                .into(ivProfileImage)

            root.setOnClickListener {
                allCustomerItemClickListener.onAllCustomerItemClicked(carDetails)
            }

        }

    }

    override fun getItemCount(): Int {
        return listOfCars.size
    }

}