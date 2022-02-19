package com.sumeet.cars360.ui.onboarding.fragments.customer

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.FileUtils
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.sumeet.cars360.data.local.preferences.CustomerLoginType
import com.sumeet.cars360.data.local.preferences.ReadPrefs
import com.sumeet.cars360.data.local.preferences.SavePrefs
import com.sumeet.cars360.data.local.preferences.UserType
import com.sumeet.cars360.databinding.FragmentNewCustomerDetailsBinding
import com.sumeet.cars360.ui.onboarding.fragments.new_customer.NewCustomerDetailsFragmentDirections
import com.sumeet.cars360.ui.onboarding.fragments.new_customer.NewCustomerViewModel
import com.sumeet.cars360.util.ButtonClickHandler
import com.sumeet.cars360.util.Resource
import com.sumeet.cars360.util.ViewVisibilityUtil
import com.sumeet.cars360.util.navigate
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.lang.Exception

@AndroidEntryPoint
class NewCustomerDetailsFromProfile:Fragment() {

    private lateinit var binding: FragmentNewCustomerDetailsBinding
    private val viewModel: NewCustomerViewModel by activityViewModels()

    private lateinit var readPrefs: ReadPrefs

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
                    val filePath: String = "${ReadPrefs(requireContext()).readUserMobileNumber()}_profile_pic.jpg"
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
    ): View {
        readPrefs = ReadPrefs(requireContext())
        binding = FragmentNewCustomerDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleListeners()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        findNavController().popBackStack()
        return true
    }

    private fun handleListeners() {
        binding.apply {
            btnCompleteProfile.setOnClickListener {
                if (ButtonClickHandler.buttonClicked() && checkDataValidity()) {

                    viewModel.apply {
                        name = etName.text.toString()
                        mobileNo = FirebaseAuth.getInstance().currentUser?.phoneNumber.toString()
                        email = etEmail.text.toString()
                        firebaseId = readPrefs.readFirebaseId().toString()
                        profileImage = userCaptureImage
                    }
                    navigate(NewCustomerDetailsFromProfileDirections.actionNewCustomerFromProfileToNewCustomerAdditionalDetailsFromProfile())
                }
            }
            btnSignMeIn.setOnClickListener {
                if (ButtonClickHandler.buttonClicked() && checkDataValidity()) {

                    viewModel.apply {
                        name = etName.text.toString()
                        mobileNo = FirebaseAuth.getInstance().currentUser?.phoneNumber.toString()
                        email = etEmail.text.toString()
                        firebaseId = FirebaseAuth.getInstance().uid.toString()
                        profileImage = userCaptureImage
                    }
                    Toast.makeText(context, viewModel.mobileNo, Toast.LENGTH_SHORT).show()
                    viewModel.insertNewUserData()

                    viewModel.insertOperation.observe(viewLifecycleOwner) {
                        when (it) {
                            is Resource.Loading -> ViewVisibilityUtil.visibilityExchanger(
                                progressBar,
                                llNewCustomerDetails
                            )
                            is Resource.Error -> {
                                ViewVisibilityUtil.visibilityExchanger(
                                    llNewCustomerDetails,
                                    progressBar
                                )
                                Toast.makeText(context, "Oops! ${viewModel.mobileNo} ${it.message}", Toast.LENGTH_LONG)
                                    .show()
                            }
                            is Resource.Success -> {
                                SavePrefs(requireContext()).apply {
                                    saveLoginStatus(true)
                                    saveUserType(UserType.Customer)
                                    saveCustomerLoginType(CustomerLoginType.LoggedIn)
                                    if (it.data != "")
                                        it.data?.let { id -> saveUserId(id) }
                                }
                                navigate(NewCustomerDetailsFromProfileDirections.actionNewCustomerFromProfileToNavigationProfile())
                            }
                        }
                    }
                }
            }

            etName.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    tilName.error = null
                }

                override fun afterTextChanged(s: Editable) {}
            })

            etEmail.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    tilEmailId.error = null
                }

                override fun afterTextChanged(s: Editable) {}
            })

            ivEditImage.setOnClickListener {
                chooseProfilePic()
            }
            ivProfilePic.setOnClickListener {
                chooseProfilePic()
            }
        }
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
            if(isReadPermissionGranted){
                val intent = Intent(Intent.ACTION_GET_CONTENT)
                intent.type = "image/*"
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true)
                profilePicResultLauncher.launch(intent)
            }
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

    private fun checkDataValidity(): Boolean {
        var validity = true
        if (binding.etName.text.isNullOrEmpty()) {
            binding.tilName.error = "Name Field Cannot be Empty"
            validity = false
        }
        if (binding.etEmail.text.isNullOrEmpty()) {
            binding.tilEmailId.error = "Email Cannot be Empty"
            validity = false
        }
        return validity
    }

}