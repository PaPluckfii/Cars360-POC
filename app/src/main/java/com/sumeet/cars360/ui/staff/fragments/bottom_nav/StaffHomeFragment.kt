package com.sumeet.cars360.ui.staff.fragments.bottom_nav

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayoutMediator
import com.sumeet.cars360.R
import com.sumeet.cars360.databinding.FragmentStaffHomeBinding
import com.sumeet.cars360.ui.customer.util.AdViewPagerAdapter
import com.sumeet.cars360.ui.customer.util.GalleriesRecyclerAdapter
import com.sumeet.cars360.ui.staff.StaffViewModel
import com.sumeet.cars360.util.*
import dagger.hilt.android.AndroidEntryPoint
import kotlin.system.exitProcess

@AndroidEntryPoint
class StaffHomeFragment : Fragment() {

    private lateinit var binding: FragmentStaffHomeBinding
    private val viewModel: StaffViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStaffHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        setUpBackButtonListener()
        handleListeners()
    }

    private fun handleListeners() {

        binding.apply {
            llCarCompanyMaster.setOnClickListener {
                if(ButtonClickHandler.buttonClicked())
                    navigate(StaffHomeFragmentDirections.actionNavigationHomeToCarCompanyMasterFragment())
            }
            llCustomerMaster.setOnClickListener {
                if(ButtonClickHandler.buttonClicked())
                    navigate(StaffHomeFragmentDirections.actionNavigationHomeToCustomerMasterFragment())
            }
            llServiceAdvisorMaster.setOnClickListener {
                if(ButtonClickHandler.buttonClicked())
                    navigate(StaffHomeFragmentDirections.actionNavigationHomeToServiceAdvisorMasterFragment())
            }
            llNotificationMaster.setOnClickListener {
                if(ButtonClickHandler.buttonClicked())
                    navigate(StaffHomeFragmentDirections.actionNavigationHomeToNotificationMasterFragment())
            }
            llServiceLogMaster.setOnClickListener {
                if(ButtonClickHandler.buttonClicked())
                    navigate(StaffHomeFragmentDirections.actionNavigationHomeToServiceLogMasterFragment())
            }
            llPostServiceFollowUp.setOnClickListener {
                if(ButtonClickHandler.buttonClicked())
                    navigate(StaffHomeFragmentDirections.actionNavigationHomeToPostServiceFollowUpFragment())
            }
            llCarHealthReport.setOnClickListener {
                if(ButtonClickHandler.buttonClicked())
                    navigate(StaffHomeFragmentDirections.actionNavigationHomeToCarHealthReportFragment())
            }
            llInvoices.setOnClickListener {
                if(ButtonClickHandler.buttonClicked())
                    navigate(StaffHomeFragmentDirections.actionNavigationHomeToInvoicesUploadFragment())
            }
            llEnquiryAndComplaints.setOnClickListener {
                if(ButtonClickHandler.buttonClicked())
                    navigate(StaffHomeFragmentDirections.actionNavigationHomeToEnquiryAndComlaintFragment())
            }
            llBeforeAfter.setOnClickListener {
                if(ButtonClickHandler.buttonClicked())
                    navigate(StaffHomeFragmentDirections.actionNavigationHomeToBeforeAfterImagesUploadFragment())
            }
        }
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

}