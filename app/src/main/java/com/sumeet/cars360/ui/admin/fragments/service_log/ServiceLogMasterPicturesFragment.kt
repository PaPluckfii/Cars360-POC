package com.sumeet.cars360.ui.admin.fragments.service_log

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.sumeet.cars360.databinding.FragmentServiceLogMasterPicturesBinding
import com.sumeet.cars360.ui.admin.util.CurrentPics
import com.sumeet.cars360.util.ButtonClickHandler
import com.sumeet.cars360.util.navigate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ServiceLogMasterPicturesFragment : Fragment() {

    private lateinit var binding: FragmentServiceLogMasterPicturesBinding
    private val viewModel: ServiceLogMasterViewModel by activityViewModels()

    private var areAllImagesCaptured = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        CurrentPics.picturesDTO.apply {
            frontPic = null
            leftPic = null
            rightPic = null
            backPic = null
        }
        binding = FragmentServiceLogMasterPicturesBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleListeners()
    }

    override fun onResume() {
        super.onResume()
        if(CurrentPics.picturesDTO.frontPic != null &&
            CurrentPics.picturesDTO.frontPic != null &&
            CurrentPics.picturesDTO.frontPic != null &&
            CurrentPics.picturesDTO.frontPic != null &&
                CurrentPics.picturesDTO.odometer != null){
            areAllImagesCaptured = true
            binding.btnOpenCamera.text = "Discard And Capture Images Again"
        }
//        if(CurrentPics.picturesDTO.frontPic != null) {
//            isFrontCaptured = true
//            binding.tvFrontView.apply {
//                setTextColor(ContextCompat.getColor(requireContext(),R.color.primaryRed))
//                setBackgroundResource(R.drawable.red_border_box)
//            }
//        }
//        if(CurrentPics.picturesDTO.leftPic != null) {
//            isLeftCaptured = true
//            binding.tvLeftView.apply {
//                setTextColor(ContextCompat.getColor(requireContext(),R.color.primaryRed))
//                setBackgroundResource(R.drawable.red_border_box)
//            }
//        }
//        if(CurrentPics.picturesDTO.rightPic != null) {
//            isRightCaptured = true
//            binding.tvRightView.apply {
//                setTextColor(ContextCompat.getColor(requireContext(),R.color.primaryRed))
//                setBackgroundResource(R.drawable.red_border_box)
//            }
//        }
//        if(CurrentPics.picturesDTO.backPic != null) {
//            isBackCaptured = true
//            binding.tvBackView.apply {
//                setTextColor(ContextCompat.getColor(requireContext(),R.color.primaryRed))
//                setBackgroundResource(R.drawable.red_border_box)
//            }
//        }
    }

    private fun handleListeners() {
        binding.apply {
//            btnFront.setOnClickListener {
//                if(ButtonClickHandler.buttonClicked())
//                    startActivity(Intent(activity,ServiceLogMasterCameraActivity::class.java))
//            }
//            btnLeft.setOnClickListener {
//                if(ButtonClickHandler.buttonClicked()) {
//                    startActivity(
//                        Intent(
//                            activity,
//                            CameraActivity::class.java
//                        ).apply { putExtra("INDEX", 2) })
//                }
//            }
//            btnRight.setOnClickListener {
//                if(ButtonClickHandler.buttonClicked())
//                    startActivity(Intent(activity,CameraActivity::class.java).apply { putExtra("INDEX", 3) })
//            }
//            btnBack.setOnClickListener {
//                if(ButtonClickHandler.buttonClicked())
//                    startActivity(Intent(activity,CameraActivity::class.java).apply { putExtra("INDEX", 4) })
//            }
            btnNext.setOnClickListener {
                if(ButtonClickHandler.buttonClicked() && areAllImagesCaptured) {
//                    viewModel.apply {
//                        frontPic = CurrentPics.picturesDTO.frontPic
//                        leftPic = CurrentPics.picturesDTO.leftPic
//                        rightPic = CurrentPics.picturesDTO.rightPic
//                        backPic = CurrentPics.picturesDTO.backPic
//                    }
                    navigate(ServiceLogMasterPicturesFragmentDirections.actionServiceLogMasterPicturesFragmentToServiceLogMasterAccessoriesFragment())
                }
                else{
                    Toast.makeText(context,"All Images are not captured, please capture them again",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
