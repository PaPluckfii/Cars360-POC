package com.sumeet.cars360.ui.admin.master_service_log.form_screens.part_1_customer_and_car_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.sumeet.cars360.R
import com.sumeet.cars360.data.local.room.model.CarEntity
import com.sumeet.cars360.data.remote.form_data.ServiceLogFormData
import com.sumeet.cars360.databinding.FragmentServiceLogMasterCarDetailsBinding
import com.sumeet.cars360.ui.admin.master_service_log.ServiceLogMasterViewModel
import com.sumeet.cars360.util.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ServiceLogMasterCarDetailsFragment : Fragment(),
    CarEntityRecyclerAdapter.CarEntityItemClickListener {

    private lateinit var binding: FragmentServiceLogMasterCarDetailsBinding
    private val viewModel: ServiceLogMasterViewModel by activityViewModels()
    private val args: ServiceLogMasterCarDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentServiceLogMasterCarDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        viewModel.getAllCarsByCustomerId(args.userId).observe(viewLifecycleOwner) { cars ->
            if (cars.isNullOrEmpty()) {
                binding.tvMessage.apply {
                    text = "No Car has been added for ${args.userName}"
                    ViewVisibilityUtil.visible(this)
                }
            } else {
                binding.tvMessage.text = "Cars for customer : ${args.userName}"
                binding.carsRecyclerView.apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter =
                        CarEntityRecyclerAdapter(cars, this@ServiceLogMasterCarDetailsFragment)
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fabAdd.setOnClickListener {
            if (ButtonClickHandler.buttonClicked()) {
                navigate(
                    ServiceLogMasterCarDetailsFragmentDirections
                        .actionServiceLogMasterCarDetailsFragmentToServiceLogMasterCarSelectBottomSheet(
                            args.userId
                        )
                )
            }
        }
    }

    override fun onCarItemClicked(carEntity: CarEntity) {
        showDialogBox(
            requireActivity(),
            "Car For New Service Log",
            "Are you sure you want to continue with this car?",
            ContextCompat.getDrawable(requireContext(), R.drawable.ic_car_primary_red),
            "Yes",
            "No",
        ) {
            navigate(
                ServiceLogMasterCarDetailsFragmentDirections
                    .actionServiceLogMasterCarDetailsFragmentToServiceLogMasterPicturesFragment(
                        ServiceLogFormData(carId = carEntity.carId)
                    )
            )
        }
    }

}