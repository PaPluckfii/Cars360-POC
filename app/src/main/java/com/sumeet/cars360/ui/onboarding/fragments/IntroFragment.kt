package com.sumeet.cars360.ui.onboarding.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sumeet.cars360.R
import com.sumeet.cars360.data.local.preferences.CustomerLoginType
import com.sumeet.cars360.data.local.preferences.SavePrefs
import com.sumeet.cars360.databinding.FragmentIntroBinding
import com.sumeet.cars360.ui.customer.CustomerActivity
import com.sumeet.cars360.util.ButtonClickHandler
import com.sumeet.cars360.util.navigate
import com.yqritc.scalablevideoview.ScalableType
import com.yqritc.scalablevideoview.ScalableVideoView
import dagger.hilt.android.AndroidEntryPoint
import java.io.IOException


@AndroidEntryPoint
class IntroFragment : Fragment() {

    private lateinit var binding: FragmentIntroBinding
    private lateinit var mBackgroundVideo: ScalableVideoView
    private lateinit var savePrefs: SavePrefs

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        savePrefs = SavePrefs(requireContext())
        binding = FragmentIntroBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBackgroundVideo = binding.videoView

        try {
            mBackgroundVideo.setRawData(R.raw.intro_screen_video_potrait)
            mBackgroundVideo.setScalableType(ScalableType.CENTER_CROP)
            mBackgroundVideo.isLooping = true
            mBackgroundVideo.prepare { mp -> mBackgroundVideo.start() }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        binding.apply {
            btnLogin.setOnClickListener {
                if (ButtonClickHandler.buttonClicked())
                    navigate(IntroFragmentDirections.actionIntroFragmentToCustomerLoginFragment())
            }
            btnSkipLogin.setOnClickListener {
                if (ButtonClickHandler.buttonClicked())
                    navigate(IntroFragmentDirections.actionIntroFragmentToNewCarDetailsFragment())
            }
            btnStaff.setOnClickListener {
                if (ButtonClickHandler.buttonClicked())
                    navigate(IntroFragmentDirections.actionIntroFragmentToStaffLoginFragment())
            }
            btnTour.setOnClickListener {
                savePrefs.saveLoginStatus(true)
                savePrefs.saveCustomerLoginType(CustomerLoginType.TakeATour)
                if (ButtonClickHandler.buttonClicked())
                    startActivity(
                        Intent(activity, CustomerActivity::class.java)
                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    ).also { activity?.finish() }
            }
        }

    }

}