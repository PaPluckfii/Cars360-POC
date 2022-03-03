package com.sumeet.cars360.ui.admin.home

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.sumeet.cars360.R
import com.sumeet.cars360.data.local.preferences.ReadPrefs
import com.sumeet.cars360.data.wrapper.UserType
import com.sumeet.cars360.databinding.FragmentAdminHomeBinding
import com.sumeet.cars360.ui.admin.AdminViewModel
import com.sumeet.cars360.ui.customer.util.CustomerMenuAdapter
import com.sumeet.cars360.ui.customer.util.CustomerMenuListener
import com.sumeet.cars360.util.ButtonClickHandler
import com.sumeet.cars360.util.Constants
import com.sumeet.cars360.util.navigate
import dagger.hilt.android.AndroidEntryPoint
import kotlin.system.exitProcess

private const val MASTER_CAR_COMPANY = "Car Company Master"
private const val MASTER_CUSTOMER = "Customer Master"
private const val MASTER_SERVICE_ADVISOR = "Service Advisor Master"
private const val MASTER_SERVICE_LOGS = "Service Log Master"
private const val MASTER_NOTIFICATION = "Notification Master"
private const val MASTER_ENQUIRY_AND_COMPLAINT = "Enquiry And Complaint"
private const val MASTER_GALLERY = "Galleries"
private const val MASTER_AD_BANNER = "Ad Banner Master"
private const val ALL_CUSTOMERS = "All Customers"
private const val ALL_SERVICE_ADVISORS = "All Service Advisors"
private const val ALL_SERVICE_LOGS = "All Service Logs"

@AndroidEntryPoint
class AdminHomeFragment : Fragment(), CustomerMenuListener {

    private lateinit var binding: FragmentAdminHomeBinding
    private val viewModel: AdminViewModel by activityViewModels()

    private lateinit var menuAdapter: CustomerMenuAdapter
    private lateinit var readPrefs: ReadPrefs

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        readPrefs = ReadPrefs(requireContext())
        binding = FragmentAdminHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpBackButtonListener()
        createCustomerMenu()
    }

    private fun createCustomerMenu() {
        menuAdapter = CustomerMenuAdapter(this)
        menuAdapter.menuList = when(readPrefs.readUserType()){
            UserType.Admin -> getAdminMenuList()
            UserType.Employee -> getEmployeeMenuList()
            else -> getEmployeeMenuList()
        }
        binding.staffMenuRecyclerView.apply {
            adapter = menuAdapter
            layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
        }
    }

    private fun getEmployeeMenuList(): List<Pair<Drawable?, String>> {
        return listOf(
            Pair(
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_car_primary_red),
                MASTER_CAR_COMPANY
            ),
            Pair(
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_person_add_24),
                MASTER_CUSTOMER
            ),
            Pair(
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_mechanical_and_services),
                MASTER_SERVICE_ADVISOR
            ),
            Pair(
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_miscellaneous_services_24),
                MASTER_SERVICE_LOGS
            ),
            Pair(
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_notifications_active_24),
                MASTER_NOTIFICATION
            ),
            Pair(
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_enquiry),
                MASTER_ENQUIRY_AND_COMPLAINT
            ),
            Pair(
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_advertisements),
                MASTER_AD_BANNER
            ),
            Pair(
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_doc_test),
                MASTER_GALLERY
            ),
            Pair(
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_all_customers),
                ALL_CUSTOMERS
            ),
            Pair(
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_all_service_advisors),
                ALL_SERVICE_ADVISORS
            ),
            Pair(
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_history),
                ALL_SERVICE_LOGS
            )
        )
    }

    private fun getAdminMenuList(): List<Pair<Drawable?, String>> {
        return listOf(
            Pair(
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_car_primary_red),
                MASTER_CAR_COMPANY
            ),
            Pair(
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_person_add_24),
                MASTER_CUSTOMER
            ),
            Pair(
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_miscellaneous_services_24),
                MASTER_SERVICE_LOGS
            ),
            Pair(
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_enquiry),
                MASTER_ENQUIRY_AND_COMPLAINT
            ),
            Pair(
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_all_customers),
                ALL_CUSTOMERS
            ),
            Pair(
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_history),
                ALL_SERVICE_LOGS
            )
        )
    }

    private var mBackPressedTime: Long = 0L
    private fun setUpBackButtonListener() {
        (requireActivity() as AppCompatActivity).onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (mBackPressedTime + Constants.BACK_BUTTON_TIME_INTERVAL > System.currentTimeMillis()) {
                        exitProcess(0)
                    } else {
                        Toast.makeText(context, "Tap back again to exit", Toast.LENGTH_SHORT)
                            .show()
                    }
                    mBackPressedTime = System.currentTimeMillis()
                }
            })
    }

