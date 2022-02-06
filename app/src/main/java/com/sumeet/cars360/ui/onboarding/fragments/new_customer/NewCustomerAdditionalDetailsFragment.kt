package com.sumeet.cars360.ui.onboarding.fragments.new_customer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.sumeet.cars360.R
import com.sumeet.cars360.databinding.FragmentNewCustomerAdditionalDetailsBinding
import com.sumeet.cars360.util.ButtonClickHandler
import com.sumeet.cars360.util.ViewVisibilityUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewCustomerAdditionalDetailsFragment : Fragment() {

    private lateinit var binding: FragmentNewCustomerAdditionalDetailsBinding
    private val viewModel: NewCustomerViewModel by activityViewModels()

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
        handleListeners()
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
                        city = etAddressCity.text.toString()
                        state = etAddressState.text.toString()
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
        if (binding.etAddressState.text.isNullOrEmpty()) {
            binding.tilAddressState.error = "Email Cannot be Empty"
            validity = false
        }
        if (binding.etAddressCity.text.isNullOrEmpty()) {
            binding.tilAddressCity.error = "Email Cannot be Empty"
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
        return validity
    }

}