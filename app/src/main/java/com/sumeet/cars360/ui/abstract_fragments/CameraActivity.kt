package com.sumeet.cars360.ui.abstract_fragments

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.sumeet.cars360.data.local.preferences.ReadPrefs
import com.sumeet.cars360.databinding.ActivityCameraBinding
import com.sumeet.cars360.ui.admin.util.CurrentPics
import com.sumeet.cars360.util.ButtonClickHandler
import java.io.File

/**
 * Constants
 */
private const val REQUEST_CODE_PERMISSIONS = 0
private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
private const val TIMER_FLAG = 500L

/**
 * This is the camera activity to capture pictures.
 */
class CameraActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCameraBinding

    private lateinit var camera: Camera
    private lateinit var preview: Preview
    private lateinit var imageCapture: ImageCapture
    private var imageIndex: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.root.postDelayed(
            Runnable { hideUI() },
            TIMER_FLAG
        )
        imageIndex = intent.getIntExtra("INDEX",0)
        checkCameraPermission()
        handleClicks()
    }

    /**
     * Function to take picture
     */
    private fun takePhoto() {
        val photoFile = File(
            externalMediaDirs.firstOrNull(),
            "userId_${ReadPrefs(this).readUserId()}_${imageIndex}_${System.currentTimeMillis()}.png"
        )
        val output = ImageCapture.OutputFileOptions.Builder(photoFile).build()
        imageCapture.takePicture(
            output,
            ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    when(imageIndex){
                        1 -> CurrentPics.picturesDTO.frontPic = photoFile
                        2 -> CurrentPics.picturesDTO.leftPic = photoFile
                        3 -> CurrentPics.picturesDTO.rightPic = photoFile
                        4 -> CurrentPics.picturesDTO.backPic = photoFile
                    }
                    finish()
                }
                override fun onError(exception: ImageCaptureException) {
                    Toast.makeText(baseContext, "Sorry, Please try again", Toast.LENGTH_SHORT)
                        .show()
                }

            }
        )
    }

    private fun checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            == PackageManager.PERMISSION_GRANTED
        ) {
            startCamera()
        } else {
            ActivityCompat.requestPermissions(
                this,
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
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            == PackageManager.PERMISSION_GRANTED
        ) {
            startCamera()
        } else {
            Toast.makeText(this, "Camera Permission Needed", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Function to hide SystemUI on regular interval of time.
     */
    private fun hideUI() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, binding.root).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }

    /**
     * Function to start the camera
     */
    private fun startCamera() {
        val cameraProviderListener = ProcessCameraProvider.getInstance(this)
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
                    CameraSelector.DEFAULT_BACK_CAMERA,
                    preview,
                    imageCapture
                )
            },
            ContextCompat.getMainExecutor(this)
        )
    }

    /**
     * Function to handle clicks
     */
    private fun handleClicks() {
        binding.btnCapture.setOnClickListener {
            if (ButtonClickHandler.buttonClicked())
                takePhoto()
        }
        binding.btnBack.setOnClickListener {
            if (ButtonClickHandler.buttonClicked())
                finish()
        }
    }

}