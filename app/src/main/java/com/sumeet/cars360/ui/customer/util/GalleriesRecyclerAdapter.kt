package com.sumeet.cars360.ui.customer.util

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sumeet.cars360.databinding.ItemGalleriesLayoutBinding

//class GalleriesRecyclerAdapter(
//    private val listOfGalleries: List<Galleries>
//): RecyclerView.Adapter<GalleriesRecyclerAdapter.GalleriesViewHolder>() {
//
//    inner class GalleriesViewHolder(val binding: ItemGalleriesLayoutBinding)
//        : RecyclerView.ViewHolder(binding.root)
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleriesViewHolder {
//        return GalleriesViewHolder(
//            ItemGalleriesLayoutBinding.inflate(
//                LayoutInflater.from(parent.context),
//                parent,
//                false
//            )
//        )
//    }
//
//    override fun onBindViewHolder(holder: GalleriesViewHolder, position: Int) {
//        Glide.with(holder.binding.root)
//            .load(listOfGalleries[position].beforeFrontImageUrl)
//            .into(holder.binding.ivBeforeFront)
//        Glide.with(holder.binding.root)
//            .load(listOfGalleries[position].afterFrontImageUrl)
//            .into(holder.binding.ivAfterFront)
//    }
//
//    override fun getItemCount(): Int {
//        return listOfGalleries.size
//    }
//
//}