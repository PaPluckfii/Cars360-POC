package com.sumeet.cars360.ui.onboarding.fragments.new_customer

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.sumeet.cars360.data.local.preferences.CustomerLoginType
import com.sumeet.cars360.data.local.preferences.ReadPrefs
import com.sumeet.cars360.data.local.preferences.SavePrefs
import com.sumeet.cars360.data.remote.model.car_entities.CarBrandResponse
import com.sumeet.cars360.data.remote.model.car_entities.CarModelResponse
import com.sumeet.cars360.databinding.CarDetailsBottomSheetBinding
import com.sumeet.cars360.databinding.FragmentNewCarDetailsBinding
import com.sumeet.cars360.ui.customer.CustomerActivity
import com.sumeet.cars360.util.Resource
import com.sumeet.cars360.util.ViewVisibilityUtil
import com.sumeet.cars360.util.hideVirtualKeyBoard
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class NewCarDetailsFragment : Fragment(),
    CarBrandSelectAdapter.CarBrandSelectListener,
    CarModelSelectAdapter.CarModelSelectListener
{

    private lateinit var binding: FragmentNewCarDetailsBinding
    private val viewModel: NewCustomerViewModel by activityViewModels()

    private lateinit var brandAdapterBrand: CarBrandSelectAdapter
    private lateinit var modelAdapterBrand: CarModelSelectAdapter
    private var isCarModelSelected = false
    private var listOfCarEntities = emptyList<CarBrandResponse>()

    private var isBottomSheetVisible = false
    private lateinit var mCarsBottomSheetBehavior: BottomSheetBehavior<CoordinatorLayout>
    private lateinit var bottomSheetLayoutBinding: CarDetailsBottomSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewCarDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        handleBottomSheet()
        setUpRecyclerAdapter()
        setUpCarBrand()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (isBottomSheetVisible) {
            hideBottomSheet()
        }
        else
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
                viewModel.fuelType = "Petrol"
//                ViewVisibilityUtil.visibilityExchanger(llAdditionalDetails,llFuelType)
                goToHome()
            }
            llDiesel.setOnClickListener {
                viewModel.fuelType = "Diesel"
//                ViewVisibilityUtil.visibilityExchanger(llAdditionalDetails,llFuelType)
                goToHome()
            }
            llElectric.setOnClickListener {
                viewModel.fuelType = "Electric"
//                ViewVisibilityUtil.visibilityExchanger(llAdditionalDetails,llFuelType)
                goToHome()
            }
            llCNG.setOnClickListener {
                viewModel.fuelType = "CNG"
//                ViewVisibilityUtil.visibilityExchanger(llAdditionalDetails,llFuelType)
                goToHome()
            }

        }

    }

    private fun goToHome() {
        SavePrefs(requireContext()).apply {
            saveCarModelId(viewModel.modelId)
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

    private fun setUpRecyclerAdapter() {


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
                        brandAdapterBrand = CarBrandSelectAdapter(list as ArrayList<CarBrandResponse>,this@NewCarDetailsFragment)
                        binding.carsRecyclerView.adapter = brandAdapterBrand

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
                        binding.carsRecyclerView,
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