package com.sumeet.cars360.ui.admin.fragments.home_navs

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.sumeet.cars360.databinding.DialogUploadDocumentBinding
import com.sumeet.cars360.databinding.FragmentBeforeAfterImagesUploadBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BeforeAfterImagesUploadFragment : Fragment() {

    lateinit var binding: FragmentBeforeAfterImagesUploadBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBeforeAfterImagesUploadBinding.inflate(inflater,container,false)
        return binding.root
    }

    fun showUploadDialogBox() {
        val alertBinding = DialogUploadDocumentBinding.inflate(
            LayoutInflater.from(context),
            binding.root,
            false
        )
        val alert = AlertDialog.Builder(requireActivity()) //set message, title, and icon
            .setView(alertBinding.root)
            .setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }
            .create()

        alert.show()
        alertBinding.apply {
            btnUpload.setOnClickListener {
                alert.dismiss()
            }
        }
    }

}