package com.sumeet.cars360.ui.admin.fragments.home_navs

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sumeet.cars360.R
import com.sumeet.cars360.databinding.FragmentServiceAdvisorMasterBinding
import com.sumeet.cars360.util.ButtonClickHandler
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ServiceAdvisorMasterFragment : Fragment() {

    private lateinit var binding: FragmentServiceAdvisorMasterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentServiceAdvisorMasterBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleListeners()
    }

    private fun handleListeners() {
        binding.apply {
            btnSelectDOB.setOnClickListener {
                if(ButtonClickHandler.buttonClicked()) {
                    //TODO
                }
            }
            btnSelectDOM.setOnClickListener {
                if(ButtonClickHandler.buttonClicked()) {
                    //TODO
                }
            }
            btnSetDate.setOnClickListener {
                if(ButtonClickHandler.buttonClicked()) {
                    //TODO
                }
            }
            btnAddServiceAdvisor.setOnClickListener {
                if(ButtonClickHandler.buttonClicked()) {
                    //TODO
                }
            }
        }
    }

}