//    private fun handleListeners() {
//        binding.apply {
//            llCarCompanyMaster.setOnClickListener {
//                if (ButtonClickHandler.buttonClicked())
//                    navigate(AdminHomeFragmentDirections.actionAdminNavigationHomeToCarCompanyMasterFragment2())
//            }
//            llCustomerMaster.setOnClickListener {
//                if (ButtonClickHandler.buttonClicked())
//                    navigate(AdminHomeFragmentDirections.actionAdminNavigationHomeToCustomerMasterFragment2())
//            }
//            llServiceLogMaster.setOnClickListener {
//                if (ButtonClickHandler.buttonClicked())
//                    navigate(AdminHomeFragmentDirections.actionAdminNavigationHomeToServiceLogMasterFragment2())
//            }
//            llServiceAdvisorMaster.setOnClickListener {
//                if (ButtonClickHandler.buttonClicked())
//                    navigate(AdminHomeFragmentDirections.actionAdminNavigationHomeToServiceAdvisorMasterFragment2())
//            }
//            llNotificationMaster.setOnClickListener {
//                if (ButtonClickHandler.buttonClicked())
//                    navigate(AdminHomeFragmentDirections.actionAdminNavigationHomeToNotificationMasterFragment2())
//            }
//            llPostServiceFollowUp.setOnClickListener {
//                if (ButtonClickHandler.buttonClicked())
//                    navigate(AdminHomeFragmentDirections.actionAdminNavigationHomeToPostServiceFollowUpFragment2())
//            }
//            llCarHealthReport.setOnClickListener {
//                if (ButtonClickHandler.buttonClicked())
//                    navigate(AdminHomeFragmentDirections.actionAdminNavigationHomeToCarHealthReportFragment2())
//            }
//            llInvoices.setOnClickListener {
//                if (ButtonClickHandler.buttonClicked())
//                    navigate(AdminHomeFragmentDirections.actionAdminNavigationHomeToInvoicesUploadFragment2())
//            }
//            llEnquiryAndComplaints.setOnClickListener {
//                if (ButtonClickHandler.buttonClicked())
//                    navigate(AdminHomeFragmentDirections.actionAdminNavigationHomeToEnquiryAndComlaintFragment2())
//            }
//            llBeforeAfter.setOnClickListener {
//                if (ButtonClickHandler.buttonClicked())
//                    navigate(AdminHomeFragmentDirections.actionAdminNavigationHomeToBeforeAfterImagesUploadFragment2())
//            }
//            llServiceLogHistory.setOnClickListener {
//                if (ButtonClickHandler.buttonClicked())
//                    navigate(AdminHomeFragmentDirections.actionAdminNavigationHomeToAdminNavigationServiceLogs())
//            }
//            llStatistics.setOnClickListener {
//                if (ButtonClickHandler.buttonClicked())
//                    navigate(AdminHomeFragmentDirections.actionAdminNavigationHomeToAdminNavigationStatistics())
//            }
//            llAllCustomers.setOnClickListener {
//                if (ButtonClickHandler.buttonClicked())
//                    navigate(AdminHomeFragmentDirections.actionAdminNavigationHomeToAllCustomersFragment())
//            }
//            llAllServiceAdvisors.setOnClickListener {
//                if (ButtonClickHandler.buttonClicked())
//                    navigate(AdminHomeFragmentDirections.actionAdminNavigationHomeToAllServiceAdvisorsFragment())
//            }
//            llAdBanner.setOnClickListener {
//                if (ButtonClickHandler.buttonClicked())
//                    navigate(AdminHomeFragmentDirections.actionAdminNavigationHomeToAdBannerFragment())
//            }
//        }
//    }

    override fun onCustomerMenuClicked(menuString: String) {
        when(menuString){
            MASTER_CAR_COMPANY -> {
                navigate(AdminHomeFragmentDirections.actionAdminNavigationHomeToCarCompanyMasterFragment2())
            }
            MASTER_CUSTOMER -> {
                navigate(AdminHomeFragmentDirections.actionAdminNavigationHomeToCustomerMasterFragment())
            }
            MASTER_SERVICE_ADVISOR -> {
                navigate(AdminHomeFragmentDirections.actionAdminNavigationHomeToServiceAdvisorMasterFragment2())
            }
            MASTER_SERVICE_LOGS -> {
                navigate(AdminHomeFragmentDirections.actionAdminNavigationHomeToServiceLogMasterCustomerDetailsFragment())
            }
            MASTER_AD_BANNER -> {
                navigate(AdminHomeFragmentDirections.actionAdminNavigationHomeToAdBannerFragment())
            }
            MASTER_GALLERY -> {
                //TODO
                Toast.makeText(context,"Galleries will be available soon",Toast.LENGTH_SHORT).show()
            }
            MASTER_ENQUIRY_AND_COMPLAINT -> {
                navigate(AdminHomeFragmentDirections.actionAdminNavigationHomeToEnquiryAndComlaintFragment2())
            }
            MASTER_NOTIFICATION -> {
                navigate(AdminHomeFragmentDirections.actionAdminNavigationHomeToNotificationMasterFragment2())
            }
            ALL_CUSTOMERS -> {
                navigate(AdminHomeFragmentDirections.actionAdminNavigationHomeToAllCustomersFragment())
            }
            ALL_SERVICE_LOGS -> {
                navigate(AdminHomeFragmentDirections.actionAdminNavigationHomeToAdminNavigationServiceLogs())
            }
            ALL_SERVICE_ADVISORS -> {
                navigate(AdminHomeFragmentDirections.actionAdminNavigationHomeToAllServiceAdvisorsFragment())
            }
        }
    }

}