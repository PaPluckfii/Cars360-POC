package com.sumeet.cars360.ui.admin.master_service_log.form_screens.part_1_customer_and_car_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.sumeet.cars360.R
import com.sumeet.cars360.data.local.room.model.CarModelEntity
import com.sumeet.cars360.data.remote.request_data.ServiceLogFormData
import com.sumeet.cars360.databinding.FragmentAdminNewCarDetailsBinding
import com.sumeet.cars360.ui.admin.AdminViewModel
import com.sumeet.cars360.ui.admin.master_service_log.ServiceLogMasterViewModel
import com.sumeet.cars360.ui.onboarding.customer.car_select.CarModelSelectAdapter
import com.sumeet.cars360.util.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ServiceLogMasterNewCarDetailsFragment : Fragment(),
    CarModelSelectAdapter.CarModelSelectListener {

    private lateinit var binding: FragmentAdminNewCarDetailsBinding
    private val adminViewModel: AdminViewModel by activityViewModels()
    private val serviceLogMasterViewModel: ServiceLogMasterViewModel by activityViewModels()
    private val args: ServiceLogMasterNewCarDetailsFragmentArgs by navArgs()

    private var brandId: String = ""
    private var modelId: String = ""
    private var insuranceExpiryData = ""

    private var isDatePickerVisible = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAdminNewCarDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        brandId = args.brandId
        populateModelData()
        handleListeners()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (isDatePickerVisible) {
            isDatePickerVisible = false
            ViewVisibilityUtil.gone(binding.llDatePickerView)
            return true
        }
        findNavController().popBackStack()
        return true
    }

    private fun handleListeners() {
        binding.apply {
            btnSelectDOIE.setOnClickListener {
                if (ButtonClickHandler.buttonClicked()) {
                    btnSelectDOIE.background = ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.button_transparent_gradient
                    )
                    isDatePickerVisible = true
                    ViewVisibilityUtil.visible(llDatePickerView)

                    if (insuranceExpiryData == "")
//                        dobDatePicker
                    else {
                        val dates = insuranceExpiryData.split("-")
                        dobDatePicker.updateDate(
                            dates[0].toInt(),
                            dates[1].toInt(),
                            dates[2].toInt()
                        )
                    }

                    btnSetDate.setOnClickListener {
                        val year = dobDatePicker.year
                        val month = dobDatePicker.month
                        val date = dobDatePicker.dayOfMonth
                        insuranceExpiryData = "$year-$month-$date"
                        btnSelectDOIE.text = insuranceExpiryData
                        isDatePickerVisible = false
                        ViewVisibilityUtil.gone(llDatePickerView)

                    }
                    llDatePickerView.setOnClickListener {
                        isDatePickerVisible = false
                        ViewVisibilityUtil.gone(llDatePickerView)
                    }
                }
            }

            binding.fabAdd.setOnClickListener {
                if (checkDataValidity()) {
                    hideVirtualKeyBoard(requireActivity(), requireContext())
                    serviceLogMasterViewModel.insertNewCarIntoDatabase(
                        userId = args.userId,
                        modelId = modelId,
                        brandId = brandId,
                        vehicleNo = binding.etLicencePlate.text.toString(),
                        bodyColor = binding.etBodyColor.text.toString(),
                        fuelType = "",  //TODO
                        insuranceCompany = binding.etInsuranceCompany.text.toString(),
                        insuranceExpiryDate = insuranceExpiryData
                    )
                    serviceLogMasterViewModel.carInsertOperation.observe(viewLifecycleOwner) {
                        when (it) {
                            is FormDataResource.Loading -> {
                                ViewVisibilityUtil.visibilityExchanger(binding.progressBar,binding.nestedScrollView)
                            }
                            is FormDataResource.Error -> {
                                ViewVisibilityUtil.visibilityExchanger(binding.nestedScrollView,binding.progressBar)
                                Toast.makeText(
                                    context,
                                    "Oops! car could not be uploaded: ${it.message}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            is FormDataResource.Success -> {
                                if (it.data != null)
                                    navigate(
                                        ServiceLogMasterNewCarDetailsFragmentDirections
                                            .actionServiceLogMasterNewCarDetailsBottomSheetToServiceLogMasterPicturesFragment(
                                                ServiceLogFormData(carId = it.data)
                                            )
                                    )
                                else {
                                    ViewVisibilityUtil.visibilityExchanger(binding.nestedScrollView,binding.progressBar)
                                    Toast.makeText(
                                        context,
                                        "Oops! car could not be uploaded please try again",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun checkDataValidity(): Boolean {
        var validity = true
        if (binding.etLicencePlate.text.isNullOrEmpty()) {
            binding.tilLicencePlate.error = "Licence Plate Cannot Be Empty"
            validity = false
        }
        if (binding.etBodyColor.text.isNullOrEmpty()) {
            binding.tilBodyColor.error = "Licence Plate Cannot Be Empty"
            validity = false
        }
        if (modelId == "") {
            Toast.makeText(context, "Please Select A Car Model", Toast.LENGTH_SHORT).show()
            validity = false
        }
        return validity
    }

    private fun populateModelData() {
        binding.tvResultForModel.text = "Showing Results For ${args.brandName}"
        adminViewModel.getCarModelByBrandId(args.userId).observe(viewLifecycleOwner) { models ->
            val modelAdapterBrand = CarModelSelectAdapter(
                models as ArrayList<CarModelEntity>,
                this@ServiceLogMasterNewCarDetailsFragment
            )
            modelAdapterBrand.highlightSelection(true)
            binding.carModelRecyclerView.adapter = modelAdapterBrand
        }
    }

    override fun onCarItemSelected(modelEntity: CarModelEntity) {
        modelId = modelEntity.modelId
    }

}