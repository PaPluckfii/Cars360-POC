package com.sumeet.cars360.ui.onboarding.fragments.new_customer

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sumeet.cars360.data.remote.model.car_entities.CarBrandResponse
import com.sumeet.cars360.data.remote.model.car_entities.CarModelResponse
import com.sumeet.cars360.databinding.ItemNewCarLayoutBinding
import com.sumeet.cars360.util.ButtonClickHandler

class CarSelectAdapter(
    private val listener: CarSelectListener
) : RecyclerView.Adapter<CarSelectAdapter.CarSelectViewHolder>() {

    inner class CarSelectViewHolder(val binding: ItemNewCarLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    interface CarSelectListener {
        fun onCarItemSelected(carBrand: CarBrandResponse, carModel: CarModelResponse?)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarSelectViewHolder {
        return CarSelectViewHolder(
            ItemNewCarLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    private var adapterType: String = ""
    private lateinit var selectedBrand: CarBrandResponse
    private var listOfCarBrands = listOf<CarBrandResponse>()
    private var listOfCarModels = listOf<CarModelResponse>()

    fun setCarBrandList(list: List<CarBrandResponse>) {
        adapterType = "BRAND"
        listOfCarBrands = list
        notifyDataSetChanged()
    }

    fun setCarModelList(list: List<CarModelResponse>) {
        adapterType = "MODEL"
        listOfCarModels = list
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: CarSelectViewHolder, position: Int) {
        if (adapterType == "BRAND") {
            holder.binding.apply {
                Glide.with(holder.binding.root).load(listOfCarBrands[position].brandLogo)
                    .into(holder.binding.ivIcon)
                tvText.text = listOfCarBrands[position].brandName
                rlCarEntity.setOnClickListener {
                    if (ButtonClickHandler.buttonClicked()) {
                        selectedBrand = listOfCarBrands[position]
                        itemClicked(carBrand = listOfCarBrands[position], null)
                    }
                }
//                ivIcon.setOnClickListener {
//                    if (ButtonClickHandler.buttonClicked()) {
//                        selectedBrand = listOfCarBrands[position]
//                        itemClicked(carBrand = listOfCarBrands[position], null)
//                    }
//                }
//                tvText.setOnClickListener {
//                    if (ButtonClickHandler.buttonClicked()) {
//                        selectedBrand = listOfCarBrands[position]
//                        itemClicked(carBrand = listOfCarBrands[position], null)
//                    }
//                }
            }
        } else {
            holder.binding.tvText.text = listOfCarModels[position].modelName
            Glide.with(holder.binding.root).load(listOfCarModels[position].modelImage)
                .into(holder.binding.ivIcon)

            holder.binding.ivIcon.setOnClickListener {
                if (ButtonClickHandler.buttonClicked()) {
                    itemClicked(carBrand = selectedBrand, listOfCarModels[position])
                }
            }
            holder.binding.tvText.setOnClickListener {
                if (ButtonClickHandler.buttonClicked()) {
                    itemClicked(carBrand = selectedBrand, listOfCarModels[position])
                }
            }
        }
    }

    private fun itemClicked(carBrand: CarBrandResponse, carModel: CarModelResponse?) {
        listener.onCarItemSelected(carBrand, carModel)
    }

    override fun getItemCount(): Int {
        return if (adapterType == "BRAND") listOfCarBrands.size
        else listOfCarModels.size
    }

}