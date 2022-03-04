package com.sumeet.cars360.ui.admin.dump

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.FileUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.sumeet.cars360.R
import com.sumeet.cars360.data.local.preferences.ReadPrefs
import com.sumeet.cars360.data.remote.model.service_logs.ServiceLogResponse
import com.sumeet.cars360.databinding.FragmentCarHealthReportBinding
import com.sumeet.cars360.ui.admin.AdminViewModel
import com.sumeet.cars360.ui.admin.util.OnServiceItemClickListener
import com.sumeet.cars360.ui.admin.util.ServiceLogRecyclerAdapter
import com.sumeet.cars360.util.FormDataResource
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.lang.Exception

class CarHealthReportFragment : Fragment(), OnServiceItemClickListener {

    private lateinit var binding: FragmentCarHealthReportBinding
    private val viewModel: AdminViewModel by activityViewModels()

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
//                        Glide.with(binding.root).load(_uri).into(binding.ivProfilePic)
                    }

                }catch (e: Exception){}
            }
        }
    private var userCaptureImage: File? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = FragmentCarHealthReportBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerData()
    }

    private fun setUpRecyclerData(){
        viewModel.getAllServiceLogsByUserId("11")
        viewModel.allServiceLogs.observe(viewLifecycleOwner) {
            when (it) {
                is FormDataResource.Loading -> {}
                is FormDataResource.Error -> {}
                is FormDataResource.Success -> {
                    binding.carHealthReportRecyclerview.apply {
                        layoutManager = LinearLayoutManager(context)
                        adapter = it.data?.serviceLogResponse?.let { data ->
                            ServiceLogRecyclerAdapter(
                                data,
                                this@CarHealthReportFragment
                            )
                        }
                    }
                }
            }
        }
    }

    override fun onServiceItemClicked(serviceLogResponse: ServiceLogResponse) {
        showDialog(serviceLogResponse)
    }

    private fun showDialog(serviceLogResponse: ServiceLogResponse) {
        val customDialog = Dialog(requireActivity())
        customDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        customDialog.setContentView(R.layout.dialog_upload_document)
        customDialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        val btnUpload = customDialog.findViewById(R.id.btnUpload) as Button
        val uploadBtn = customDialog.findViewById<ImageView>(R.id.uploadHealthReport)

        uploadBtn.setOnClickListener {
            chooseProfilePic()
        }

        btnUpload.setOnClickListener {

        }
        customDialog.show()
    }

    private fun chooseProfilePic() {
        if(isReadPermissionGranted || sdkCheck()) {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "application/pdf"
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