package com.sumeet.cars360.ui.admin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.sumeet.cars360.R
import com.sumeet.cars360.databinding.ActivityAdminBinding
import com.sumeet.cars360.util.ViewVisibilityUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AdminActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAdminBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        setUpBottomNav()

    }

    private fun setUpBottomNav() {
        val navController =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_admin)
                ?.findNavController()

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.admin_navigation_home,
                R.id.admin_navigation_service_logs,
                R.id.admin_navigation_statistics
            )
        )
        setupActionBarWithNavController(navController!!, appBarConfiguration)
        binding.navView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.serviceLogMasterFragment2 -> {
                    ViewVisibilityUtil.gone(binding.navView)
                }
                else -> {
                    ViewVisibilityUtil.visible(binding.navView)
                }
            }
        }

    }

}