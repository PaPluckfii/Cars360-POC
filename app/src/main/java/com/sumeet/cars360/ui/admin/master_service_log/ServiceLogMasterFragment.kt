package com.sumeet.cars360.ui.admin.master_service_log

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.sumeet.cars360.R
import com.sumeet.cars360.databinding.FragmentServiceLogMasterBinding
import com.sumeet.cars360.util.FormDataResource
import com.sumeet.cars360.util.ViewVisibilityUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ServiceLogMasterFragment : Fragment() {

    private lateinit var binding: FragmentServiceLogMasterBinding
    private val viewModel: ServiceLogMasterViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentServiceLogMasterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpNavigation()
        setUpObserver()
        setHasOptionsMenu(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        findNavController().popBackStack()
        return true
    }

    private fun setUpObserver() {
        viewModel.insertOperation.observe(viewLifecycleOwner) {
            when (it) {
                is FormDataResource.Success -> {
                    findNavController().navigate(R.id.action_serviceLogMasterFragment2_to_admin_navigation_home2)
                }
                else -> {}
            }
        }
    }

    private fun setUpNavigation() {
        val navController =
            childFragmentManager.findFragmentById(R.id.nav_host_service_log_master)
                ?.findNavController()

        navController?.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.serviceLogMasterCustomerDetailsFragment -> {}
                R.id.serviceLogMasterPicturesFragment -> {
                    binding.customerTick
                        .setImageDrawable(
                            ContextCompat.getDrawable(
                                requireContext(),
                                R.drawable.ic_correct
                            )
                        )
                    binding.picturesTick
                        .setImageDrawable(
                            ContextCompat.getDrawable(
                                requireContext(),
                                R.drawable.ic_down_arrow
                            )
                        )
                }
                R.id.serviceLogMasterAccessoriesFragment -> {
                    binding.picturesTick
                        .setImageDrawable(
                            ContextCompat.getDrawable(
                                requireContext(),
                                R.drawable.ic_correct
                            )
                        )
                    binding.accessoriesTick
                        .setImageDrawable(
                            ContextCompat.getDrawable(
                                requireContext(),
                                R.drawable.ic_down_arrow
                            )
                        )
                }
                R.id.serviceLogMasterTypesOfServicesFragment -> {
                    binding.accessoriesTick
                        .setImageDrawable(
                            ContextCompat.getDrawable(
                                requireContext(),
                                R.drawable.ic_correct
                            )
                        )
                    binding.typeOfServicesTick
                        .setImageDrawable(
                            ContextCompat.getDrawable(
                                requireContext(),
                                R.drawable.ic_down_arrow
                            )
                        )
                }
                R.id.serviceLogMasterRequestedRepairsFragment -> {
                    binding.typeOfServicesTick
                        .setImageDrawable(
                            ContextCompat.getDrawable(
                                requireContext(),
                                R.drawable.ic_correct
                            )
                        )
                    binding.requestedRepairsTick
                        .setImageDrawable(
                            ContextCompat.getDrawable(
                                requireContext(),
                                R.drawable.ic_down_arrow
                            )
                        )
                }
                else -> {
                    ViewVisibilityUtil.gone(binding.clTicks)
                }
            }
        }
    }

}