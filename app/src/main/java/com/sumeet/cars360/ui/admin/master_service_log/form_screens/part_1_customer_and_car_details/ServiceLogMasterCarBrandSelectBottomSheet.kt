package com.sumeet.cars360.ui.admin.master_service_log.form_screens.part_1_customer_and_car_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sumeet.cars360.data.local.room.model.CarBrandEntity
import com.sumeet.cars360.databinding.FragmentCarCompanyMasterBinding
import com.sumeet.cars360.ui.admin.AdminViewModel
import com.sumeet.cars360.ui.onboarding.customer.car_select.CarBrandSelectAdapter
import com.sumeet.cars360.util.Resource
import com.sumeet.cars360.util.hideVirtualKeyBoard
import com.sumeet.cars360.util.navigate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ServiceLogMasterCarBrandSelectBottomSheet :
    BottomSheetDialogFragment(),
    CarBrandSelectAdapter.CarBrandSelectListener {

    private lateinit var binding: FragmentCarCompanyMasterBinding
    private val viewModel: AdminViewModel by activityViewModels()
    private val args: ServiceLogMasterCarBrandSelectBottomSheetArgs by navArgs()

    private lateinit var brandAdapterBrand: CarBrandSelectAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCarCompanyMasterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.swipeRefreshLayout.isRefreshing = true
        setUpCarBrand()
    }

    private fun setUpCarBrand() {
        viewModel.carBrands.observe(viewLifecycleOwner) { result ->
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

    private fun setUpRecyclerAdapter(list: List<CarBrandEntity>) {
        brandAdapterBrand = CarBrandSelectAdapter(
            list as ArrayList<CarBrandEntity>,
            this@ServiceLogMasterCarBrandSelectBottomSheet
        )
        binding.carCompanyRecyclerView.apply {
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (dialog is BottomSheetDialog) {
                        val behaviour = (dialog as BottomSheetDialog).behavior
                        behaviour.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                            override fun onStateChanged(bottomSheet: View, newState: Int) {
                                if (newState == BottomSheetBehavior.STATE_DRAGGING) {
//                                if(dx != 0 && dy != 0)
                                    behaviour.state = BottomSheetBehavior.STATE_EXPANDED
                                }
                            }

                            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                            }
                        })
                    }
                }
            })
            adapter = brandAdapterBrand
        }
            binding.etSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    brandAdapterBrand.getFilter().filter(query)
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    brandAdapterBrand.getFilter().filter(newText)
                    return true
                }

            })
    }

    override fun onCarBrandSelected(brandId: String) {
        hideVirtualKeyBoard(requireActivity(), requireContext())
        navigate(
            ServiceLogMasterCarBrandSelectBottomSheetDirections.actionServiceLogMasterCarSelectBottomSheetToServiceLogMasterNewCarDetailsBottomSheet(
                args.userId,
                brandId,
                ""
            )
        )
    }



}