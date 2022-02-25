package com.sumeet.cars360.ui.customer.fragments

import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.tabs.TabLayoutMediator
import com.sumeet.cars360.R
import com.sumeet.cars360.data.wrapper.CustomerLoginType
import com.sumeet.cars360.data.local.preferences.ReadPrefs
import com.sumeet.cars360.databinding.BottomSheetCustomerServicesBinding
import com.sumeet.cars360.databinding.FragmentCustomerHomeBinding
import com.sumeet.cars360.ui.customer.CustomerViewModel
import com.sumeet.cars360.ui.customer.util.*
import com.sumeet.cars360.util.*
import dagger.hilt.android.AndroidEntryPoint
import kotlin.system.exitProcess

private const val MENU_PERIODIC_SERVICES = "Periodic\nServices"
private const val MENU_CUSTOM_SERVICES = "Custom\nServices"
private const val MENU_ROAD_SIDE_ASSISTANCE = "Road Side\nAssistance"
private const val MENU_EMERGENCY = "Emergency"
private const val MENU_GALLERIES = "View\nGalleries"
private const val MENU_JOB_CARD_HISTORY = "Job Card\nHistory"
private const val MENU_HEALTH_REPORT = "Health Report\nHistory"
private const val MENU_INVOICES = "Invoices\nHistory"
private const val MENU_ENQUIRY = "Enquiry"
private const val MENU_LOCATE = "Locate\nUs"
private const val MENU_EMAIL = "Email\nUs"
private const val MENU_BEFORE_AFTER = "Before/After\nImages"
private const val MENU_NEXT_SERVICE = "Next Service\nDate"
private const val MENU_INSURANCE_RENEW = "Insurance\nRenewal Date"
private const val MENU_CAR_DETAILING = "Car\nDetailing"
private const val MENU_TIRES = "Tires"
private const val MENU_CAR_WRAPPING = "Car\nWrapping"
private const val MENU_MODIFICATION = "Car\nModification"
private const val MENU_ACCESSORIES = "Car\nAccessories"
private const val MENU_REFER = "Refer/\nShare"

@AndroidEntryPoint
class CustomerHomeFragment : Fragment(), CustomerMenuListener {

    private lateinit var binding: FragmentCustomerHomeBinding
    private val viewModel: CustomerViewModel by activityViewModels()

    private lateinit var menuAdapter: CustomerMenuAdapter
    private lateinit var readPrefs: ReadPrefs

    private lateinit var mCustomerBottomSheetBehavior: BottomSheetBehavior<CoordinatorLayout>
    private lateinit var bottomSheetLayoutBinding: BottomSheetCustomerServicesBinding
//    private lateinit var customerRequestServiceSheetBinding: CustomerRequestServiceBottomSheetLayoutBinding

