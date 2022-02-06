package com.sumeet.cars360.ui.admin.fragments.home_navs

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.sumeet.cars360.data.remote.model.car_entities.CarBrandResponse
import com.sumeet.cars360.data.remote.model.car_entities.CarModelResponse
import com.sumeet.cars360.databinding.FragmentCarCompanyMasterBinding
import com.sumeet.cars360.ui.admin.AdminViewModel
import com.sumeet.cars360.ui.onboarding.fragments.new_customer.CarSelectAdapter
import com.sumeet.cars360.ui.staff.StaffViewModel
import com.sumeet.cars360.util.ButtonClickHandler
import com.sumeet.cars360.util.Resource
import com.sumeet.cars360.util.ViewVisibilityUtil
import com.sumeet.cars360.util.navigate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CarCompanyMasterFragment : Fragment(),CarSelectAdapter.CarSelectListener {

    private lateinit var binding: FragmentCarCompanyMasterBinding
    private val viewModel: AdminViewModel by activityViewModels()

    private lateinit var recyclerAdapter: CarSelectAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCarCompanyMasterBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerAdapter()
        getDataAndObserve()
        handleListeners()
    }

    private fun handleListeners() {
        binding.apply {
            btnAddCar.setOnClickListener {
                if(ButtonClickHandler.buttonClicked())
                    navigate(CarCompanyMasterFragmentDirections.actionCarCompanyMasterFragment2ToAddCarEntitiesFragment())
            }
        }
    }

    private fun getDataAndObserve() {
        viewModel.getAllCarEntities()

        viewModel.carEntities.observe(viewLifecycleOwner,{
            when(it){
                is Resource.Loading -> {}
                is Resource.Error -> {}
                is Resource.Success -> {
                    it.data?.carBrandResponse?.let { it1 -> recyclerAdapter.setCarBrandList(it1) }
                    ViewVisibilityUtil.visibilityExchanger(binding.carCompanyRecyclerView,binding.shimmerFrameLayout)
                }
            }
        })
    }

    private fun setUpRecyclerAdapter() {
        recyclerAdapter = CarSelectAdapter(this)
        binding.carCompanyRecyclerView.adapter = recyclerAdapter
    }

    override fun onCarItemSelected(carBrand: CarBrandResponse, carModel: CarModelResponse?) {
        if(carModel == null) {
            viewModel.currentBrandId = carBrand.brandId.toString()
            carBrand.carModelResponses?.let { recyclerAdapter.setCarModelList(it) }
        }
        else{
            //viewModel.modelId = carModel.modelId.toString()
        }
    }

}