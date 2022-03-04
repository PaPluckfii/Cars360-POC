package com.sumeet.cars360.ui.admin.master_car

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.sumeet.cars360.data.local.room.model.CarBrandEntity
import com.sumeet.cars360.databinding.BottomSheetCarDetailsBinding
import com.sumeet.cars360.databinding.FragmentCarCompanyMasterBinding
import com.sumeet.cars360.ui.admin.AdminViewModel
import com.sumeet.cars360.ui.onboarding.customer.car_select.CarBrandSelectAdapter
import com.sumeet.cars360.util.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CarCompanyMasterFragment : Fragment(),
    CarBrandSelectAdapter.CarBrandSelectListener
{

    private lateinit var binding: FragmentCarCompanyMasterBinding
    private val viewModel: AdminViewModel by activityViewModels()
    private val carCompanyMasterViewModel: CarCompanyMasterViewModel by viewModels()
    private lateinit var brandAdapterBrand: CarBrandSelectAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCarCompanyMasterBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        binding.swipeRefreshLayout.isRefreshing = true
        setUpCarBrand()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        findNavController().popBackStack()
        return true
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

    private fun setUpRecyclerAdapter(list: List<CarBrandEntity>){
        brandAdapterBrand = CarBrandSelectAdapter(
            list as ArrayList<CarBrandEntity>,
            this@CarCompanyMasterFragment
        )
        binding.carCompanyRecyclerView.adapter = brandAdapterBrand

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
        hideVirtualKeyBoard(requireActivity(),requireContext())
        navigate(CarCompanyMasterFragmentDirections.actionCarCompanyMasterFragmentToCarModelMasterFragment(brandId))
    }
}