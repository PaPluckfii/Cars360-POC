package com.sumeet.cars360.ui.admin.master_car

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.FileUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sumeet.cars360.data.local.room.model.CarModelEntity
import com.sumeet.cars360.databinding.FragmentAddCarEntitiesBinding
import com.sumeet.cars360.util.*
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

@AndroidEntryPoint
class AddCarEntitiesFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentAddCarEntitiesBinding
    private val viewModel: CarCompanyMasterViewModel by activityViewModels()
    private val args: AddCarEntitiesFragmentArgs by navArgs()

    private var isReadPermissionGranted = false
    private var isWritePermissionGranted = false
    private var permissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            isReadPermissionGranted = permissions[android.Manifest.permission.READ_EXTERNAL_STORAGE]
                ?: isReadPermissionGranted
        }

    private var userCaptureImage: File? = null
    private val profilePicResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                val _uri: Uri? = data?.data
                try {
                    var filePath: String =
                        "${args.modelEntity.modelName}_${System.currentTimeMillis()}.png"
                    Log.d("FILEPATH", "URI = $_uri")
                    if (_uri != null && "content" == _uri.scheme) {
                        val inputStream = requireActivity().contentResolver.openInputStream(_uri)
                            ?: throw IOException("Unable to obtain input stream from URI")

                        userCaptureImage = File.createTempFile("Cars360", filePath)
                        val outputStream = FileOutputStream(userCaptureImage)

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                            FileUtils.copy(inputStream, outputStream)
                        }
                        Log.d("FILEPATH", "Chosen path = $filePath")
                        Glide.with(binding.root).load(_uri).into(binding.ivProfilePic)
                    }

                } catch (e: Exception) {
                }
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddCarEntitiesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            if (args.modelEntity.modelLogo != null) {
                Glide.with(binding.root).load(args.modelEntity.modelLogo).into(binding.ivProfilePic)
            }
            etName.setText(args.modelEntity.modelName)
        }
        handleListeners()
    }

    private fun handleListeners() {
        binding.apply {
            clImage.setOnClickListener {
                chooseProfilePic()
            }
            btnUpload.setOnClickListener {
//                if(ButtonClickHandler.buttonClicked() && userCaptureImage != null){
//                    if(type == 0){
//                        viewModel.insertNewCarBrand(binding.etName.text.toString(),userCaptureImage!!)
//
//                        viewModel.insertOperation.observe(viewLifecycleOwner) {
//                            when (it) {
//                                is OldResource.Loading -> {}
//                                is OldResource.Error -> {}
//                                is OldResource.Success -> {
//                                    Toast.makeText(context, "Brand Added", Toast.LENGTH_SHORT)
//                                        .show()
//                                    navigate(AddCarEntitiesFragmentDirections.actionAddCarEntitiesFragmentToAdminNavigationHome())
//                                }
//                            }
//                        }
//
//                    }
//                    else{
//                        viewModel.insertNewCarModel(binding.etName.text.toString(),userCaptureImage!!)
//
//                        viewModel.insertOperation.observe(viewLifecycleOwner) {
//                            when (it) {
//                                is OldResource.Loading -> {}
//                                is OldResource.Error -> {}
//                                is OldResource.Success -> {
//                                    Toast.makeText(context, "Model Added", Toast.LENGTH_SHORT)
//                                        .show()
//                                    navigate(AddCarEntitiesFragmentDirections.actionAddCarEntitiesFragmentToAdminNavigationHome())
//                                }
//                            }
//                        }
//                    }
//                }

//                else{
//                    Toast.makeText(context,"Please Select An Image",Toast.LENGTH_SHORT).show()
//                }

                if (ButtonClickHandler.buttonClicked()) {
                    if (userCaptureImage != null) {
                        viewModel.updateCarModel(
                            CarModelEntity(
                                modelId = args.modelEntity.modelId,
                                brandId = args.modelEntity.brandId,
                                modelName = binding.etName.text.toString(),
                                modelLogo = ""
                            ),
                            userCaptureImage!!
                        )

                        viewModel.insertOperation.observe(viewLifecycleOwner) {
                            when(it){
                                is FormDataResource.Loading -> {
                                    ViewVisibilityUtil.visible(binding.progressBar)
                                }
                                is FormDataResource.Success -> {
                                    Toast.makeText(context,it.data,Toast.LENGTH_SHORT).show()
                                    navigate(AddCarEntitiesFragmentDirections.actionAddCarEntitiesFragmentToAdminNavigationHome())
                                }
                                is FormDataResource.Error -> {
                                    Toast.makeText(context,it.message,Toast.LENGTH_SHORT).show()
                                    navigate(AddCarEntitiesFragmentDirections.actionAddCarEntitiesFragmentToAdminNavigationHome())
                                }
                            }
                        }

                    } else {
                        Toast.makeText(context, "Please Select An Image", Toast.LENGTH_SHORT).show()
                    }
                }

            }
        }
    }

    private fun chooseProfilePic() {
        if (isReadPermissionGranted || sdkCheck()) {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true)
            profilePicResultLauncher.launch(intent)
        } else {
            requestPermission()
            if (isReadPermissionGranted) {
                val intent = Intent(Intent.ACTION_GET_CONTENT)
                intent.type = "image/*"
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true)
                profilePicResultLauncher.launch(intent)
            }
        }
    }

    private fun sdkCheck(): Boolean {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            return true
        }

        return false

    }

    private fun requestPermission() {

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
        if (!isWritePermissionGranted) {

            permissionRequest.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)

        }
        if (!isReadPermissionGranted) {

            permissionRequest.add(android.Manifest.permission.READ_EXTERNAL_STORAGE)

        }

        if (permissionRequest.isNotEmpty()) {
            permissionLauncher.launch(permissionRequest.toTypedArray())
        }

    }

}