package com.sumeet.cars360.ui.admin.fragments.service_log

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.sumeet.cars360.databinding.FragmentServiceLogMasterAccessoriesBinding
import com.sumeet.cars360.ui.admin.util.ServiceLogCreationHelper
import com.sumeet.cars360.util.AccessoriesItemAdapter
import com.sumeet.cars360.util.ButtonClickHandler
import com.sumeet.cars360.util.CheckBoxClickListener
import com.sumeet.cars360.util.navigate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ServiceLogMasterAccessoriesFragment : Fragment(), CheckBoxClickListener {

    private lateinit var binding: FragmentServiceLogMasterAccessoriesBinding
    private val viewModel: ServiceLogMasterViewModel by activityViewModels()

    private lateinit var accessoriesAdapter: AccessoriesItemAdapter
    private val listOfAccessories = mutableListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentServiceLogMasterAccessoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        generateAccessoriesData()
        handleListeners()
    }

    private fun handleListeners() {
        binding.btnNext.setOnClickListener {
            if (ButtonClickHandler.buttonClicked()) {
                ServiceLogCreationHelper.serviceLogDTO.accessories = getAccessoriesData()
                navigate(
                    ServiceLogMasterAccessoriesFragmentDirections
                        .actionServiceLogMasterAccessoriesFragmentToServiceLogMasterTypesOfServicesFragment()
                )
            }
        }
    }

    private fun getAccessoriesData(): String {
        val str = StringBuilder()
        for (accessory in listOfAccessories) {
            str.append(accessory)
        }
        return str.toString()
    }

    private fun generateAccessoriesData() {
        val list = listOf<String>(
            "Owner Manual Warranty Book",
            "Caska/AVN/SD Card",
            "Pen Drive 1/Pen Drive 2",
            "Key Chain",
            "Clock",
            "Gear Lock",
            "Sunglasses",
            "Cigarette Lighter",
            "Mobile Charger",
            "Central Lock remote",
            "Stereo remote",
            "Cds",
            "Cds Magazine",
            "Divinite",
            "Perfume",
            "Fog Lamps",
            "Anetenne",
            "Jack/Rod",
            "Dicky Mat",
            "Body Cover",
            "Mud Flap No",
            "Cabin Mats",
            "Wheel Cover/Cap No.",
            "Battery No.",
            "Spare Wheel Make",
            "Type Make F/L",
            "Type Make F/R",
            "Type Make F/L",
            "Type Make F/R"
        )
        accessoriesAdapter = AccessoriesItemAdapter(list, this)
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        binding.accessoriesRecyclerView.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = accessoriesAdapter
        }
    }

    override fun onCheckBoxItemClicked(accessory: String, isChecked: Boolean) {
        if (isChecked)
            listOfAccessories.add(accessory)
        else
            listOfAccessories.remove(accessory)
    }

}