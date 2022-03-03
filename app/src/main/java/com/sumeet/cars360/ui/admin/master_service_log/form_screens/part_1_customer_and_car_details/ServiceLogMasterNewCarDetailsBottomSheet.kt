package com.sumeet.cars360.ui.admin.master_service_log.form_screens.part_1_customer_and_car_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sumeet.cars360.data.local.room.model.CarModelEntity
import com.sumeet.cars360.databinding.FragmentAdminNewCarDetailsBinding
import com.sumeet.cars360.ui.admin.AdminViewModel
import com.sumeet.cars360.ui.onboarding.customer.car_select.CarModelSelectAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ServiceLogMasterNewCarDetailsBottomSheet: BottomSheetDialogFragment(), CarModelSelectAdapter.CarModelSelectListener {

    private lateinit var binding: FragmentAdminNewCarDetailsBinding
    private val adminViewModel: AdminViewModel by activityViewModels()
    private val args: ServiceLogMasterNewCarDetailsBottomSheetArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAdminNewCarDetailsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        populateModelData()
    }

    private fun populateModelData() {
        adminViewModel.getCarModelByBrandId(args.userId).observe(viewLifecycleOwner) { models ->
            val modelAdapterBrand = CarModelSelectAdapter(
                models as ArrayList<CarModelEntity>,
                this@ServiceLogMasterNewCarDetailsBottomSheet
            )
            binding.carModelRecyclerView.adapter = modelAdapterBrand
        }
    }

    override fun onCarItemSelected(modelEntity: CarModelEntity) {
        //TODO("Not yet implemented")
    }

}