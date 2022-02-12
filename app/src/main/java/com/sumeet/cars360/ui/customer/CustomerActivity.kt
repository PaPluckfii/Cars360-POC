package com.sumeet.cars360.ui.customer

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.sumeet.cars360.R
import com.sumeet.cars360.data.local.preferences.ReadPrefs
import com.sumeet.cars360.data.local.preferences.SavePrefs
import com.sumeet.cars360.data.remote.model.user.UserResponse
import com.sumeet.cars360.databinding.ActivityCustomerBinding
import com.sumeet.cars360.ui.customer.util.observeOnce
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CustomerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCustomerBinding
    private val viewModel: CustomerViewModel by viewModels()
    private lateinit var savePrefs: SavePrefs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCustomerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        savePrefs = SavePrefs(this)
        setUpBottomNav()
        getAndSetUserData()
    }

    private fun getAndSetUserData() {
        viewModel.getCustomerByUserId("11")
        viewModel.customerDetails.observeOnce(this, Observer {
            it?.let {
                    setUserData(it.userResponse!![0])
            }
        })
    }

    private fun setUserData(userResponse: UserResponse) {
        savePrefs.saveUserName(userResponse.name.toString())
        savePrefs.saveUserEmail(userResponse.email.toString())
        savePrefs.saveUserAddress(userResponse.address.toString())
        savePrefs.saveUserCity(userResponse.city.toString())
        savePrefs.saveUserCountry(userResponse.country.toString())
        savePrefs.saveUserState(userResponse.state.toString())
        savePrefs.saveUserPostalCode(userResponse.postalCode.toString())
        savePrefs.saveUserDob(userResponse.dOB.toString())
        savePrefs.saveUserGstin(userResponse.gSTIN.toString())
        savePrefs.saveProfileImage(userResponse.profileImage.toString())
        savePrefs.saveUserDom(userResponse.dOM.toString())

    }


    private fun setUpBottomNav() {
        val navController =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_customer)
                ?.findNavController()

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_explore,
                R.id.navigation_help,
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