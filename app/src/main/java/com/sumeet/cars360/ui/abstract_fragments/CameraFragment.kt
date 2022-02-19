package com.sumeet.cars360.ui.abstract_fragments

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.sumeet.cars360.data.local.preferences.ReadPrefs
import com.sumeet.cars360.databinding.FragmentCameraBinding
import com.sumeet.cars360.ui.admin.fragments.service_log.ServiceLogMasterViewModel
import com.sumeet.cars360.util.navigate
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

/**
 * Constants
 */
private const val REQUEST_CODE_PERMISSIONS = 0
private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
private const val TIMER_FLAG = 500L

@AndroidEntryPoint
class CameraFragment : Fragment() {

    private lateinit var binding: FragmentCameraBinding
    private val viewModel: ServiceLogMasterViewModel by activityViewModels()
    private val args: CameraFragmentArgs by navArgs()

    private lateinit var camera: Camera
    private lateinit var preview: Preview
    private lateinit var imageCapture: ImageCapture
    private var cameraSelector: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCameraBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //hideUI()
        checkCameraPermission()
        handleClicks()
    }

    private fun checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
            == PackageManager.PERMISSION_GRANTED
        ) {
            startCamera()
        } else {
            ActivityCompat.requestPermissions(
                requireActivity(),
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
            == PackageManager.PERMISSION_GRANTED
        ) {
            startCamera()
        } else {
            Toast.makeText(context, "Camera Permission Needed", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Function to hide SystemUI on regular interval of time.
     */
    private fun hideUI() {
        (activity as AppCompatActivity).supportActionBar?.hide()
        WindowCompat.setDecorFitsSystemWindows(requireActivity().window, true)
        WindowInsetsControllerCompat(requireActivity().window, binding.root).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }

    /**
     * Function to start the camera
     */
    private fun startCamera() {
        val cameraProviderListener = ProcessCameraProvider.getInstance(requireContext())
        cameraProviderListener.addListener(
            Runnable {
                val cameraProvider = cameraProviderListener.get()
                preview = Preview.Builder().build().also {
                    it.setSurfaceProvider(binding.cameraPreview.surfaceProvider)
                }
                imageCapture = ImageCapture.Builder().build()

                cameraProvider.unbindAll()

                camera = cameraProvider.bindToLifecycle(
                    this,
                    cameraSelector,
                    preview,
                    imageCapture
                )
            },
            ContextCompat.getMainExecutor(requireContext())
        )
    }

    /**
     * Function to handle clicks
     */
    private fun handleClicks() {
        binding.btnCapture.setOnClickListener {
            takePhoto()
        }
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    /**
     * Function to take picture
     */
    private fun takePhoto() {
        val photoFile = File(
            requireActivity().externalMediaDirs.firstOrNull(),
            "userId_${ReadPrefs(requireContext()).readUserId()}_${args.imageIndex}_${System.currentTimeMillis()}.jpg"   //TODO rename
        )
        val output = ImageCapture.OutputFileOptions.Builder(photoFile).build()
        imageCapture.takePicture(
            output,
            ContextCompat.getMainExecutor(requireContext()),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    when(args.imageIndex){
//                        1 -> viewModel.frontPic = photoFile
//                        2 -> viewModel.leftPic = photoFile
//                        3 -> viewModel.rightPic = photoFile
//                        4 -> viewModel.backPic = photoFile
                    }
                    navigate(CameraFragmentDirections.actionCameraFragmentToServiceLogMasterPicturesFragment())
                }
                override fun onError(exception: ImageCaptureException) {
                    Toast.makeText(context, "Sorry, Please try again", Toast.LENGTH_SHORT)
                        .show()
                }

            }
        )
    }

}