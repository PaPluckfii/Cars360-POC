package com.sumeet.cars360.ui.customer.util

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sumeet.cars360.databinding.ItemViewpagerAdBinding

class AdViewPagerAdapter(
    private val adList: List<Drawable?>
) : RecyclerView.Adapter<AdViewPagerAdapter.AdViewPagerViewHolder>() {

    inner class AdViewPagerViewHolder(val binding: ItemViewpagerAdBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdViewPagerViewHolder {
        val binding = ItemViewpagerAdBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return AdViewPagerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AdViewPagerViewHolder, position: Int) {
        Glide.with(holder.binding.root).load(adList[position]).into(holder.binding.ivImage)
    }

    override fun getItemCount(): Int {
        return adList.size
    }

}