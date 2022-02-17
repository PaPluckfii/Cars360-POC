package com.sumeet.cars360.ui.admin.fragments.service_log

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.sumeet.cars360.R
import com.sumeet.cars360.databinding.FragmentServiceLogMasterCustomerDetailsBinding
import com.sumeet.cars360.ui.admin.util.ServiceLogCreationHelper
import com.sumeet.cars360.util.ButtonClickHandler
import com.sumeet.cars360.util.Resource
import com.sumeet.cars360.util.ViewVisibilityUtil
import com.sumeet.cars360.util.navigate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ServiceLogMasterCustomerDetailsFragment : Fragment() {

    private lateinit var binding: FragmentServiceLogMasterCustomerDetailsBinding
    private val viewModel: ServiceLogMasterViewModel by activityViewModels()

    private var isCustomerSelected = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentServiceLogMasterCustomerDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleListeners()
    }

    private fun handleListeners() {
        binding.apply {
            etSearch.addTextChangedListener(object : TextWatcher {
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    tilSearch.error = null
                    if (p0?.length == 10)
                        binding.btnSearch.requestFocus()
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun afterTextChanged(p0: Editable?) {}
            })

            btnSearch.setOnClickListener {
                if (ButtonClickHandler.buttonClicked() && checkDataValidity()) {
                    viewModel.searchUserByMobileNumber("+91${binding.etSearch.text}")

                    viewModel.customerData.observe(viewLifecycleOwner) {
                        when (it) {
                            is Resource.Loading -> {
                                ViewVisibilityUtil.visibilityExchanger(
                                    binding.progressBar,
                                    binding.llCustomerData
                                )
                            }
                            is Resource.Error -> {
                                ViewVisibilityUtil.visibilityExchanger(
                                    binding.llCustomerData,
                                    binding.progressBar
                                )
                                Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                            }
                            is Resource.Success -> {
                                val user = it.data?.userResponse?.get(0)
                                binding.apply {
                                    tvName.text = user?.name
                                    tvMobile.text = user?.mobile
                                    tvAddressLine.text = user?.address
                                    tvCity.text = user?.city
                                    tvState.text = user?.state
                                    tvPostalCode.text = user?.postalCode
                                    tvEmailId.text = user?.email
                                }

                                val carNamesArray = arrayListOf<String>()
                                viewModel.customerCarData?.carDetailsResponse?.forEach { carDetails ->
                                    carNamesArray.add(carDetails.modelName.toString())
                                }

                                binding.carsSpinner.adapter = ArrayAdapter(
                                    requireContext(),
                                    R.layout.item_services_spinner,
                                    carNamesArray
                                )
                                ViewVisibilityUtil.visibilityExchanger(
                                    binding.llCustomerData,
                                    binding.progressBar
                                )
                                isCustomerSelected = true
                            }
                        }
                    }
                }
            }
            btnNext.setOnClickListener {
                if(ButtonClickHandler.buttonClicked() && isCustomerSelected){
                    val selectedCar = binding.carsSpinner.selectedItemPosition
                    ServiceLogCreationHelper.serviceLogDTO.carId = viewModel.customerCarData?.carDetailsResponse?.get(selectedCar)?.carId.toString()
                    navigate(ServiceLogMasterCustomerDetailsFragmentDirections.actionServiceLogMasterCustomerDetailsFragmentToServiceLogMasterPicturesFragment())
                }
            }
        }
    }

    private fun checkDataValidity(): Boolean {
        if(binding.etSearch.text?.length == 10)
            return true
        binding.tilSearch.error = "Enter A Valid Mobile Number"
        return false
    }
}