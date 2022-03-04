package com.sumeet.cars360.ui.onboarding.customer.new_customer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.sumeet.cars360.R
import com.sumeet.cars360.databinding.FragmentNewCustomerAdditionalDetailsBinding
import com.sumeet.cars360.ui.onboarding.customer.NewCustomerViewModel
import com.sumeet.cars360.util.ButtonClickHandler
import com.sumeet.cars360.util.CityStateManagementTool
import com.sumeet.cars360.util.ViewVisibilityUtil
import dagger.hilt.android.AndroidEntryPoint
import gr.escsoft.michaelprimez.searchablespinner.interfaces.OnItemSelectedListener

@AndroidEntryPoint
class NewCustomerAdditionalDetailsFragment : Fragment() {

    private lateinit var binding: FragmentNewCustomerAdditionalDetailsBinding
    private val viewModel: NewCustomerViewModel by activityViewModels()

    private lateinit var listOfCityState: List<String>
    private lateinit var listOfCities: List<String>
    private var isDatePickerVisible = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewCustomerAdditionalDetailsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getCityStateData()
        handleListeners()
    }

    private fun getCityStateData() {
        listOfCityState = CityStateManagementTool.getParsedStateData(
            resources.assets.open("in_city_state_data.json")
        )
        binding.stateSpinner.setAdapter(
            ArrayAdapter(
                requireContext(),
                R.layout.item_services_spinner,
                listOfCityState
            )
        )
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(isDatePickerVisible){
            isDatePickerVisible = false
            ViewVisibilityUtil.gone(binding.llDatePickerView)
            return true
        }
        findNavController().popBackStack()
        return true
    }

    private fun handleListeners() {
        binding.apply {

            stateSpinner.setOnItemSelectedListener(object : OnItemSelectedListener{
                override fun onItemSelected(view: View?, position: Int, id: Long) {
                    listOfCities = CityStateManagementTool.getParsedCityData(
                        resources.assets.open("in_city_state_data.json"),
                        listOfCityState[position]
                    )

                    binding.citySpinner.setAdapter(
                        ArrayAdapter(
                            requireContext(),
                            R.layout.item_services_spinner,
                            listOfCities
                        )
                    )

                    ViewVisibilityUtil.visible(citySpinner)
                }

                override fun onNothingSelected() {
                    ViewVisibilityUtil.gone(citySpinner)
                }

            })

            dobDatePicker.maxDate = System.currentTimeMillis() - 1000

            btnSelectDOB.setOnClickListener {
                if(ButtonClickHandler.buttonClicked()) {
                    binding.btnSelectDOB.background = ContextCompat.getDrawable(requireContext(),R.drawable.button_transparent_gradient)
                    isDatePickerVisible = true
                    ViewVisibilityUtil.visible(llDatePickerView)

                    if(viewModel.dob == "")
                        dobDatePicker.updateDate(1990,1,1)
                    else {
                        val dates = viewModel.dob.split("-")
                        dobDatePicker.updateDate(dates[0].toInt(), dates[1].toInt(),dates[2].toInt())
                    }

                    btnSetDate.setOnClickListener {
                        val year = dobDatePicker.year
                        val month = dobDatePicker.month
                        val date = dobDatePicker.dayOfMonth
                        viewModel.dob = "$year-$month-$date"
                        btnSelectDOB.text = viewModel.dob
                        isDatePickerVisible = false
                        ViewVisibilityUtil.gone(llDatePickerView)

                    }
                    llDatePickerView.setOnClickListener {
                        isDatePickerVisible = false
                        ViewVisibilityUtil.gone(llDatePickerView)
                    }
                }
            }

            btnSelectDOM.setOnClickListener {
                if(ButtonClickHandler.buttonClicked()) {
                    isDatePickerVisible = true
                    ViewVisibilityUtil.visible(llDatePickerView)

                    if(viewModel.dom == "")
                        dobDatePicker.updateDate(2010,1,1)
                    else {
                        val dates = viewModel.dom.split("-")
                        dobDatePicker.updateDate(dates[0].toInt(), dates[1].toInt(),dates[2].toInt())
                    }

                    btnSetDate.setOnClickListener {
                        val year = dobDatePicker.year
                        val month = dobDatePicker.month
                        val date = dobDatePicker.dayOfMonth
                        viewModel.dom = "$year-$month-$date"
                        btnSelectDOM.text = viewModel.dom
                        isDatePickerVisible = false
                        ViewVisibilityUtil.gone(llDatePickerView)

                    }
                    llDatePickerView.setOnClickListener {
                        isDatePickerVisible = false
                        ViewVisibilityUtil.gone(llDatePickerView)
                    }
                }
            }

            btnSignUp.setOnClickListener {

                if(ButtonClickHandler.buttonClicked() && checkDataValidity()){

                    viewModel.apply {
                        address = etAddress1.text.toString()
                        city = citySpinner.selectedItem.toString()
                        state = stateSpinner.selectedItem.toString()
                        pinCode = etAddressPinCode.text.toString()
                        gstIn = etGstIn.text.toString()
                    }

                }

            }

        }
    }

    private fun checkDataValidity(): Boolean {
        var validity = true
        if (binding.etAddress1.text.isNullOrEmpty()) {
            binding.tilAddress1.error = "Address Field Cannot be Empty"
            validity = false
        }
        if (binding.citySpinner.isSelected) {
            binding.citySpinner.background = ContextCompat.getDrawable(requireContext(),R.color.primaryRed)
            validity = false
        }
        if (binding.stateSpinner.isSelected) {
            binding.stateSpinner.background = ContextCompat.getDrawable(requireContext(),R.color.primaryRed)
            validity = false
        }
        if (binding.etAddressPinCode.text.toString().length != 6) {
            binding.tilAddressPinCode.error = "Email Cannot be Empty"
            validity = false
        }
        if(viewModel.dob == ""){
            binding.btnSelectDOB.background = ContextCompat.getDrawable(requireContext(),R.color.secondaryRed)
            validity = false
        }
        if(viewModel.dom == ""){
            binding.btnSelectDOM.background = ContextCompat.getDrawable(requireContext(),R.color.secondaryRed)
            validity = false
        }
        return validity
    }

}