package com.sumeet.cars360

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sumeet.cars360.data.local.preferences.ReadPrefs
import com.sumeet.cars360.data.wrapper.UserType
import com.sumeet.cars360.databinding.ActivityMainBinding
import com.sumeet.cars360.ui.admin.AdminActivity
import com.sumeet.cars360.ui.customer.CustomerActivity
import com.sumeet.cars360.ui.onboarding.OnBoardingActivity
import com.sumeet.cars360.util.SystemUIHandler
import com.yqritc.scalablevideoview.ScalableType
import dagger.hilt.android.AndroidEntryPoint
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var readPrefs: ReadPrefs
    private lateinit var systemUIHandler: SystemUIHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        systemUIHandler = SystemUIHandler(window, binding.root)
        playAnimationAfterSplashScreen()

    }

    private fun playAnimationAfterSplashScreen() {
        try {
            systemUIHandler.hideSystemUI()
            binding.intoAnimationPlayer.apply {
                setRawData(R.raw.cars_360_into_animation)
                setScalableType(ScalableType.CENTER_CROP)
                isLooping = false
                prepare { start() }
                setOnCompletionListener {
                    checkLoginStatus()
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
            checkLoginStatus()
        }
    }

    private fun checkLoginStatus() {
        readPrefs = ReadPrefs(this)
        if (readPrefs.readLoginStatus()) {
            when (readPrefs.readUserType()) {
                UserType.Customer -> startActivity(
                    Intent(this, CustomerActivity::class.java)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                ).also {
                    finish()
                }
                else -> startActivity(
                    Intent(this, AdminActivity::class.java)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                ).also {
                    finish()
                }
            }
        } else {
            startActivity(
                Intent(this, OnBoardingActivity::class.java)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            ).also {
                finish()
            }
        }
    }
}