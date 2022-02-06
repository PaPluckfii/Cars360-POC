package com.sumeet.cars360.ui.staff

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.sumeet.cars360.R
import com.sumeet.cars360.databinding.ActivityStaffBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StaffActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStaffBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStaffBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        setUpBottomNav()

    }

    private fun setUpBottomNav() {
        val navController =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_staff)
                ?.findNavController()

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_explore,
                R.id.navigation_service_logs,
                R.id.navigation_profile
            )
        )
        setupActionBarWithNavController(navController!!, appBarConfiguration)
        binding.navView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.navigation_explore -> {
                    supportActionBar?.hide()
                }
                else -> {
                    supportActionBar?.show()
                }
            }
        }

    }

}