package com.sumeet.cars360.ui.admin.master_service_log.form_screens.part_4_service_types

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.sumeet.cars360.databinding.FragmentServiceLogMasterTypesOfServicesBinding
import com.sumeet.cars360.ui.admin.master_service_log.ServiceLogMasterViewModel
import com.sumeet.cars360.ui.admin.util.ServiceLogCreationHelper
import com.sumeet.cars360.util.ButtonClickHandler
import com.sumeet.cars360.util.navigate
import dagger.hilt.android.AndroidEntryPoint
import java.lang.StringBuilder

@AndroidEntryPoint
class ServiceLogMasterTypesOfServicesFragment : Fragment() {

    private lateinit var binding: FragmentServiceLogMasterTypesOfServicesBinding
    private val viewModel: ServiceLogMasterViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentServiceLogMasterTypesOfServicesBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnNext.setOnClickListener {
            if(ButtonClickHandler.buttonClicked() && checkDataValidity()){
                createTypesOfServices()
                navigate(ServiceLogMasterTypesOfServicesFragmentDirections.actionServiceLogMasterTypesOfServicesFragmentToServiceLogMasterRequestedRepairsFragment())
            }
        }
    }

    private fun createTypesOfServices(){
        val str = StringBuilder()
        if (binding.rbYesRepeat.isChecked) str.append("Repeat Job,")
        if (binding.rbYesRoadTest.isChecked) str.append("Time & Cost Estimate Explained for all Listed jobs,")
        if (binding.rbYesTimeNCost.isChecked) str.append("Road Test Done Alongwith Customer")
        if (binding.rbYesCNG.isChecked) str.append("CNG/LPG Fitted")
        ServiceLogCreationHelper.serviceLogDTO.serviceTypes = str.toString()
    }

    private fun checkDataValidity(): Boolean {
        var validity = true
        if (!binding.rbYesRepeat.isChecked && !binding.rbNoRepeat.isChecked)
            validity = false
        if (!binding.rbYesRoadTest.isChecked && !binding.rbNoRoadTest.isChecked)
            validity = false
        if (!binding.rbYesTimeNCost.isChecked && !binding.rbNoTimeNCost.isChecked)
            validity = false
        if (!binding.rbYesCNG.isChecked && !binding.rbNoCNG.isChecked)
            validity = false
        return validity
    }
}