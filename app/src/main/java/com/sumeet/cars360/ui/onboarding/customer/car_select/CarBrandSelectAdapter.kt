package com.sumeet.cars360.ui.onboarding.customer.car_select

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sumeet.cars360.data.local.room.model.CarBrandEntity
import com.sumeet.cars360.data.local.room.model.CarModelEntity
import com.sumeet.cars360.databinding.ItemNewCarLayoutBinding
import com.sumeet.cars360.util.ButtonClickHandler
import java.util.*


class CarSelectViewHolder(val binding: ItemNewCarLayoutBinding) :
    RecyclerView.ViewHolder(binding.root)

@SuppressLint("NotifyDataSetChanged")
class CarBrandSelectAdapter(
    private val listOfCarBrands: ArrayList<CarBrandEntity>,
    private val brandListener: CarBrandSelectListener,
) : RecyclerView.Adapter<CarSelectViewHolder>() {

    private val initialBrandDataList = ArrayList<CarBrandEntity>().apply {
        addAll(listOfCarBrands)
    }

    private var brandFilter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filteredList: ArrayList<CarBrandEntity> = ArrayList()
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
            Log.d("FilterResultLog", filteredList.size.toString())
            val results = FilterResults()
            results.values = filteredList
            return results
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            if (results?.values is ArrayList<*>) {
                listOfCarBrands.clear()
                listOfCarBrands.addAll(results.values as ArrayList<CarBrandEntity>)
                notifyDataSetChanged()
            }
        }
    }

    interface CarBrandSelectListener {
        fun onCarBrandSelected(brandId: String)
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
                    listOfCarBrands[position].brandId?.let { id ->
                        brandListener.onCarBrandSelected(id)
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
    private val listOfModels: List<CarModelEntity>,
    private val listener: CarModelSelectListener
) : RecyclerView.Adapter<CarSelectViewHolder>() {

    interface CarModelSelectListener {
        fun onCarItemSelected(modelEntity: CarModelEntity)
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
        Glide.with(holder.binding.root).load(listOfModels[position].modelLogo)
            .into(holder.binding.ivIcon)

        holder.binding.rlCarEntity.setOnClickListener {
            if (ButtonClickHandler.buttonClicked()) {
                listener.onCarItemSelected(listOfModels[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return listOfModels.size
    }

}