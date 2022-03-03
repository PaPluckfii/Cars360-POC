package com.sumeet.cars360.ui.onboarding.customer.car_select

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.sumeet.cars360.data.local.preferences.ReadPrefs
import com.sumeet.cars360.data.local.preferences.SavePrefs
import com.sumeet.cars360.data.local.room.model.CarBrandEntity
import com.sumeet.cars360.data.local.room.model.CarModelEntity
import com.sumeet.cars360.data.wrapper.CustomerLoginType
import com.sumeet.cars360.databinding.BottomSheetCarDetailsBinding
import com.sumeet.cars360.databinding.FragmentNewCarDetailsBinding
import com.sumeet.cars360.ui.customer.CustomerActivity
import com.sumeet.cars360.ui.onboarding.OnBoardingViewModel
import com.sumeet.cars360.util.Resource
import com.sumeet.cars360.util.ViewVisibilityUtil
import com.sumeet.cars360.util.hideVirtualKeyBoard
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class NewCarDetailsFragment : Fragment(),
    CarBrandSelectAdapter.CarBrandSelectListener,
    CarModelSelectAdapter.CarModelSelectListener {

    private lateinit var binding: FragmentNewCarDetailsBinding
    private val viewModel: OnBoardingViewModel by activityViewModels()

    private lateinit var savePrefs: SavePrefs

    private lateinit var brandAdapterBrand: CarBrandSelectAdapter
    private lateinit var modelAdapterBrand: CarModelSelectAdapter

    private var isCarModelSelected = false
    private var isBottomSheetVisible = false

    private lateinit var mCarsBottomSheetBehavior: BottomSheetBehavior<CoordinatorLayout>
    private lateinit var bottomSheetLayoutBinding: BottomSheetCarDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        savePrefs = SavePrefs(requireContext())
        binding = FragmentNewCarDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        handleBottomSheet()
        setUpCarBrand()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (isBottomSheetVisible) {
            hideBottomSheet()
        } else
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

        bottomSheetLayoutBinding.apply {
            llPetrol.setOnClickListener {
                savePrefs.saveFuelType("Petrol")
                goToHome()
            }
            llDiesel.setOnClickListener {
                savePrefs.saveFuelType("Diesel")
                goToHome()
            }
//            llElectric.setOnClickListener {
//                savePrefs.saveFuelType("Petrol")
//                goToHome()
//            }
//            llCNG.setOnClickListener {
//                savePrefs.saveFuelType("Petrol")
//                goToHome()
//            }

        }

    }

    private fun goToHome() {
        SavePrefs(requireContext()).apply {
            if (ReadPrefs(requireContext()).readFirebaseId() != "")
                saveCustomerLoginType(CustomerLoginType.LoggedIn)
            else
                saveCustomerLoginType(CustomerLoginType.SkippedLogin)
        }
        startActivity(
            Intent(activity, CustomerActivity::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        ).also { activity?.finish() }
    }

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
//                        brandAdapterBrand = CarBrandSelectAdapter(list as ArrayList<CarBrandResponse>,this@NewCarDetailsFragment)
//                        binding.carsRecyclerView.adapter = brandAdapterBrand
//
//                        binding.etSearch.apply {
//
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
//                        binding.carsRecyclerView,
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
            this@NewCarDetailsFragment
        )
        binding.carsRecyclerView.adapter = brandAdapterBrand

        binding.etSearch.apply {
            setOnClickListener {
                if (isBottomSheetVisible) {
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

    override fun onCarItemSelected(modelEntity: CarModelEntity) {
        if (!isCarModelSelected) {
            savePrefs.saveCarModelId(modelEntity.modelId)
            isCarModelSelected = true
            ViewVisibilityUtil.visibilityExchanger(
                bottomSheetLayoutBinding.llFuelType,
                bottomSheetLayoutBinding.carModelsRecyclerView
            )
        }
    }

    override fun onCarBrandSelected(brandId: String) {
        hideVirtualKeyBoard(requireActivity(), requireContext())
        viewModel.getCarModelByBrandId(brandId).observe(viewLifecycleOwner){
            modelAdapterBrand = CarModelSelectAdapter(it, this)
            bottomSheetLayoutBinding.carModelsRecyclerView.adapter = modelAdapterBrand
        }
        showBottomSheet()
    }

}