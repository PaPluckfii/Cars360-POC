package com.sumeet.cars360.ui.admin.master_service_log.form_screens.part_2_images_capture

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.otaliastudios.cameraview.PictureResult
import com.sumeet.cars360.databinding.ActivityServiceLogMasterImagePreviewBinding
import com.sumeet.cars360.ui.admin.util.CurrentPics.currentImageIndex
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ServiceLogMasterImagePreviewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityServiceLogMasterImagePreviewBinding

    companion object {
        var pictureResult: PictureResult? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityServiceLogMasterImagePreviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val result = pictureResult ?: run {
            finish()
            return
        }

        try {
            result.toBitmap(1000, 1000) { bitmap -> binding.ivCapturedImage.setImageBitmap(bitmap) }
        } catch (e: UnsupportedOperationException) { }

        binding.btnDiscard.setOnClickListener{
            finish()
        }

        binding.btnSave.setOnClickListener {
            currentImageIndex += 1
            finish()
        }

    }
}