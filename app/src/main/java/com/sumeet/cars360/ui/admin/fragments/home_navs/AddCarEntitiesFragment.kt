package com.sumeet.cars360.ui.admin.fragments.home_navs

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
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.sumeet.cars360.R
import com.sumeet.cars360.data.local.preferences.ReadPrefs
import com.sumeet.cars360.databinding.FragmentAddCarEntitiesBinding
import com.sumeet.cars360.ui.admin.AdminViewModel
import com.sumeet.cars360.ui.admin.fragments.service_log.ServiceLogDetailsFragmentArgs
import com.sumeet.cars360.util.ButtonClickHandler
import com.sumeet.cars360.util.Resource
import com.sumeet.cars360.util.navigate
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.lang.Exception

@AndroidEntryPoint
class AddCarEntitiesFragment : Fragment() {

    private lateinit var binding: FragmentAddCarEntitiesBinding
    private val viewModel: AdminViewModel by activityViewModels()
    private val args: AddCarEntitiesFragmentArgs by navArgs()

    private var type = 0

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
    ): View {
        binding = FragmentAddCarEntitiesBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        type = args.type

        if (type == 1)
            binding.tilName.hint = "Enter Model Name Here"

        handleListeners()
    }

    private fun handleListeners() {
        binding.apply {
            clImage.setOnClickListener{
                chooseProfilePic()
            }
            btnUpload.setOnClickListener {
                if(ButtonClickHandler.buttonClicked() && userCaptureImage != null){
                    if(type == 0){
                        viewModel.insertNewCarBrand(binding.etName.text.toString(),userCaptureImage!!)

                        viewModel.insertOperation.observe(viewLifecycleOwner,{
                            when(it){
                                is Resource.Loading -> {}
                                is Resource.Error -> {}
                                is Resource.Success -> {
                                    Toast.makeText(context,"Brand Added",Toast.LENGTH_SHORT).show()
                                    navigate(AddCarEntitiesFragmentDirections.actionAddCarEntitiesFragmentToAdminNavigationHome())
                                }
                            }
                        })

                    }
                    else{
                        viewModel.insertNewCarModel(binding.etName.text.toString(),userCaptureImage!!)

                        viewModel.insertOperation.observe(viewLifecycleOwner,{
                            when(it){
                                is Resource.Loading -> {}
                                is Resource.Error -> {}
                                is Resource.Success -> {
                                    Toast.makeText(context,"Model Added",Toast.LENGTH_SHORT).show()
                                    navigate(AddCarEntitiesFragmentDirections.actionAddCarEntitiesFragmentToAdminNavigationHome())
                                }
                            }
                        })
                    }
                }
                else{
                    Toast.makeText(context,"Please Select An Image",Toast.LENGTH_SHORT).show()
                }
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