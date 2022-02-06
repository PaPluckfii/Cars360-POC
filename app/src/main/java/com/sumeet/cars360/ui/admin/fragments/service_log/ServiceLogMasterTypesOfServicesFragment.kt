package com.sumeet.cars360.ui.admin.fragments.service_log

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.sumeet.cars360.R
import com.sumeet.cars360.databinding.FragmentServiceLogMasterTypesOfServicesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ServiceLogMasterTypesOfServicesFragment : Fragment() {

    private lateinit var binding: FragmentServiceLogMasterTypesOfServicesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentServiceLogMasterTypesOfServicesBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnNext.setOnClickListener {
            findNavController().navigate(R.id.action_serviceLogMasterTypesOfServicesFragment_to_serviceLogMasterRequestedRepairsFragment)
        }
    }
}