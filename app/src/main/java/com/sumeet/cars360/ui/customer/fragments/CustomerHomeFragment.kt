package com.sumeet.cars360.ui.customer.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.tabs.TabLayoutMediator
import com.sumeet.cars360.R
import com.sumeet.cars360.databinding.CustomerRequestServiceBottomSheetLayoutBinding
import com.sumeet.cars360.databinding.CustomerServicesBottomSheetLayoutBinding
import com.sumeet.cars360.databinding.FragmentCustomerHomeBinding
import com.sumeet.cars360.ui.customer.CustomerViewModel
import com.sumeet.cars360.ui.customer.util.AdViewPagerAdapter
import com.sumeet.cars360.ui.customer.util.GalleriesRecyclerAdapter
import com.sumeet.cars360.ui.customer.util.ServiceRequestDateItemClickListener
import com.sumeet.cars360.util.*
import dagger.hilt.android.AndroidEntryPoint
import kotlin.system.exitProcess

@AndroidEntryPoint
class CustomerHomeFragment : Fragment() {

    private lateinit var binding: FragmentCustomerHomeBinding
    private val viewModel: CustomerViewModel by activityViewModels()

    private lateinit var mCustomerBottomSheetBehavior: BottomSheetBehavior<CoordinatorLayout>
    private lateinit var bottomSheetLayoutBinding: CustomerServicesBottomSheetLayoutBinding
//    private lateinit var customerRequestServiceSheetBinding: CustomerRequestServiceBottomSheetLayoutBinding

    private var isBottomSheetVisible: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCustomerHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        setUpBackButtonListener()
        createAdList()
        handleBottomSheet()
        observeGalleriesData()
    }

    private fun handleBottomSheet() {

        bottomSheetLayoutBinding = binding.customerServicesBottomSheetLayout
        mCustomerBottomSheetBehavior = BottomSheetBehavior.from(binding.customerServicesSheet)
        mCustomerBottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN

        setUpOptionsListListeners()

//        setUpRequestServiceListeners()
    }

//    private fun setUpRequestServiceListeners() {
//
//        customerRequestServiceSheetBinding.apply {
//            serviceSpinner.adapter = ArrayAdapter(
//                requireContext(),
//                R.layout.item_services_spinner,
//                resources.getStringArray(R.array.ServiceRequests)
//            )
//            //TODO
//            carSpinner.adapter = ArrayAdapter(
//                requireContext(),
//                R.layout.item_services_spinner,
//                arrayOf(
//                    "BMW CL 500",
//                    "BMW CL 500"
//                )
//            )
//        }
//
//        customerRequestServiceSheetBinding.apply {
//            btnSelectDate.setOnClickListener {
//                showDatePicker()
//
//                datePicker.apply {
//                    val now = System.currentTimeMillis() - 1000
//                    minDate = now
//                    maxDate = now + (1000 * 60 * 60 * 24 * 7)
//                }
//
//            }
//        }
//    }

//    private fun showDatePicker() {
//        ViewVisibilityUtil.apply {
//            gone(customerRequestServiceSheetBinding.llrequestService)
//            visible(customerRequestServiceSheetBinding.datePicker)
//            visible(customerRequestServiceSheetBinding.btnCreateRequest)
//        }
//    }
//
//    private fun hideDatePicker() {
//        ViewVisibilityUtil.apply {
//            visible(customerRequestServiceSheetBinding.llrequestService)
//            gone(customerRequestServiceSheetBinding.datePicker)
//            gone(customerRequestServiceSheetBinding.btnCreateRequest)
//        }
//    }

//    private fun showRequestBottomSheet(menuType: BottomSheetMenuDataHandler.MenuType) {
//        ViewVisibilityUtil.visibilityExchanger(
//            binding.customerServicesBottomSheetLayout.root,
//            binding.customerRequestServiceBottomSheetLayout.root
//        )
//        //when(menuType)
//    }

