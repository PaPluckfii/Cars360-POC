package com.sumeet.cars360.ui.customer.util

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sumeet.cars360.data.remote.old_model.Cars360Document
import com.sumeet.cars360.databinding.ItemProfileBottomSheetBinding

class ProfileBottomSheetRecyclerAdapter(
    private val profileBottomSheetItemClickListener: ProfileBottomSheetItemClickListener
): RecyclerView.Adapter<ProfileBottomSheetRecyclerAdapter.ProfileBottomSheetViewHolder>() {

    inner class ProfileBottomSheetViewHolder(val binding: ItemProfileBottomSheetBinding)
        : RecyclerView.ViewHolder(binding.root)

    private var list = emptyList<Cars360Document>()

    fun setStaticList(listOfDocs: List<Cars360Document>){
        list = listOfDocs
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProfileBottomSheetViewHolder {
        return ProfileBottomSheetViewHolder(
            ItemProfileBottomSheetBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ProfileBottomSheetViewHolder, position: Int) {
        holder.binding.apply {
            root.setOnClickListener {
                profileBottomSheetItemClickListener.onBottomSheetItemClicked()
            }
            tvDate.text = list[position].documentCreatedDate
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

}

interface ProfileBottomSheetItemClickListener{
    fun onBottomSheetItemClicked()
}