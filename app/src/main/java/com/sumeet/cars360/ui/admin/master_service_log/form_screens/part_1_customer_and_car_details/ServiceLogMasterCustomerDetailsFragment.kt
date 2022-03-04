package com.sumeet.cars360.ui.admin.master_service_log.form_screens.part_1_customer_and_car_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.sumeet.cars360.R
import com.sumeet.cars360.data.local.room.model.CustomerEntity
import com.sumeet.cars360.databinding.FragmentServiceLogMasterCustomerDetailsBinding
import com.sumeet.cars360.ui.admin.master_service_log.ServiceLogMasterViewModel
import com.sumeet.cars360.ui.admin.util.AllCustomerRecyclerAdapter
import com.sumeet.cars360.ui.admin.util.CustomerEntityClickListener
import com.sumeet.cars360.util.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ServiceLogMasterCustomerDetailsFragment : Fragment(), CustomerEntityClickListener {

    private lateinit var binding: FragmentServiceLogMasterCustomerDetailsBinding
    private val viewModel: ServiceLogMasterViewModel by activityViewModels()

    private lateinit var customerRecyclerAdapter: AllCustomerRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentServiceLogMasterCustomerDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ViewVisibilityUtil.visible(binding.llProgress)
        setUpRecyclerView()
        handleListeners()
    }

    private fun setUpRecyclerView() {
        viewModel.customers.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Resource.Loading -> {}
                is Resource.Error -> {
                    //TODO show error message and implement swipe to refresh or other notification method
                }
                is Resource.Success -> {
                    ViewVisibilityUtil.gone(binding.llProgress)
                }
            }
            result.data?.let { setUpRecyclerAdapter(it) }
        }

    }

    private fun setUpRecyclerAdapter(list: List<CustomerEntity>) {
        customerRecyclerAdapter = AllCustomerRecyclerAdapter(
            list as ArrayList<CustomerEntity>,
            this@ServiceLogMasterCustomerDetailsFragment
        )
        binding.customersRecyclerView.adapter = customerRecyclerAdapter

        binding.etSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                customerRecyclerAdapter.getFilter().filter(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                customerRecyclerAdapter.getFilter().filter(newText)
                return true
            }

        })
    }

    private fun handleListeners() {
//        binding.apply {
//            etSearch.addTextChangedListener(object : TextWatcher {
//                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//                    tilSearch.error = null
//                    if (p0?.length == 10) {
//                        hideVirtualKeyBoard(requireActivity(), requireContext())
//                        binding.btnSearch.requestFocus()
//                    }
//                }
//
//                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
//                override fun afterTextChanged(p0: Editable?) {}
//            })
//
//            btnSearch.setOnClickListener {
//                if (ButtonClickHandler.buttonClicked() && checkDataValidity()) {
//
//                    viewModel.searchUserByMobileNumber("+91${binding.etSearch.text}")
//
//                    viewModel.customerData.observe(viewLifecycleOwner) {
//                        when (it) {
//                            is FormDataResource.Loading -> {
//                                ViewVisibilityUtil.visibilityExchanger(
//                                    binding.progressBar,
//                                    binding.llCustomerData
//                                )
//                            }
//                            is FormDataResource.Error -> {
//                                ViewVisibilityUtil.gone(binding.progressBar)
//                                Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
//                            }
//                            is FormDataResource.Success -> {
//                                val user = it.data?.userResponse?.get(0)
//                                binding.apply {
//                                    tvName.text = user?.name
//                                    tvMobile.text = user?.mobile
//                                    tvAddressLine.text = user?.address
//                                    tvCity.text = user?.city
//                                    tvState.text = user?.state
//                                    tvPostalCode.text = user?.postalCode
//                                    tvEmailId.text = user?.email
//                                }
//
//                                val carNamesArray = arrayListOf<String>()
//                                viewModel.customerCarData?.carDetailsResponse?.forEach { carDetails ->
//                                    carNamesArray.add(carDetails.modelName.toString())
//                                }
//
//                                binding.carsSpinner.adapter = ArrayAdapter(
//                                    requireContext(),
//                                    R.layout.item_services_spinner,
//                                    carNamesArray
//                                )
//                                ViewVisibilityUtil.visibilityExchanger(
//                                    binding.llCustomerData,
//                                    binding.progressBar
//                                )
//                                isCustomerSelected = true
//                            }
//                        }
//                    }
//                }
//            }
//            btnNext.setOnClickListener {
//                if (ButtonClickHandler.buttonClicked()) {
//                    if (isCustomerSelected) {
//                        val selectedCar = binding.carsSpinner.selectedItemPosition
//                        ServiceLogCreationHelper.serviceLogDTO.carId =
//                            viewModel.customerCarData?.carDetailsResponse?.get(selectedCar)?.carId.toString()
//                        navigate(ServiceLogMasterCustomerDetailsFragmentDirections.actionServiceLogMasterCustomerDetailsFragmentToServiceLogMasterPicturesFragment())
//                    } else
//                        Toast.makeText(
//                            context,
//                            "Please select a customer to continue",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                }
//            }
//        }

        binding.fabAdd.setOnClickListener {
            if (ButtonClickHandler.buttonClicked())
                showDialogBox(
                    requireActivity(),
                    "Add New Customer",
                    "Do you want to add a new customer?",
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.ic_baseline_person_add_24
                    ),
                    "YES",
                    "CANCEL"
                ) {
                    navigate(ServiceLogMasterCustomerDetailsFragmentDirections.actionServiceLogMasterCustomerDetailsFragmentToServiceLogMasterCustomerMasterFragment())
                }
        }

    }

//    private fun checkDataValidity(): Boolean {
//        if (binding.etSearch.text?.length == 10)
//            return true
//        binding.tilSearch.error = "Enter A Valid Mobile Number"
//        return false
//    }

    override fun onCustomerEntityItemClicked(customerEntity: CustomerEntity) {
        navigate(
            ServiceLogMasterCustomerDetailsFragmentDirections
                .actionServiceLogMasterCustomerDetailsFragmentToServiceLogMasterCarDetailsFragment(
                    customerEntity.userId,
                    customerEntity.name.toString()
                )
        )
    }
}