//    private fun hideRequestBottomSheet() {
//        ViewVisibilityUtil.visibilityExchanger(
//            binding.customerRequestServiceBottomSheetLayout.root,
//            binding.customerServicesBottomSheetLayout.root
//        )
//    }

    private fun setUpOptionsListListeners() {

        mCustomerBottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        mCustomerBottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
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
            binding.vCustomerTransparentBg.setOnClickListener {
                if (isBottomSheetVisible)
                    hideBottomSheet()
            }
            llMechanicalAndRegularServicing.setOnClickListener {
                BottomSheetMenuDataHandler().apply {
                    binding = bottomSheetLayoutBinding
                    resetData()
                    populateData(
                        requireContext(),
                        BottomSheetMenuDataHandler.MenuType.MECHANICAL_SERVICES
                    )
                }
                showBottomSheet()
                bottomSheetLayoutBinding.ibMinimizeBottomSheet.setOnClickListener {
                    hideBottomSheet()
                }
            }
            llAccidentalRepair.setOnClickListener {
                BottomSheetMenuDataHandler().apply {
                    binding = bottomSheetLayoutBinding
                    resetData()
                    populateData(
                        requireContext(),
                        BottomSheetMenuDataHandler.MenuType.ACCIDENTAL_REPAIR
                    )
                }
                showBottomSheet()
                bottomSheetLayoutBinding.ibMinimizeBottomSheet.setOnClickListener {
                    hideBottomSheet()
                }
            }
            llCarDetailing.setOnClickListener {
                BottomSheetMenuDataHandler().apply {
                    binding = bottomSheetLayoutBinding
                    resetData()
                    populateData(
                        requireContext(),
                        BottomSheetMenuDataHandler.MenuType.CAR_DETAILING
                    )
                }
                showBottomSheet()
                bottomSheetLayoutBinding.ibMinimizeBottomSheet.setOnClickListener {
                    hideBottomSheet()
                }
            }
            llServices.setOnClickListener {
                BottomSheetMenuDataHandler().apply {
                    binding = bottomSheetLayoutBinding
                    resetData()
                    populateData(
                        requireContext(),
                        BottomSheetMenuDataHandler.MenuType.LIFE_LONG_CARE
                    )
                }
                showBottomSheet()
                bottomSheetLayoutBinding.ibMinimizeBottomSheet.setOnClickListener {
                    hideBottomSheet()
                }
            }
            llTires.setOnClickListener {
                BottomSheetMenuDataHandler().apply {
                    binding = bottomSheetLayoutBinding
                    resetData()
                    populateData(
                        requireContext(),
                        BottomSheetMenuDataHandler.MenuType.TIRES
                    )
                }
                showBottomSheet()
                bottomSheetLayoutBinding.ibMinimizeBottomSheet.setOnClickListener {
                    hideBottomSheet()
                }
            }
            llCarWrapping.setOnClickListener {
                BottomSheetMenuDataHandler().apply {
                    binding = bottomSheetLayoutBinding
                    resetData()
                    populateData(
                        requireContext(),
                        BottomSheetMenuDataHandler.MenuType.CAR_WRAPPING
                    )
                }
                showBottomSheet()
                bottomSheetLayoutBinding.ibMinimizeBottomSheet.setOnClickListener {
                    hideBottomSheet()
                }
            }
            llCarModification.setOnClickListener {
                BottomSheetMenuDataHandler().apply {
                    binding = bottomSheetLayoutBinding
                    resetData()
                    populateData(
                        requireContext(),
                        BottomSheetMenuDataHandler.MenuType.CAR_MODIFICATION
                    )
                }
                showBottomSheet()
                bottomSheetLayoutBinding.ibMinimizeBottomSheet.setOnClickListener {
                    hideBottomSheet()
                }
            }
            llAccessories.setOnClickListener {
                BottomSheetMenuDataHandler().apply {
                    binding = bottomSheetLayoutBinding
                    resetData()
                    populateData(
                        requireContext(),
                        BottomSheetMenuDataHandler.MenuType.ACCESSORIES
                    )
                }
                showBottomSheet()
                bottomSheetLayoutBinding.ibMinimizeBottomSheet.setOnClickListener {
                    hideBottomSheet()
                }
            }
        }
    }

    private fun showBottomSheet() {
        mCustomerBottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        binding.vCustomerTransparentBg.visibility = View.VISIBLE
        isBottomSheetVisible = true
    }

    private fun hideBottomSheet() {
        mCustomerBottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        binding.vCustomerTransparentBg.visibility = View.GONE
        isBottomSheetVisible = false
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.locate_us_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        try {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(
                        "https://www.google.com/maps/place/" +
                                "Cars+360%C2%B0/@21.235233,81.589693,15z/data=" +
                                "!4m5!3m4!1s0x0:0x7dace6e394d698e3!8m2!3d21.2352333!4d81." +
                                "5896934?hl=en-US"
                    )
                )
            )
        } catch (e: Exception) {
            Toast.makeText(context, "Something went wrong - ${e.message}", Toast.LENGTH_SHORT)
                .show()
        }
        return true
    }

    private fun observeGalleriesData() {

        val recyclerAdapter = GalleriesRecyclerAdapter(viewModel.getGalleriesData())

        binding.galleriesReviewRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = recyclerAdapter

        }

        GalleriesAutoScroll().apply {
            adapter = recyclerAdapter
            recyclerView = binding.galleriesReviewRecyclerView
            autoScroll(4000L)
        }

    }

    private fun createAdList() {
        //TODO setup ad banners
        //TODO get ad list form server

        val adList = listOf(
            ContextCompat.getDrawable(requireContext(), R.drawable.dummy_banner_2),
            ContextCompat.getDrawable(requireContext(), R.drawable.dummy_banner_2),
            ContextCompat.getDrawable(requireContext(), R.drawable.dummy_banner_2),
            ContextCompat.getDrawable(requireContext(), R.drawable.dummy_banner_2),
            ContextCompat.getDrawable(requireContext(), R.drawable.dummy_banner_2)
        )

        binding.adViewPager.apply {
            setPageTransformer(ZoomOutPageTransformer())
            adapter = AdViewPagerAdapter(adList)
            autoScroll(2600L)
        }

        TabLayoutMediator(binding.tabLayout, binding.adViewPager) { _, _ ->
        }.attach()

    }

    private var mBackPressedTime: Long = 0L
    private fun setUpBackButtonListener() {
        (requireActivity() as AppCompatActivity).onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (isBottomSheetVisible)
                        hideBottomSheet()
                    else {
                        if (mBackPressedTime + Constants.BACK_BUTTON_TIME_INTERVAL > System.currentTimeMillis()) {
                            exitProcess(0)
                        } else {
                            Toast.makeText(context, "Tap back again to exit", Toast.LENGTH_SHORT)
                                .show()
                        }
                        mBackPressedTime = System.currentTimeMillis()
                    }
                }
            })
    }

}