    private var isBottomSheetVisible: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        readPrefs = ReadPrefs(requireContext())
        binding = FragmentCustomerHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        setUpBackButtonListener()
        createAdList()
        handleBottomSheet()
        handleSocialsListeners()
        createCustomerMenu()
    }

    private fun handleSocialsListeners() {
        binding.apply {
            ivWebsite.setOnClickListener {
                try {
                    startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse(
                                "https://cars-360.in/index.php"
                            )
                        )
                    )
                } catch (e: Exception) {
                    Toast.makeText(
                        context,
                        "Something went wrong - ${e.message}",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }

            ivFacebook.setOnClickListener {
                try {
                    startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse(
                                "https://www.facebook.com/carsraipur360/?ref=bookmarks"
                            )
                        )
                    )
                } catch (e: Exception) {
                    Toast.makeText(
                        context,
                        "Something went wrong - ${e.message}",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }

            ivInstagram.setOnClickListener {
                try {
                    startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse(
                                "https://www.instagram.com/cars360raipur/"
                            )
                        )
                    )
                } catch (e: Exception) {
                    Toast.makeText(
                        context,
                        "Something went wrong - ${e.message}",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }

            ivYoutube.setOnClickListener {
                try {
                    startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse(
                                "https://www.youtube.com/channel/UC7KHqksz2u6IF9trW5Mi4jg?view_as=subscriber"
                            )
                        )
                    )
                } catch (e: Exception) {
                    Toast.makeText(
                        context,
                        "Something went wrong - ${e.message}",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }
        }
    }

    private fun createCustomerMenu() {

        menuAdapter = CustomerMenuAdapter(this)
        menuAdapter.menuList = when(readPrefs.readCustomerLoginType()){
            CustomerLoginType.LoggedIn -> getCompleteList()
            CustomerLoginType.SkippedLogin -> getSkippedList()
            CustomerLoginType.TakeATour -> getTourList()
        }

        binding.customerMenuRecyclerView.apply {
            adapter = menuAdapter
            layoutManager = GridLayoutManager(context, 4, GridLayoutManager.VERTICAL, false)
        }

    }

    private fun getTourList(): List<Pair<Drawable?, String>> {
        return listOf(
            Pair(
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_accidental_repair),
                MENU_ROAD_SIDE_ASSISTANCE
            ),
            Pair(
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_call),
                MENU_EMERGENCY
            ),
            Pair(
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_advertisements),
                MENU_GALLERIES
            ),
            Pair(
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_enquiry),
                MENU_ENQUIRY
            ),
            Pair(
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_locate_us),
                MENU_LOCATE
            ),
            Pair(
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_email),
                MENU_EMAIL
            ),
            Pair(
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_detailing),
                MENU_CAR_DETAILING
            ),
            Pair(
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_tyres),
                MENU_TIRES
            ),
            Pair(
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_wrap),
                MENU_CAR_WRAPPING
            ),
            Pair(
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_modification),
                MENU_MODIFICATION
            ),
            Pair(
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_accessories),
                MENU_ACCESSORIES
            ),
            Pair(
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_share),
                MENU_REFER
            )
        )
    }

    private fun getSkippedList(): List<Pair<Drawable?, String>> {
        return listOf(
            Pair(
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_mechanical_and_services),
                MENU_PERIODIC_SERVICES
            ),
            Pair(
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_miscellaneous_services_24),
                MENU_CUSTOM_SERVICES
            ),
            Pair(
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_accidental_repair),
                MENU_ROAD_SIDE_ASSISTANCE
            ),
            Pair(
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_call),
                MENU_EMERGENCY
            ),
            Pair(
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_advertisements),
                MENU_GALLERIES
            ),
            Pair(
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_enquiry),
                MENU_ENQUIRY
            ),
            Pair(
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_locate_us),
                MENU_LOCATE
            ),
            Pair(
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_email),
                MENU_EMAIL
            ),Pair(
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_detailing),
                MENU_CAR_DETAILING
            ),
            Pair(
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_tyres),
                MENU_TIRES
            ),
            Pair(
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_wrap),
                MENU_CAR_WRAPPING
            ),
            Pair(
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_modification),
                MENU_MODIFICATION
            ),
            Pair(
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_accessories),
                MENU_ACCESSORIES
            ),
            Pair(
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_share),
                MENU_REFER
            )
        )
    }

    private fun getCompleteList(): List<Pair<Drawable?, String>> {
        return listOf(
            Pair(
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_mechanical_and_services),
                MENU_PERIODIC_SERVICES
            ),
            Pair(
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_miscellaneous_services_24),
                MENU_CUSTOM_SERVICES
            ),
            Pair(
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_accidental_repair),
                MENU_ROAD_SIDE_ASSISTANCE
            ),
            Pair(
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_call),
                MENU_EMERGENCY
            ),
            Pair(
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_advertisements),
                MENU_GALLERIES
            ),
            Pair(
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_history),
                MENU_JOB_CARD_HISTORY
            ),
            Pair(
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_invoice),
                MENU_INVOICES
            ),
            Pair(
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_health_report),
                MENU_HEALTH_REPORT
            ),
            Pair(
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_enquiry),
                MENU_ENQUIRY
            ),
            Pair(
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_locate_us),
                MENU_LOCATE
            ),
            Pair(
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_email),
                MENU_EMAIL
            ),
            Pair(
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_before_after),
                MENU_BEFORE_AFTER
            ),
            Pair(
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_mechanical_and_services),
                MENU_NEXT_SERVICE
            ),
            Pair(
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_mechanical_and_services),
                MENU_INSURANCE_RENEW
            ),
            Pair(
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_detailing),
                MENU_CAR_DETAILING
            ),
            Pair(
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_tyres),
                MENU_TIRES
            ),
            Pair(
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_wrap),
                MENU_CAR_WRAPPING
            ),
            Pair(
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_modification),
                MENU_MODIFICATION
            ),
            Pair(
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_accessories),
                MENU_ACCESSORIES
            ),
            Pair(
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_share),
                MENU_REFER
            )
        )
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

    override fun onCustomerMenuClicked(menuString: String) {

        when (menuString) {

            MENU_PERIODIC_SERVICES -> {
                BottomSheetMenuDataHandler().apply {
                    binding = bottomSheetLayoutBinding
                    resetData()
                    populateData(
                        requireContext(),
                        BottomSheetMenuDataHandler.MenuType.PERIODIC_SERVICES
                    )
                }
                showBottomSheet()
                bottomSheetLayoutBinding.ibMinimizeBottomSheet.setOnClickListener {
                    hideBottomSheet()
                }
            }

            MENU_CUSTOM_SERVICES -> {
                BottomSheetMenuDataHandler().apply {
                    binding = bottomSheetLayoutBinding
                    resetData()
                    populateData(
                        requireContext(),
                        BottomSheetMenuDataHandler.MenuType.CUSTOM_SERVICES
                    )
                }
                showBottomSheet()
                bottomSheetLayoutBinding.ibMinimizeBottomSheet.setOnClickListener {
                    hideBottomSheet()
                }
            }

            MENU_ROAD_SIDE_ASSISTANCE -> {
                try {
                    startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:+919584390909")))
                } catch (e: Exception) {
                    Toast.makeText(
                        context,
                        "Something went wrong - ${e.message}",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }

            MENU_EMERGENCY -> {
                try {
                    startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:+919584390909")))
                } catch (e: Exception) {
                    Toast.makeText(
                        context,
                        "Something went wrong - ${e.message}",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }

            MENU_GALLERIES -> {
                Toast.makeText(context,"Feature will be available soon once admin and service advisors are completely operational",Toast.LENGTH_SHORT).show()
            }

            MENU_JOB_CARD_HISTORY -> {
                Toast.makeText(context,"Feature will be available soon once admin and service advisors are completely operational",Toast.LENGTH_SHORT).show()
            }

            MENU_INVOICES -> {
                Toast.makeText(context,"Feature will be available soon once admin and service advisors are completely operational",Toast.LENGTH_SHORT).show()
            }

            MENU_HEALTH_REPORT -> {
                Toast.makeText(context,"Feature will be available soon once admin and service advisors are completely operational",Toast.LENGTH_SHORT).show()
            }

            MENU_ENQUIRY -> {
                Toast.makeText(context,"This is a Notification related feature and will be avalable soon",Toast.LENGTH_SHORT).show()
            }

            MENU_LOCATE -> {
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
                    Toast.makeText(
                        context,
                        "Something went wrong - ${e.message}",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }

            MENU_EMAIL -> {
                try {
                    startActivity(
                        Intent(
                            Intent.ACTION_SENDTO,
                            Uri.fromParts(
                                "mailto", "admin@cars360.in", null
                            )
                        ).putExtra(Intent.EXTRA_SUBJECT, "Enquiry From App")
                            .putExtra(Intent.EXTRA_TEXT, "Hi, ")
                    )
                } catch (e: Exception) {
                    Toast.makeText(
                        context,
                        "Something went wrong - ${e.message}",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }

            MENU_BEFORE_AFTER -> {
                Toast.makeText(context,"Feature will be available soon",Toast.LENGTH_SHORT).show()
            }

            MENU_NEXT_SERVICE -> {
                Toast.makeText(context,"Feature will be available soon",Toast.LENGTH_SHORT).show()
            }

            MENU_INSURANCE_RENEW -> {
                Toast.makeText(context,"Feature will be available soon",Toast.LENGTH_SHORT).show()
            }

            MENU_CAR_DETAILING -> {
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

            MENU_TIRES -> {
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

            MENU_CAR_WRAPPING -> {
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

            MENU_MODIFICATION -> {
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

            MENU_ACCESSORIES -> {
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

            MENU_REFER -> {
                try {
                    startActivity(
                        Intent.createChooser(
                            Intent().apply {
                                action = Intent.ACTION_SEND
                                putExtra(Intent.EXTRA_TEXT, "This message will contain appstore link")
                                type = "text/plain"
                            },
                            null
                        )
                    )
                } catch (e: Exception) {
                    Toast.makeText(context, "Something went wrong - ${e.message}", Toast.LENGTH_SHORT)
                        .show()
                }
            }

        }

    }

}