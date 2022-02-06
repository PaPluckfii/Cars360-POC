package com.sumeet.cars360.ui.admin.fragments.home_navs

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.sumeet.cars360.R
import com.sumeet.cars360.databinding.FragmentAddCarEntitiesBinding
import com.sumeet.cars360.ui.admin.AdminViewModel
import com.sumeet.cars360.util.ButtonClickHandler
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddCarEntitiesFragment : Fragment() {

    private lateinit var binding: FragmentAddCarEntitiesBinding
    private val viewModel: AdminViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddCarEntitiesBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        handleListeners()
        if(viewModel.currentBrandId == ""){

        }

    }

    private fun handleListeners() {
        binding.apply {
            btnUpload.setOnClickListener {
                if(ButtonClickHandler.buttonClicked()){

                }
            }
        }
    }

}