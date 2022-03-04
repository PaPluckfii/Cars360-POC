package com.sumeet.cars360.ui.admin.data_presenter.all_customers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.sumeet.cars360.R
import com.sumeet.cars360.databinding.FragmentAllCustomerDetailsBinding

class AllCustomerDetailsFragment : Fragment() {

    private lateinit var binding: FragmentAllCustomerDetailsBinding
    private val args: AllCustomerDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAllCustomerDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpProfileData()
        setHasOptionsMenu(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        findNavController().popBackStack()
        return true
    }

    private fun setUpProfileData() {
        binding.apply {
            args.customerEntity.apply {
                setTextInView(tvName, name)
                setTextInView(tvMobile, mobileNumber)
                setTextInView(tvEmailId, emailId)
                setTextInView(tvAddressLine, address)
                setTextInView(tvCity, city)
                setTextInView(tvState, state)
                if (postalCode?.isNotEmpty() == true) {
                    setTextInView(tvPostalCode, "Pin Code : $postalCode")
                } else {
                    setTextInView(tvPostalCode, "")
                }
                Glide.with(binding.root)
                    .load(userImageUrl)
                    .error(R.drawable.ic_dummy_profile_pic)
                    .into(ivProfileImage)
            }
        }
    }

    private fun setTextInView(textView: TextView, text: String?) {
        println("viewText: $text")
        if (text.isNullOrEmpty()) {
            textView.visibility = View.GONE
        } else {
            textView.visibility = View.VISIBLE
            textView.text = text
        }
    }
}