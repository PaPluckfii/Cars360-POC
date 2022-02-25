package com.sumeet.cars360.ui.admin.fragments.home_navs

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.sumeet.cars360.data.remote.model.car_entities.CarBrandResponse
import com.sumeet.cars360.data.remote.model.car_entities.CarModelResponse
import com.sumeet.cars360.databinding.BottomSheetCarDetailsBinding
import com.sumeet.cars360.databinding.FragmentCarCompanyMasterBinding
import com.sumeet.cars360.ui.admin.AdminViewModel
import com.sumeet.cars360.ui.onboarding.fragments.customer.car_select.CarBrandSelectAdapter
import com.sumeet.cars360.ui.onboarding.fragments.customer.car_select.CarModelSelectAdapter
import com.sumeet.cars360.util.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CarCompanyMasterFragment : Fragment(),
    CarBrandSelectAdapter.CarBrandSelectListener,
    CarModelSelectAdapter.CarModelSelectListener
{

    private lateinit var binding: FragmentCarCompanyMasterBinding
    private val viewModel: AdminViewModel by activityViewModels()

    private lateinit var brandAdapterBrand: CarBrandSelectAdapter
    private lateinit var modelAdapterBrand: CarModelSelectAdapter
    private var isCarModelSelected = false

    private var isBottomSheetVisible = false
    private lateinit var mCarsBottomSheetBehavior: BottomSheetBehavior<CoordinatorLayout>
    private lateinit var bottomSheetLayoutBinding: BottomSheetCarDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCarCompanyMasterBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        handleBottomSheet()
        setUpCarBrand()
        handleListeners()
    }

    private fun handleListeners() {
        binding.fabAddCarModel.setOnClickListener {
            //TODO Add new Car
            Toast.makeText(context,"Feature not ready yet",Toast.LENGTH_SHORT).show()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        findNavController().popBackStack()
        return true
    }

    private fun handleBottomSheet() {

        bottomSheetLayoutBinding = binding.carDetailsBottomSheet
        mCarsBottomSheetBehavior = BottomSheetBehavior.from(binding.carDetailsSheet)
        mCarsBottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN

        setUpOptionsListListeners()

    }

    private fun setUpOptionsListListeners() {

        mCarsBottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        mCarsBottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
                    }
                    BottomSheetBehavior.STATE_EXPANDED -> {
                        binding.vCustomerTransparentBg.visibility = View.VISIBLE
                    }
                    BottomSheetBehavior.STATE_HALF_EXPANDED -> {
                    }
                    BottomSheetBehavior.STATE_HIDDEN -> {
                        binding.vCustomerTransparentBg.visibility = View.GONE
                    }
                    BottomSheetBehavior.STATE_DRAGGING -> {
                    }
                    BottomSheetBehavior.STATE_SETTLING -> {
                        binding.vCustomerTransparentBg.visibility = View.VISIBLE
                    }
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {

            }
        })

        binding.apply {
            vCustomerTransparentBg.setOnClickListener {
                if (isBottomSheetVisible) {
                    hideBottomSheet()
                }
            }
        }
    }

    private fun setUpCarBrand() {

        viewModel.getAllCarEntities()
        viewModel.carEntities.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                }
                is Resource.Error -> {
                    ViewVisibilityUtil.gone(binding.shimmerFrameLayout)
                    Toast.makeText(context, "Oops! ${it.message}", Toast.LENGTH_LONG).show()
                }
                is Resource.Success -> {
                    it.data?.carBrandResponse?.let { list ->
                        brandAdapterBrand = CarBrandSelectAdapter(list as ArrayList<CarBrandResponse>,this@CarCompanyMasterFragment)
                        binding.carCompanyRecyclerView.adapter = brandAdapterBrand

                        binding.etSearch.apply {

                            setOnClickListener {
                                if(isBottomSheetVisible){
                                    hideBottomSheet()
                                }
                            }

                            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
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

                    }
                    ViewVisibilityUtil.visibilityExchanger(
                        binding.carCompanyRecyclerView,
                        binding.shimmerFrameLayout
                    )
                }
            }
        }

    }

    private fun showBottomSheet() {
        ViewVisibilityUtil.visibilityExchanger(
            bottomSheetLayoutBinding.carModelsRecyclerView,
            bottomSheetLayoutBinding.llFuelType
        )
        mCarsBottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        binding.vCustomerTransparentBg.visibility = View.VISIBLE
        isBottomSheetVisible = true
    }

    private fun hideBottomSheet() {
        mCarsBottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        binding.vCustomerTransparentBg.visibility = View.GONE
        isBottomSheetVisible = false
        isCarModelSelected = false
    }

    override fun onCarItemSelected(id: String) {
        if (!isCarModelSelected) {
            viewModel.modelId = id
            isCarModelSelected = true
            ViewVisibilityUtil.visibilityExchanger(
                bottomSheetLayoutBinding.llFuelType,
                bottomSheetLayoutBinding.carModelsRecyclerView
            )
        }
    }

    override fun onCarBrandSelected(listOfModels: List<CarModelResponse>) {
        hideVirtualKeyBoard(requireActivity(),requireContext())
        modelAdapterBrand = CarModelSelectAdapter(listOfModels,this)
        bottomSheetLayoutBinding.carModelsRecyclerView.adapter = modelAdapterBrand
        showBottomSheet()
    }
}