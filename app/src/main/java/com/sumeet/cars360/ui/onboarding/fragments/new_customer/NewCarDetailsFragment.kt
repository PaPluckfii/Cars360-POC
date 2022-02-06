package com.sumeet.cars360.ui.onboarding.fragments.new_customer

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.sumeet.cars360.R
import com.sumeet.cars360.data.local.preferences.ReadPrefs
import com.sumeet.cars360.data.remote.model.car_entities.CarBrandResponse
import com.sumeet.cars360.data.remote.model.car_entities.CarModelResponse
import com.sumeet.cars360.databinding.CarDetailsBottomSheetBinding
import com.sumeet.cars360.databinding.CustomerRequestServiceBottomSheetLayoutBinding
import com.sumeet.cars360.databinding.CustomerServicesBottomSheetLayoutBinding
import com.sumeet.cars360.databinding.FragmentNewCarDetailsBinding
import com.sumeet.cars360.ui.customer.CustomerActivity
import com.sumeet.cars360.ui.staff.StaffActivity
import com.sumeet.cars360.util.ButtonClickHandler
import com.sumeet.cars360.util.Resource
import com.sumeet.cars360.util.ViewVisibilityUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewCarDetailsFragment : Fragment(), CarSelectAdapter.CarSelectListener {

    private lateinit var binding: FragmentNewCarDetailsBinding
    private val viewModel: NewCustomerViewModel by activityViewModels()

    private lateinit var recyclerAdapter: CarSelectAdapter
    private var isBrandSelected = false
    private var isBottomSheetVisible = false

    private lateinit var mCarsBottomSheetBehavior: BottomSheetBehavior<CoordinatorLayout>
    private lateinit var bottomSheetLayoutBinding: CarDetailsBottomSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewCarDetailsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleBottomSheet()
        setUpRecyclerAdapter()
        setUpCarBrand()
    }

    private fun handleBottomSheet() {

        bottomSheetLayoutBinding = binding.carDetailsBottomSheet
        mCarsBottomSheetBehavior = BottomSheetBehavior.from(binding.carDetailsSheet)
        mCarsBottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN

        setUpOptionsListListeners()

    }

    private fun setUpOptionsListListeners() {

        mCarsBottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        mCarsBottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
                    }
                    BottomSheetBehavior.STATE_EXPANDED -> {
                        binding.vCustomerTransparentBg.visibility = View.VISIBLE
                    }
                    BottomSheetBehavior.STATE_HALF_EXPANDED -> {
                    }
                    BottomSheetBehavior.STATE_HIDDEN -> {
                        binding.vCustomerTransparentBg.visibility = View.GONE
                    }
                    BottomSheetBehavior.STATE_DRAGGING -> {
                    }
                    BottomSheetBehavior.STATE_SETTLING -> {
                        binding.vCustomerTransparentBg.visibility = View.VISIBLE
                    }
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {

            }
        })

        binding.apply {
            vCustomerTransparentBg.setOnClickListener {
                if (isBottomSheetVisible)
                    hideBottomSheet()
            }
        }

        bottomSheetLayoutBinding.apply {
            llPetrol.setOnClickListener {
                viewModel.fuelType = "Petrol"
                ViewVisibilityUtil.visibilityExchanger(llAdditionalDetails,llFuelType)
            }
            llDiesel.setOnClickListener {
                viewModel.fuelType = "Diesel"
                ViewVisibilityUtil.visibilityExchanger(llAdditionalDetails,llFuelType)
            }
            llElectric.setOnClickListener {
                viewModel.fuelType = "Electric"
                ViewVisibilityUtil.visibilityExchanger(llAdditionalDetails,llFuelType)
            }
            llCNG.setOnClickListener {
                viewModel.fuelType = "CNG"
                ViewVisibilityUtil.visibilityExchanger(llAdditionalDetails,llFuelType)
            }

            bodyColorSpinner.adapter = ArrayAdapter(
                requireContext(),
                R.layout.item_services_spinner,
                resources.getStringArray(R.array.CarBodyColor)
            )

            btnSelectDate.setOnClickListener {
                //TODO
            }

            //TODO check body color
            btnAddCar.setOnClickListener {
                if(ButtonClickHandler.buttonClicked() && checkDataValidity()){
                    viewModel.vehicleNo = etVehicleNo.text.toString()
                    viewModel.insuranceCompany = etInsuranceCompany.text.toString()
                    viewModel.bodyColor = bodyColorSpinner.selectedItem.toString()

                    viewModel.insertNewCar(ReadPrefs(requireContext()).readUserId().toString())

                    viewModel.insertOperation.observe(viewLifecycleOwner,{
                        when(it){
                            is Resource.Loading -> {
                                hideBottomSheet()
                                ViewVisibilityUtil.visibilityExchanger(binding.progressBar,binding.carsRecyclerView)
                            }
                            is Resource.Error -> {
                                ViewVisibilityUtil.visibilityExchanger(binding.carsRecyclerView,binding.progressBar)
                            }
                            is Resource.Success -> {
                                startActivity(
                                    Intent(activity, CustomerActivity::class.java)
                                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                ).also { activity?.finish() }
                            }
                        }
                    })

                }
            }

        }

    }

    private fun checkDataValidity(): Boolean {
        var validity = true
        if (bottomSheetLayoutBinding.etVehicleNo.text.isNullOrEmpty()) {
            bottomSheetLayoutBinding.tilVehicleNo.error = "Vehicle Number Cannot be Empty"
            validity = false
        }
        if (bottomSheetLayoutBinding.etInsuranceCompany.text.isNullOrEmpty()) {
            bottomSheetLayoutBinding.tilInsuranceCompany.error = "Insurance Company Cannot be Empty"
            validity = false
        }
        return validity
    }

    private fun setUpRecyclerAdapter() {
        recyclerAdapter = CarSelectAdapter(this)
        binding.carsRecyclerView.adapter = recyclerAdapter
    }

    private fun setUpCarBrand() {
        viewModel.getAllCarEntities()
        viewModel.carEntities.observe(viewLifecycleOwner,{
            when(it){
                is Resource.Loading -> {}
                is Resource.Error -> {
                    ViewVisibilityUtil.gone(binding.shimmerFrameLayout)
                    Toast.makeText(context,"Oops! ${it.message}",Toast.LENGTH_LONG).show()
                }
                is Resource.Success -> {
                    it.data?.carBrandResponse?.let { list -> recyclerAdapter.setCarBrandList(list) }
                    ViewVisibilityUtil.visibilityExchanger(binding.carsRecyclerView,binding.shimmerFrameLayout)
                }
            }
        })
    }

    private fun showBottomSheet() {
        mCarsBottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        binding.vCustomerTransparentBg.visibility = View.VISIBLE
        isBottomSheetVisible = true
    }

    private fun hideBottomSheet() {
        mCarsBottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        binding.vCustomerTransparentBg.visibility = View.GONE
        isBottomSheetVisible = false
    }

    override fun onCarItemSelected(carBrand: CarBrandResponse, carModel: CarModelResponse?) {
        if(carModel == null) {
            viewModel.brandId = carBrand.brandId.toString()
            carBrand.carModelResponses?.let { recyclerAdapter.setCarModelList(it) }
        }
        else{
            viewModel.modelId = carModel.modelId.toString()
            showBottomSheet()
        }
    }

}