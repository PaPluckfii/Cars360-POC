package com.sumeet.cars360

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.sumeet.cars360.data.local.preferences.ReadPrefs
import com.sumeet.cars360.data.local.preferences.UserType
import com.sumeet.cars360.ui.customer.CustomerActivity
import com.sumeet.cars360.ui.onboarding.OnBoardingActivity
import com.sumeet.cars360.ui.staff.StaffActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var readPrefs: ReadPrefs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val splashScreen = installSplashScreen()
        checkLoginStatus()

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
                    Intent(this, StaffActivity::class.java)
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