package com.sumeet.cars360.ui.staff.fragments.new_service_log

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddNewJobFragment : Fragment() {

//    private lateinit var binding: FragmentAddNewJobBinding
//    private val viewModel: NewServiceLogViewModel by activityViewModels()
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//
//        viewModel.myCustomer = null
//        viewModel.myServiceLog = null
//
//        binding = FragmentAddNewJobBinding.inflate(inflater, container, false)
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        binding.apply {
//            btnNew.setOnClickListener {
//                if(findNavController().currentDestination?.id == R.id.employeeAddNewJobFragment)
//                    findNavController().navigate(R.id.action_employeeAddNewJobFragment_to_newJobCustomerDetailsFragment)
//            }
//            btnExisting.setOnClickListener {
//                if (btnExisting.text == "Check Mobile No.") {
//
//                    ViewVisibilityUtil.visibilityExchanger(
//                        binding.progressBar,
//                        binding.linearLayout
//                    )
//
//                    viewModel.findCustomerByMobileNumber("+91${etNumber.text.toString()}")
//
//                    viewModel.readyToNavigate.observe(viewLifecycleOwner, {
//                        when (it) {
//                            false -> {
//                                ViewVisibilityUtil.visibilityExchanger(
//                                    binding.progressBar,
//                                    binding.linearLayout
//                                )
//                            }
//                            true -> {
//                                if (viewModel.myCustomer == null) {
//                                    Toast.makeText(
//                                        context,
//                                        "No Customer with this mobile number found, try again",
//                                        Toast.LENGTH_SHORT
//                                    ).show()
//                                    ViewVisibilityUtil.visibilityExchanger(
//                                        binding.linearLayout,
//                                        binding.progressBar
//                                    )
//                                } else {
//                                    Toast.makeText(
//                                        context,
//                                        "Found Customer Let's Proceed",
//                                        Toast.LENGTH_SHORT
//                                    ).show()
////                                    ViewVisibilityUtil.visibilityExchanger(
////                                        binding.linearLayout,
////                                        binding.progressBar
////                                    )
//                                    if(findNavController().currentDestination?.id == R.id.employeeAddNewJobFragment)
//                                        findNavController().navigate(R.id.action_employeeAddNewJobFragment_to_newJobCustomerDetailsFragment)
//                                }
//                            }
////                            is Resource.Loading ->{
////                                ViewVisibilityUtil.visible(binding.progressBar)
////                            }
////                            is Resource.Error ->{
////                                ViewVisibilityUtil.gone(binding.progressBar)
////                                Toast.makeText(context,"No Customer with this mobile number exists",Toast.LENGTH_SHORT).show()
////                            }
////                            is Resource.Success ->{
////                                Toast.makeText(context,"Found Customer Let's Proceed",Toast.LENGTH_SHORT).show()
////                                ViewVisibilityUtil.gone(binding.progressBar)
////                                findNavController().navigate(R.id.action_addNewJobFragment_to_newJobCustomerDetailsFragment)
////                            }
//                        }
//                    })
//                } else {
//                    ViewVisibilityUtil.visible(binding.tilNumber)
//                    ViewVisibilityUtil.gone(binding.btnNew)
//                    btnExisting.text = "Check Mobile No."
//                }
//            }
//        }
//    }

}