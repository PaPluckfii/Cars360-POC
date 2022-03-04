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
        binding.swipeRefreshLayout.isRefreshing = true
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
                    binding.swipeRefreshLayout.isRefreshing = false
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
        binding.swipeRefreshLayout.setOnRefreshListener {

        }
    }

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