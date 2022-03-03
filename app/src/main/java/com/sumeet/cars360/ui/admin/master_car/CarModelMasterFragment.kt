package com.sumeet.cars360.ui.admin.master_car

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.sumeet.cars360.data.local.room.model.CarModelEntity
import com.sumeet.cars360.databinding.FragmentCarModelMasterBinding
import com.sumeet.cars360.ui.admin.AdminViewModel
import com.sumeet.cars360.ui.onboarding.customer.car_select.CarModelSelectAdapter
import com.sumeet.cars360.util.navigate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CarModelMasterFragment : Fragment(), CarModelSelectAdapter.CarModelSelectListener {

    private lateinit var binding: FragmentCarModelMasterBinding
    private val adminViewModel: AdminViewModel by activityViewModels()
    private val carCompanyMasterViewModel: CarCompanyMasterViewModel by viewModels()
    private val navArgs: CarModelMasterFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCarModelMasterBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpBrandDetails()
        setUpModelsRecyclerView()
    }

    private fun setUpModelsRecyclerView() {
        adminViewModel.getCarModelByBrandId(navArgs.carBrandId).observe(viewLifecycleOwner) { models ->
            val modelAdapterBrand = CarModelSelectAdapter(
                models as ArrayList<CarModelEntity>,
                this@CarModelMasterFragment
            )
            binding.carModelRecyclerView.adapter = modelAdapterBrand
        }
    }

    private fun setUpBrandDetails() {
        adminViewModel.carBrands.observe(viewLifecycleOwner) { brands ->
            brands.data?.first { it.brandId == navArgs.carBrandId }.apply {
                Glide.with(binding.root).load(this?.brandLogo).into(binding.ivBrandLogo)
                binding.etBrandName.setText(this?.brandName)
            }
        }
    }

    override fun onCarItemSelected(modelEntity: CarModelEntity) {
        navigate(CarModelMasterFragmentDirections.actionCarModelMasterFragmentToAddCarEntitiesFragment(modelEntity))
    }

}