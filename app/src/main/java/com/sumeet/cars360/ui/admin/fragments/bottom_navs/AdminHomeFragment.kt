package com.sumeet.cars360.ui.admin.fragments.bottom_navs

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sumeet.cars360.databinding.FragmentAdminHomeBinding
import com.sumeet.cars360.util.ButtonClickHandler
import com.sumeet.cars360.util.navigate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AdminHomeFragment : Fragment() {

    private lateinit var binding: FragmentAdminHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAdminHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleListeners()
    }

    private fun handleListeners() {
        binding.apply {
            llCarCompanyMaster.setOnClickListener {
                if(ButtonClickHandler.buttonClicked())
                    navigate(AdminHomeFragmentDirections.actionAdminNavigationHomeToCarCompanyMasterFragment2())
            }
            llCustomerMaster.setOnClickListener {
                if(ButtonClickHandler.buttonClicked())
                    navigate(AdminHomeFragmentDirections.actionAdminNavigationHomeToCustomerMasterFragment2())
            }
            llServiceLogMaster.setOnClickListener {
                if(ButtonClickHandler.buttonClicked())
                    navigate(AdminHomeFragmentDirections.actionAdminNavigationHomeToServiceLogMasterFragment2())
            }
            llServiceAdvisorMaster.setOnClickListener {
                if(ButtonClickHandler.buttonClicked())
                    navigate(AdminHomeFragmentDirections.actionAdminNavigationHomeToServiceAdvisorMasterFragment2())
            }
            llNotificationMaster.setOnClickListener {
                if(ButtonClickHandler.buttonClicked())
                    navigate(AdminHomeFragmentDirections.actionAdminNavigationHomeToNotificationMasterFragment2())
            }
            llPostServiceFollowUp.setOnClickListener {
                if(ButtonClickHandler.buttonClicked())
                    navigate(AdminHomeFragmentDirections.actionAdminNavigationHomeToPostServiceFollowUpFragment2())
            }
            llCarHealthReport.setOnClickListener {
                if(ButtonClickHandler.buttonClicked())
                    navigate(AdminHomeFragmentDirections.actionAdminNavigationHomeToCarHealthReportFragment2())
            }
            llInvoices.setOnClickListener {
                if(ButtonClickHandler.buttonClicked())
                    navigate(AdminHomeFragmentDirections.actionAdminNavigationHomeToInvoicesUploadFragment2())
            }
            llEnquiryAndComplaints.setOnClickListener {
                if(ButtonClickHandler.buttonClicked())
                    navigate(AdminHomeFragmentDirections.actionAdminNavigationHomeToEnquiryAndComlaintFragment2())
            }
            llBeforeAfter.setOnClickListener {
                if(ButtonClickHandler.buttonClicked())
                    navigate(AdminHomeFragmentDirections.actionAdminNavigationHomeToBeforeAfterImagesUploadFragment2())
            }
            llServiceLogHistory.setOnClickListener {
                if(ButtonClickHandler.buttonClicked())
                    navigate(AdminHomeFragmentDirections.actionAdminNavigationHomeToAdminNavigationServiceLogs())
            }
            llStatistics.setOnClickListener {
                if(ButtonClickHandler.buttonClicked())
                    navigate(AdminHomeFragmentDirections.actionAdminNavigationHomeToAdminNavigationStatistics())
            }
            llAllCustomers.setOnClickListener {
                if(ButtonClickHandler.buttonClicked())
                    navigate(AdminHomeFragmentDirections.actionAdminNavigationHomeToAllCustomersFragment())
            }
            llAllServiceAdvisors.setOnClickListener {
                if(ButtonClickHandler.buttonClicked())
                    navigate(AdminHomeFragmentDirections.actionAdminNavigationHomeToAllServiceAdvisorsFragment())
            }
            llAdBanner.setOnClickListener {
                if(ButtonClickHandler.buttonClicked())
                    navigate(AdminHomeFragmentDirections.actionAdminNavigationHomeToAdBannerFragment())
            }
        }
    }

}