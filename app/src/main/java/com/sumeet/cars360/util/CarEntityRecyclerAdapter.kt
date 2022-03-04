package com.sumeet.cars360.util

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sumeet.cars360.data.local.room.model.CarEntity
import com.sumeet.cars360.databinding.ItemCustomerCarBinding

class CarEntityRecyclerAdapter(
    private val listOfCars: List<CarEntity>,
    private val carEntityItemClickListener: CarEntityItemClickListener
) : RecyclerView.Adapter<CarEntityRecyclerAdapter.CarsViewHolder>() {

    inner class CarsViewHolder(val binding: ItemCustomerCarBinding) :
        RecyclerView.ViewHolder(binding.root)

    interface CarEntityItemClickListener {
        fun onCarItemClicked(carEntity: CarEntity)
    }

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
            tvCarName.text = "${listOfCars[position].carBrand} ${listOfCars[position].carModelName}"
            tvCarLicencePlate.text = listOfCars[position].plateNo
            tvLastServiceDate.text =
                if (listOfCars[position].lastServiceDate != null)
                    "Last Service Date : ${listOfCars[position].lastServiceDate}"
                else "Last Service Date : No Data Available"
            Glide.with(holder.binding.root).load(listOfCars[position].carModelLogo)
                .into(ivCarFrontPic)
            Glide.with(holder.binding.root).load(listOfCars[position].carBrandLogo)
                .into(ivBrandLogo)

            root.setOnClickListener {
                carEntityItemClickListener.onCarItemClicked(listOfCars[position])
            }
        }

    }

    override fun getItemCount(): Int {
        return listOfCars.size
    }

}