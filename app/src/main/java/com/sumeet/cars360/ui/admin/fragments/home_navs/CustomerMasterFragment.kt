package com.sumeet.cars360.ui.admin.fragments.home_navs

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.sumeet.cars360.R
import com.sumeet.cars360.databinding.FragmentCustomerMasterBinding
import com.sumeet.cars360.ui.admin.AdminViewModel
import com.sumeet.cars360.util.ButtonClickHandler
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CustomerMasterFragment : Fragment() {

    private lateinit var binding: FragmentCustomerMasterBinding
    private val viewModel: AdminViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCustomerMasterBinding.inflate(inflater,container,false)
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
            btnAddNewCustomer.setOnClickListener {
                if(ButtonClickHandler.buttonClicked()) {
                    if(checkDataValidity()){

                    }
                }
            }
        }
    }

    private fun checkDataValidity(): Boolean {
        //TODO
        return true
    }

}