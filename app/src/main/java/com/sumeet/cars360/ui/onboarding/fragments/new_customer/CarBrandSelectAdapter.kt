package com.sumeet.cars360.ui.onboarding.fragments.new_customer

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sumeet.cars360.data.remote.model.car_entities.CarBrandResponse
import com.sumeet.cars360.data.remote.model.car_entities.CarModelResponse
import com.sumeet.cars360.data.remote.model.car_entities.car_brand.AllCarBrands
import com.sumeet.cars360.data.remote.model.car_entities.car_model.AllCarModels
import com.sumeet.cars360.databinding.ItemNewCarLayoutBinding
import com.sumeet.cars360.util.ButtonClickHandler
import java.util.*
import kotlin.collections.ArrayList


class CarSelectViewHolder(val binding: ItemNewCarLayoutBinding) :
    RecyclerView.ViewHolder(binding.root)

@SuppressLint("NotifyDataSetChanged")
class CarBrandSelectAdapter(
    private val listOfCarBrands: ArrayList<CarBrandResponse>,
    private val brandListener: CarBrandSelectListener,
) : RecyclerView.Adapter<CarSelectViewHolder>() {

    private val initialBrandDataList = ArrayList<CarBrandResponse>().apply {
        addAll(listOfCarBrands)
    }

    private var brandFilter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filteredList: ArrayList<CarBrandResponse> = ArrayList()
            if (constraint == null || constraint.isEmpty()) {
                initialBrandDataList.let {
                    filteredList.addAll(it)
                }
            } else {
                val query = constraint.toString().trim().lowercase(Locale.getDefault())
                initialBrandDataList.forEach {
                    if (it.brandName?.lowercase(Locale.ROOT)?.contains(query) == true) {
                        filteredList.add(it)
                    }
                }
            }
            Log.d("FilterResultLog",filteredList.size.toString())
            val results = FilterResults()
            results.values = filteredList
            return results
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            if (results?.values is ArrayList<*>) {
                listOfCarBrands.clear()
                listOfCarBrands.addAll(results.values as ArrayList<CarBrandResponse>)
                notifyDataSetChanged()
            }
        }
    }

    interface CarBrandSelectListener {
        fun onCarBrandSelected(listOfModels: List<CarModelResponse>)
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

    override fun onBindViewHolder(holder: CarSelectViewHolder, position: Int) {
            holder.binding.apply {
                Glide.with(holder.binding.root).load(listOfCarBrands[position].brandLogo)
                    .into(holder.binding.ivIcon)
                tvText.text = listOfCarBrands[position].brandName
                rlCarEntity.setOnClickListener {
                    if (ButtonClickHandler.buttonClicked()) {
                        listOfCarBrands[position].carModelResponses?.let { list ->
                            brandListener?.onCarBrandSelected(list)
                        }
                    }
                }
            }
    }

    override fun getItemCount(): Int {
        return listOfCarBrands.size
    }

    fun getFilter(): Filter {
        return brandFilter
    }
}

class CarModelSelectAdapter(
    private val listOfModels: List<CarModelResponse>,
    private val listener: CarModelSelectListener
): RecyclerView.Adapter<CarSelectViewHolder>(){

    interface CarModelSelectListener {
        fun onCarItemSelected(id: String)
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

    override fun onBindViewHolder(holder: CarSelectViewHolder, position: Int) {
        holder.binding.tvText.text = listOfModels[position].modelName
        Glide.with(holder.binding.root).load(listOfModels[position].modelImage)
            .into(holder.binding.ivIcon)

        holder.binding.rlCarEntity.setOnClickListener {
            if (ButtonClickHandler.buttonClicked()) {
                listOfModels[position].modelId?.let { id ->
                    listener.onCarItemSelected(id)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return listOfModels.size
    }

}