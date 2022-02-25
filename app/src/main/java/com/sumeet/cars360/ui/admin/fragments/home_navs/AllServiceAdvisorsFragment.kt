package com.sumeet.cars360.ui.admin.fragments.home_navs

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.sumeet.cars360.data.remote.model.user.UserResponse
import com.sumeet.cars360.databinding.FragmentAllServiceAdvisorsBinding
import com.sumeet.cars360.ui.admin.AdminViewModel
import com.sumeet.cars360.ui.admin.AllCustomerRecyclerAdapter
import com.sumeet.cars360.ui.admin.OnAllCustomerItemClickListener
import com.sumeet.cars360.util.Constants.NO_INTERNET_CONNECTION
import com.sumeet.cars360.util.Resource
import com.sumeet.cars360.util.ViewVisibilityUtil
import com.sumeet.cars360.util.navigate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AllServiceAdvisorsFragment : Fragment(), OnAllCustomerItemClickListener {

    private lateinit var binding: FragmentAllServiceAdvisorsBinding
    private val viewModel: AdminViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAllServiceAdvisorsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerData()
        setHasOptionsMenu(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        findNavController().popBackStack()
        return true
    }

    private fun setUpRecyclerData() {
        viewModel.getAllServiceAdvisorByUserId("3")
        viewModel.allServiceAdvisors.observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Loading -> {
                    ViewVisibilityUtil.visibilityExchanger(visible = binding.progressBar
                        ,gone = binding.allServiceAdvisorsRecyclerview)
                    ViewVisibilityUtil.gone(binding.errorMessage)
                }
                is Resource.Error -> {
                    ViewVisibilityUtil.gone(binding.allServiceAdvisorsRecyclerview)
                    ViewVisibilityUtil.gone(binding.progressBar)
                    ViewVisibilityUtil.visible(binding.errorMessage)

                    if (it.message.equals(NO_INTERNET_CONNECTION))
                        binding.tvError.text = NO_INTERNET_CONNECTION
                    else
                        binding.tvError.text = it.message
                }
                is Resource.Success -> {
                    ViewVisibilityUtil.visibilityExchanger(visible = binding.allServiceAdvisorsRecyclerview
                        ,gone = binding.progressBar)
                    ViewVisibilityUtil.gone(binding.errorMessage)
                    binding.allServiceAdvisorsRecyclerview.apply {
                        layoutManager = LinearLayoutManager(context)
                        adapter = it.data?.userResponse?.let { data ->
                            AllCustomerRecyclerAdapter(
                                data,
                                this@AllServiceAdvisorsFragment
                            )
                        }
                    }
                }
            }
        })
    }

    override fun onAllCustomerItemClicked(userResponse: UserResponse) {
        navigate(AllServiceAdvisorsFragmentDirections.actionAllServiceAdvisorsFragmentToAllCustomerDetailsFragment(userResponse))
    }


}