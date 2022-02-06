package com.sumeet.cars360.ui.onboarding.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sumeet.cars360.R
import com.sumeet.cars360.databinding.FragmentIntroBinding
import com.sumeet.cars360.util.ButtonClickHandler
import com.sumeet.cars360.util.navigate
import dagger.hilt.android.AndroidEntryPoint
import com.yqritc.scalablevideoview.ScalableType
import com.yqritc.scalablevideoview.ScalableVideoView
import java.io.IOException


@AndroidEntryPoint
class IntroFragment : Fragment() {

    private lateinit var binding: FragmentIntroBinding
    private lateinit var mBackgroundVideo: ScalableVideoView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentIntroBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBackgroundVideo = binding.videoView

        try {
            mBackgroundVideo.setRawData(R.raw.intro_screen_video)
            mBackgroundVideo.setScalableType(ScalableType.CENTER_CROP)
            mBackgroundVideo.isLooping = true
            mBackgroundVideo.prepare { mp -> mBackgroundVideo.start() }
        } catch (e: IOException) {
            e.printStackTrace()
            //ignore
        }

        binding.btnNext.setOnClickListener {
            if(ButtonClickHandler.buttonClicked())
                navigate(IntroFragmentDirections.actionIntroFragmentToCustomerLoginFragment())
        }

    }

}