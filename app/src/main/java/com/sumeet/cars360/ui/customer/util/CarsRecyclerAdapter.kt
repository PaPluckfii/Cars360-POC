package com.sumeet.cars360.ui.customer.util

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sumeet.cars360.data.local.room.model.CarEntity
import com.sumeet.cars360.data.remote.model.user_cars.CarDetailsResponse
import com.sumeet.cars360.databinding.ItemCustomerCarBinding

class CarsRecyclerAdapter(
    private val listOfCars: List<CarDetailsResponse>,
    private val carItemClickListener: CarItemClickListener
) : RecyclerView.Adapter<CarsRecyclerAdapter.CarsViewHolder>() {

    inner class CarsViewHolder(val binding: ItemCustomerCarBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarsViewHolder {
        return CarsViewHolder(
            ItemCustomerCarBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CarsViewHolder, position: Int) {
        holder.binding.apply {
            tvCarName.text = "${listOfCars[position].brandName} ${listOfCars[position].modelName}"
//            tvLicencePlate.text = listOfCars[position].plateNo
//            tvTextLastServiceDate.text =
//                if (listOfCars[position].lastServiceDate != null)
//                    "Last Service Date : ${listOfCars[position].lastServiceDate}"
//                else "Last Service Date : No Data Available"
            Glide.with(holder.binding.root).load(listOfCars[position].modelImage)
                .into(ivCarFrontPic)
            Glide.with(holder.binding.root).load(listOfCars[position].brandLogo)
                .into(ivBrandLogo)

            root.setOnClickListener {
                carItemClickListener.onCarItemClicked(listOfCars[position])
            }

        }

    }

    override fun getItemCount(): Int {
        return listOfCars.size
    }

}