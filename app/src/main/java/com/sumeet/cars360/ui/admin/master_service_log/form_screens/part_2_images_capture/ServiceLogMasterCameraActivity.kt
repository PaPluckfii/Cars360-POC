package com.sumeet.cars360.ui.admin.master_service_log.form_screens.part_2_images_capture

import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.otaliastudios.cameraview.CameraListener
import com.otaliastudios.cameraview.CameraView
import com.otaliastudios.cameraview.PictureResult
import com.otaliastudios.cameraview.controls.Preview
import com.sumeet.cars360.R
import com.sumeet.cars360.databinding.ActivityServiceLogMasterCameraBinding
import com.sumeet.cars360.ui.admin.util.CurrentPics
import com.sumeet.cars360.ui.admin.util.CurrentPics.currentImageIndex
import com.sumeet.cars360.util.ViewVisibilityUtil
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class ServiceLogMasterCameraActivity : AppCompatActivity() {

    private lateinit var binding: ActivityServiceLogMasterCameraBinding

    private val camera: CameraView by lazy { findViewById(R.id.serviceLogMasterCamera) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityServiceLogMasterCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)
        hideUI()
        setImageTypeText()
        handleCameraFunctionality()
        handleListeners()
    }

    override fun onResume() {
        super.onResume()
        setImageTypeText()
        hideUI()
    }

    private fun handleListeners() {
        binding.apply {
            btnShutter.setOnClickListener {
                capturePictureSnapshot()
            }
        }
    }

    private fun setImageTypeText(){
        if(currentImageIndex == 5){
            finish()
            return
        }
        binding.tvImageType.text = when(currentImageIndex){
            0 -> "Please Capture the Front View of the Vehicle"
            1 -> "Please Capture the Left View of the Vehicle"
            2 -> "Please Capture the Back View of the Vehicle"
            3 -> "Please Capture the Right View of the Vehicle"
            4 -> "Please Capture the Odometer of the Vehicle"
            else -> ""
        }
    }

    private fun handleCameraFunctionality() {
        camera.setLifecycleOwner(this)
        camera.addCameraListener(object : CameraListener(){
            override fun onPictureTaken(result: PictureResult) {
                super.onPictureTaken(result)
                try {
                    when(currentImageIndex){
                        0 -> {
//                            result.toBitmap(1000, 1000) { bitmap ->
                                val file = File(
                                    externalMediaDirs.firstOrNull(),
                                    "service_log_car_front_${System.currentTimeMillis()}.png"
                                )

//                            }
                            result.toFile(file) { f ->
                                CurrentPics.picturesDTO.frontPic = f
                            }
                            ServiceLogMasterImagePreviewActivity.pictureResult = result
                            startActivity(Intent(this@ServiceLogMasterCameraActivity,
                                ServiceLogMasterImagePreviewActivity::class.java))
                        }
                        1 -> {
                            val file = File(
                                externalMediaDirs.firstOrNull(),
                                "service_log_car_left_${System.currentTimeMillis()}.png"
                            )
                            result.toFile(file) { f ->
                                CurrentPics.picturesDTO.leftPic = f
                            }
                            ServiceLogMasterImagePreviewActivity.pictureResult = result
                            startActivity(Intent(this@ServiceLogMasterCameraActivity,
                                ServiceLogMasterImagePreviewActivity::class.java))
                        }
                        2 -> {
                            val file = File(
                                externalMediaDirs.firstOrNull(),
                                "service_log_car_back_${System.currentTimeMillis()}.png"
                            )
                            result.toFile(file) { f ->
                                CurrentPics.picturesDTO.backPic = f
                            }
                            ServiceLogMasterImagePreviewActivity.pictureResult = result
                            startActivity(Intent(this@ServiceLogMasterCameraActivity,
                                ServiceLogMasterImagePreviewActivity::class.java))
                        }
                        3 -> {
                            val file = File(
                                externalMediaDirs.firstOrNull(),
                                "service_log_car_right_${System.currentTimeMillis()}.png"
                            )
                            result.toFile(file) { f ->
                                CurrentPics.picturesDTO.rightPic = f
                            }
                            ServiceLogMasterImagePreviewActivity.pictureResult = result
                            startActivity(Intent(this@ServiceLogMasterCameraActivity,
                                ServiceLogMasterImagePreviewActivity::class.java))
                        }
                        4 -> {
                            val file = File(
                                externalMediaDirs.firstOrNull(),
                                "service_log_car_odometer_${System.currentTimeMillis()}.png"
                            )
                            result.toFile(file) { f ->
                                CurrentPics.picturesDTO.odometer = f
                            }
                            ServiceLogMasterImagePreviewActivity.pictureResult = result
                            startActivity(Intent(this@ServiceLogMasterCameraActivity,
                                ServiceLogMasterImagePreviewActivity::class.java))
                        }
                    }
                } catch (e: UnsupportedOperationException) {
                    Toast.makeText(this@ServiceLogMasterCameraActivity,"Could not capture image please try again", Toast.LENGTH_SHORT).show()
//                    imageView.setImageDrawable(ColorDrawable(Color.GREEN))
//                    Toast.makeText(this, "Can't preview this format: " + result.getFormat(), Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun capturePictureSnapshot() {
        if (camera.isTakingPicture) return
        if (camera.preview != Preview.GL_SURFACE) return run {
            Toast.makeText(this,"Picture snapshots are only allowed with the GL_SURFACE preview.", Toast.LENGTH_SHORT).show()
        }
        camera.takePictureSnapshot()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String?>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        val valid = grantResults.all { it == PackageManager.PERMISSION_GRANTED }
        if (valid && !camera.isOpened) {
            camera.open()
        }
    }

    private fun hideUI() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, binding.root).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        val newOrientation = newConfig.orientation

        if(newOrientation == Configuration.ORIENTATION_LANDSCAPE){
            ViewVisibilityUtil.gone(binding.animationView)
        }
        else{
            ViewVisibilityUtil.visible(binding.animationView)
        }

    }

}