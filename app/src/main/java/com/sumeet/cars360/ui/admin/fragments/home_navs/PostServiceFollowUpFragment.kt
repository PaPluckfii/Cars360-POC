package com.sumeet.cars360.ui.admin.fragments.home_navs

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.sumeet.cars360.R
import com.sumeet.cars360.data.remote.model.service_logs.ServiceLogResponse
import com.sumeet.cars360.databinding.FragmentPostServiceFollowUpBinding
import com.sumeet.cars360.ui.admin.AdminViewModel
import com.sumeet.cars360.ui.admin.OnServiceItemClickListener
import com.sumeet.cars360.ui.admin.ServiceLogRecyclerAdapter
import com.sumeet.cars360.util.Constants
import com.sumeet.cars360.util.Resource
import com.sumeet.cars360.util.ViewVisibilityUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostServiceFollowUpFragment : Fragment(), OnServiceItemClickListener {

    private lateinit var binding: FragmentPostServiceFollowUpBinding
    private val viewModel: AdminViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPostServiceFollowUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerData()
    }

    private fun setUpRecyclerData(){
        viewModel.getAllServiceLogsByUserId("11")
        viewModel.allServiceLogs.observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Loading -> {
                    ViewVisibilityUtil.visibilityExchanger(visible = binding.progressBar
                        ,gone = binding.postServiceFollowUpRecyclerview)
                    ViewVisibilityUtil.gone(binding.errorMessage)
                }
                is Resource.Error -> {
                    ViewVisibilityUtil.gone(binding.postServiceFollowUpRecyclerview)
                    ViewVisibilityUtil.gone(binding.progressBar)
                    ViewVisibilityUtil.visible(binding.errorMessage)

                    if (it.message.equals(Constants.NO_INTERNET_CONNECTION))
                        binding.tvError.text = Constants.NO_INTERNET_CONNECTION
                    else
                        binding.tvError.text = it.message
                }
                is Resource.Success -> {
                    ViewVisibilityUtil.visibilityExchanger(visible = binding.postServiceFollowUpRecyclerview
                        ,gone = binding.progressBar)
                    ViewVisibilityUtil.gone(binding.errorMessage)
                    binding.postServiceFollowUpRecyclerview.apply {
                        layoutManager = LinearLayoutManager(context)
                        adapter = it.data?.serviceLogResponse?.let { data ->
                            ServiceLogRecyclerAdapter(
                                data,
                                this@PostServiceFollowUpFragment
                            )
                        }
                    }
                }
            }
        })
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
        btnUpload.setOnClickListener {

        }
        customDialog.show()
    }

}