package com.sumeet.cars360.ui.admin.fragments.service_log

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.sumeet.cars360.R
import com.sumeet.cars360.databinding.DialogRequestedServiceBinding
import com.sumeet.cars360.databinding.FragmentServiceLogMasterRequestedRepairsBinding
import com.sumeet.cars360.databinding.ItemRequestedServiceBinding
import com.sumeet.cars360.ui.admin.fragments.home_navs.ServiceLogMasterFragment
import com.sumeet.cars360.ui.admin.fragments.home_navs.ServiceLogMasterFragmentDirections
import com.sumeet.cars360.util.ButtonClickHandler
import com.sumeet.cars360.util.Resource
import com.sumeet.cars360.util.ViewVisibilityUtil
import com.sumeet.cars360.util.navigate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ServiceLogMasterRequestedRepairsFragment : Fragment() {

    private lateinit var binding: FragmentServiceLogMasterRequestedRepairsBinding
    private val viewModel: ServiceLogMasterViewModel by activityViewModels()

    private lateinit var recyclerAdapter: RequestedRepairAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentServiceLogMasterRequestedRepairsBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        handleListeners()
    }

    private fun setUpRecyclerView() {
        recyclerAdapter = RequestedRepairAdapter()
        binding.requestedRepairRecyclerView.adapter = recyclerAdapter
    }

    private fun handleListeners() {
        binding.apply {
            btnAddMore.setOnClickListener {
                if (ButtonClickHandler.buttonClicked()) {
                    showDialog()
                }
            }
            btnFinalize.setOnClickListener {
                if (ButtonClickHandler.buttonClicked()) {
                    if(recyclerAdapter.listOfRequestedRepairs.isEmpty())
                        Toast.makeText(context,"Please add one service",Toast.LENGTH_SHORT).show()
                    else{

                        ViewVisibilityUtil.gone(binding.requestedRepairRecyclerView)
                        ViewVisibilityUtil.gone(binding.llButtons)
                        ViewVisibilityUtil.visible(binding.progressBar)

                        viewModel.additionalDetails = createData()
                        viewModel.estimatedAmount = getTotalEstimate()

                        viewModel.insertNewServiceLog()

                        viewModel.insertOperation.observe(viewLifecycleOwner, {
                            when(it){
                                is Resource.Loading -> {}
                                is Resource.Error -> {
                                    Toast.makeText(context,"Oops ${it.message}",Toast.LENGTH_SHORT).show()
                                }
                                is Resource.Success -> {
                                    Toast.makeText(context,"Uploaded Service Log with ID : ${it.data}",Toast.LENGTH_SHORT).show()
                                }
                            }
                        })

                    }

                }
            }
        }
    }

    private fun getTotalEstimate(): String {
        var total = 0.0
        for (i in recyclerAdapter.listOfRequestedRepairs){
            total += i.estimatedCost
        }
        return total.toString()
    }

    private fun createData(): String {
        val str = StringBuffer()
        for (i in recyclerAdapter.listOfRequestedRepairs){
            str.append(
                i.requestedRepair + "-" + i.serviceType + "-" + i.estimatedCost + ","
            )
        }
        return str.toString()
    }

    private fun showDialog() {
        val bind: DialogRequestedServiceBinding = DialogRequestedServiceBinding.inflate(
            LayoutInflater.from(context)
        )
        val customDialog = Dialog(requireActivity())
        customDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        customDialog.setContentView(bind.root)
        customDialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        bind.apply {
            btnAddRequestedRepair.setOnClickListener {
                if(ButtonClickHandler.buttonClicked() && checkDataValidity(bind)){
                    recyclerAdapter.listOfRequestedRepairs.add(
                        RequestedRepair(
                            bind.etRemarks.text.toString(),
                            bind.typesOfServicesSpinner.selectedItem.toString(),
                            bind.etEstimate.text.toString().toDouble()
                        )
                    )
                    recyclerAdapter.notifyDataSetChanged()
                    customDialog.dismiss()
                }
            }
        }

        customDialog.show()
    }

    private fun checkDataValidity(bind: DialogRequestedServiceBinding): Boolean {
        var validity = true
        if(bind.etRemarks.text.isNullOrEmpty())
            validity = false
        if(bind.etEstimate.text.isNullOrEmpty())
            validity = false
        return validity
    }

}

class RequestedRepairAdapter() :
    RecyclerView.Adapter<RequestedRepairAdapter.RequestedRepairViewHolder>() {

    val listOfRequestedRepairs = mutableListOf<RequestedRepair>()

    inner class RequestedRepairViewHolder(val binding: ItemRequestedServiceBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RequestedRepairViewHolder {
        return RequestedRepairViewHolder(
            ItemRequestedServiceBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RequestedRepairViewHolder, position: Int) {
        holder.binding.apply {
            requestedRepair.text = "Requested Repair: ${listOfRequestedRepairs[position].requestedRepair}"
            type.text =  "Service Type: ${listOfRequestedRepairs[position].serviceType}"
            estimate.text =  "Estimated Value: ${listOfRequestedRepairs[position].estimatedCost}"
            btnDelete.setOnClickListener {
                listOfRequestedRepairs.removeAt(position)
                notifyDataSetChanged()
            }
        }
    }

    override fun getItemCount(): Int {
        return listOfRequestedRepairs.size
    }

}

data class RequestedRepair(
    var requestedRepair: String = "",
    var serviceType: String = "",
    var estimatedCost: Double = 0.0
)