package com.sumeet.cars360.ui.admin.master_service_advisor

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.FileUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.sumeet.cars360.R
import com.sumeet.cars360.data.local.preferences.ReadPrefs
import com.sumeet.cars360.databinding.FragmentServiceAdvisorMasterBinding
import com.sumeet.cars360.ui.admin.AdminViewModel
import com.sumeet.cars360.util.ButtonClickHandler
import com.sumeet.cars360.util.FormDataResource
import com.sumeet.cars360.util.ViewVisibilityUtil
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.lang.Exception

@AndroidEntryPoint
class ServiceAdvisorMasterFragment : Fragment() {

    private lateinit var binding: FragmentServiceAdvisorMasterBinding
    private val viewModel: AdminViewModel by activityViewModels()

    private var isDatePickerVisible = false
    private var isReadPermissionGranted = false
    private var isWritePermissionGranted = false
    private var permissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()){ permissions ->
            isReadPermissionGranted = permissions[android.Manifest.permission.READ_EXTERNAL_STORAGE] ?: isReadPermissionGranted
        }

    private val profilePicResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                val _uri: Uri? = data?.data
                try {
                    var filePath: String = "${ReadPrefs(requireContext()).readUserMobileNumber()}_profile_pic.jpg"
                    Log.d("FILEPATH", "URI = $_uri")
                    if (_uri != null && "content" == _uri.scheme) {
                        val inputStream = requireActivity().contentResolver.openInputStream(_uri)
                            ?: throw IOException("Unable to obtain input stream from URI")

                        userCaptureImage = File.createTempFile("Cars360",filePath)
                        val outputStream = FileOutputStream(userCaptureImage)

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                            FileUtils.copy(inputStream, outputStream)
                        }
                        Log.d("FILEPATH", "Chosen path = $filePath")
                        Glide.with(binding.root).load(_uri).into(binding.ivProfilePic)
                    }

                }catch (e: Exception){}
            }
        }
    private var userCaptureImage: File? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentServiceAdvisorMasterBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.resetUserDataInViewModel()
        handleListeners()
        setHasOptionsMenu(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        findNavController().popBackStack()
        return true
    }

    private fun handleListeners() {
        binding.apply {
            dobDatePicker.maxDate = System.currentTimeMillis() - 1000

            btnSelectDOB.setOnClickListener {
                if(ButtonClickHandler.buttonClicked()) {
                    binding.btnSelectDOB.background = ContextCompat.getDrawable(requireContext(),R.drawable.button_transparent_gradient)
                    isDatePickerVisible = true
                    ViewVisibilityUtil.visible(llDatePickerView)

                    if(viewModel.dob == "")
                        dobDatePicker.updateDate(1990,1,1)
                    else {
                        val dates = viewModel.dob.split("-")
                        dobDatePicker.updateDate(dates[0].toInt(), dates[1].toInt(),dates[2].toInt())
                    }

                    btnSetDate.setOnClickListener {
                        val year = dobDatePicker.year
                        val month = dobDatePicker.month
                        val date = dobDatePicker.dayOfMonth
                        viewModel.dob = "$year-$month-$date"
                        btnSelectDOB.text = viewModel.dob
                        isDatePickerVisible = false
                        ViewVisibilityUtil.gone(llDatePickerView)

                    }
                    llDatePickerView.setOnClickListener {
                        isDatePickerVisible = false
                        ViewVisibilityUtil.gone(llDatePickerView)
                    }
                }
            }

            btnSelectDOM.setOnClickListener {
                if(ButtonClickHandler.buttonClicked()) {
                    isDatePickerVisible = true
                    ViewVisibilityUtil.visible(llDatePickerView)

                    if(viewModel.dom == "")
                        dobDatePicker.updateDate(2010,1,1)
                    else {
                        val dates = viewModel.dom.split("-")
                        dobDatePicker.updateDate(dates[0].toInt(), dates[1].toInt(),dates[2].toInt())
                    }

                    btnSetDate.setOnClickListener {
                        val year = dobDatePicker.year
                        val month = dobDatePicker.month
                        val date = dobDatePicker.dayOfMonth
                        viewModel.dom = "$year-$month-$date"
                        btnSelectDOM.text = viewModel.dom
                        isDatePickerVisible = false
                        ViewVisibilityUtil.gone(llDatePickerView)

                    }
                    llDatePickerView.setOnClickListener {
                        isDatePickerVisible = false
                        ViewVisibilityUtil.gone(llDatePickerView)
                    }
                }
            }
            clProfilePic.setOnClickListener {
                chooseProfilePic()
            }
            btnAddServiceAdvisor.setOnClickListener {
                if(ButtonClickHandler.buttonClicked()) {
                    if(checkDataValidity()){
                        viewModel.insertNewUserData()

                        viewModel.insertOperation.observe(viewLifecycleOwner) {
                            when (it) {
                                is FormDataResource.Loading -> {
                                    ViewVisibilityUtil.visibilityExchanger(
                                        binding.progressBar,
                                        binding.nestedScrollView
                                    )
                                }
                                is FormDataResource.Error -> {
                                    ViewVisibilityUtil.gone(binding.progressBar)
                                    Toast.makeText(
                                        context,
                                        "Error: ${it.message}",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                                is FormDataResource.Success -> {
                                    Toast.makeText(
                                        context, "Added Customer with id: ${it.data}",
                                        Toast.LENGTH_SHORT
                                    ).show()
//                                    navigate(CustomerMasterFragmentDirections.actionCustomerMasterFragment2ToAdminNavigationHome())
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun checkDataValidity(): Boolean {
        var validity = true
        if (binding.etAddress1.text.isNullOrEmpty()) {
            binding.tilAddress1.error = "Address Field Cannot be Empty"
            validity = false
        }
        if (binding.etAddressState.text.isNullOrEmpty()) {
            binding.tilAddressState.error = "Email Cannot be Empty"
            validity = false
        }
        if (binding.etAddressCity.text.isNullOrEmpty()) {
            binding.tilAddressCity.error = "Email Cannot be Empty"
            validity = false
        }
        if (binding.etAddressPinCode.text.toString().length != 6) {
            binding.tilAddressPinCode.error = "Email Cannot be Empty"
            validity = false
        }
        if(viewModel.dob == ""){
            binding.btnSelectDOB.background = ContextCompat.getDrawable(requireContext(),R.color.secondaryRed)
            validity = false
        }
        if(viewModel.dom == ""){
            binding.btnSelectDOM.background = ContextCompat.getDrawable(requireContext(),R.color.secondaryRed)
            validity = false
        }
        return validity
    }

    private fun chooseProfilePic() {
        if(isReadPermissionGranted || sdkCheck()) {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true)
            profilePicResultLauncher.launch(intent)
        }
        else{
            requestPermission()
        }
    }

    private fun sdkCheck() : Boolean{

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
            return true
        }

        return false

    }

    private fun requestPermission(){

        val isReadPermission = ContextCompat.checkSelfPermission(
            requireContext(),
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED

        val isWritePermission = ContextCompat.checkSelfPermission(
            requireContext(),
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED

        val minSdkLevel = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q

        isReadPermissionGranted = isReadPermission
        isWritePermissionGranted = isWritePermission || minSdkLevel

        val permissionRequest = mutableListOf<String>()
        if (!isWritePermissionGranted){

            permissionRequest.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)

        }
        if (!isReadPermissionGranted){

            permissionRequest.add(android.Manifest.permission.READ_EXTERNAL_STORAGE)

        }

        if (permissionRequest.isNotEmpty())
        {
            permissionLauncher.launch(permissionRequest.toTypedArray())
        }

    }

}