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
    private var isCarModelSelected = false

    private var isBottomSheetVisible = false
    private lateinit var mCarsBottomSheetBehavior: BottomSheetBehavior<CoordinatorLayout>
    private lateinit var bottomSheetLayoutBinding: BottomSheetCarDetailsBinding

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
        setUpCarBrand()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        findNavController().popBackStack()
        return true
    }

//    private fun handleBottomSheet() {
//
//        bottomSheetLayoutBinding = binding.carDetailsBottomSheet
//        mCarsBottomSheetBehavior = BottomSheetBehavior.from(binding.carDetailsSheet)
//        mCarsBottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
//
//        setUpOptionsListListeners()
//
//    }
//
//    private fun setUpOptionsListListeners() {
//
//        mCarsBottomSheetBehavior.addBottomSheetCallback(object :
//            BottomSheetBehavior.BottomSheetCallback() {
//            override fun onStateChanged(bottomSheet: View, newState: Int) {
//                when (newState) {
//                    BottomSheetBehavior.STATE_COLLAPSED -> {
//                        mCarsBottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
//                    }
//                    BottomSheetBehavior.STATE_EXPANDED -> {
//                        binding.vCustomerTransparentBg.visibility = View.VISIBLE
//                    }
//                    BottomSheetBehavior.STATE_HALF_EXPANDED -> {
//                    }
//                    BottomSheetBehavior.STATE_HIDDEN -> {
//                        binding.vCustomerTransparentBg.visibility = View.GONE
//                    }
//                    BottomSheetBehavior.STATE_DRAGGING -> {
//                    }
//                    BottomSheetBehavior.STATE_SETTLING -> {
//                        binding.vCustomerTransparentBg.visibility = View.VISIBLE
//                    }
//                }
//            }
//
//            override fun onSlide(bottomSheet: View, slideOffset: Float) {
//
//            }
//        })
//
//        binding.apply {
//            vCustomerTransparentBg.setOnClickListener {
//                if (isBottomSheetVisible) {
//                    hideBottomSheet()
//                }
//            }
//        }
//    }

    private fun setUpCarBrand() {

//        viewModel.getAllCarEntities()
//        viewModel.carEntities.observe(viewLifecycleOwner) {
//            when (it) {
//                is OldResource.Loading -> {
//                }
//                is OldResource.Error -> {
//                    ViewVisibilityUtil.gone(binding.shimmerFrameLayout)
//                    Toast.makeText(context, "Oops! ${it.message}", Toast.LENGTH_LONG).show()
//                }
//                is OldResource.Success -> {
//                    it.data?.carBrandResponse?.let { list ->
////                        brandAdapterBrand = CarBrandSelectAdapter(list as ArrayList<CarBrandResponse>,this@CarCompanyMasterFragment)
////                        binding.carCompanyRecyclerView.adapter = brandAdapterBrand
//                        binding.etSearch.apply {
//                            setOnClickListener {
//                                if(isBottomSheetVisible){
//                                    hideBottomSheet()
//                                }
//                            }
//
//                            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//                                override fun onQueryTextSubmit(query: String?): Boolean {
//                                    brandAdapterBrand.getFilter().filter(query)
//                                    return true
//                                }
//
//                                override fun onQueryTextChange(newText: String?): Boolean {
//                                    brandAdapterBrand.getFilter().filter(newText)
//                                    return true
//                                }
//
//                            })
//
//
//                        }
//
//                    }
//                    ViewVisibilityUtil.visibilityExchanger(
//                        binding.carCompanyRecyclerView,
//                        binding.shimmerFrameLayout
//                    )
//                }
//            }
//        }

        viewModel.carBrands.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Resource.Loading -> {}
                is Resource.Error -> {
                    //TODO show error message and implement swipe to refresh or other notification method
                }
                is Resource.Success -> {
                    ViewVisibilityUtil.gone(binding.shimmerFrameLayout)
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