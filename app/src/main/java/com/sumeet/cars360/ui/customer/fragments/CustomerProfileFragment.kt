package com.sumeet.cars360.ui.customer.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.sumeet.cars360.R
import com.sumeet.cars360.data.local.room.model.CarEntity
import com.sumeet.cars360.data.remote.model.user.UserResponse
import com.sumeet.cars360.data.remote.old_model.Cars360Document
import com.sumeet.cars360.databinding.CustomerProfileBottomSheetBinding
import com.sumeet.cars360.databinding.FragmentCustomerProfileBinding
import com.sumeet.cars360.ui.customer.CustomerViewModel
import com.sumeet.cars360.ui.customer.util.CarItemClickListener
import com.sumeet.cars360.ui.customer.util.CarsRecyclerAdapter
import com.sumeet.cars360.ui.customer.util.ProfileBottomSheetItemClickListener
import com.sumeet.cars360.ui.customer.util.ProfileBottomSheetRecyclerAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CustomerProfileFragment : Fragment(), CarItemClickListener, ProfileBottomSheetItemClickListener {

    private lateinit var binding: FragmentCustomerProfileBinding
    private val viewModel: CustomerViewModel by activityViewModels()

    private lateinit var mCustomerBottomSheetBehavior: BottomSheetBehavior<CoordinatorLayout>
    private lateinit var bottomSheetLayoutBinding: CustomerProfileBottomSheetBinding
    private var isBottomSheetVisible: Boolean = false
    private lateinit var bottomSheetRecyclerAdapter: ProfileBottomSheetRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCustomerProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        observeCustomerData()
        setUpBottomSheet()
    }

    private fun setUpBottomSheet() {
        bottomSheetLayoutBinding = binding.customerProfileBottomSheetLayout
        mCustomerBottomSheetBehavior = BottomSheetBehavior.from(binding.customerProfileSheet)
        mCustomerBottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        bottomSheetRecyclerAdapter = ProfileBottomSheetRecyclerAdapter(this)
        setUpOptionsListListeners()
    }

    private fun setUpOptionsListListeners() {
        mCustomerBottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        mCustomerBottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
                    }
                    BottomSheetBehavior.STATE_EXPANDED -> {
                        binding.vCustomerTransparentBg.visibility = View.VISIBLE
                    }
                    BottomSheetBehavior.STATE_HALF_EXPANDED -> {
                    }
                    BottomSheetBehavior.STATE_HIDDEN -> {
                        binding.vCustomerTransparentBg.visibility = View.GONE
                    }
                    BottomSheetBehavior.STATE_DRAGGING -> {
                    }
                    BottomSheetBehavior.STATE_SETTLING -> {
                        binding.vCustomerTransparentBg.visibility = View.VISIBLE
                    }
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {

            }
        })

        binding.apply {
            binding.vCustomerTransparentBg.setOnClickListener {
                if (isBottomSheetVisible)
                    hideBottomSheet()
            }
            llEditProfile.setOnClickListener {
                Toast.makeText(context, "Profile Details are disabled for now", Toast.LENGTH_SHORT)
                    .show()
            }
            llInvoices.setOnClickListener {
                bottomSheetLayoutBinding.tvTitle.text = "Invoices"
                bottomSheetLayoutBinding.bottomSheetRecyclerView.adapter = bottomSheetRecyclerAdapter
                bottomSheetRecyclerAdapter.setStaticList(listOf(
                    Cars360Document(0,"","","20 Jan 2022"),
                    Cars360Document(0,"","","20 Dec 2021"),
                    Cars360Document(0,"","","30 Oct 2021")
                ))
                showBottomSheet()
                bottomSheetLayoutBinding.ibMinimizeBottomSheet.setOnClickListener {
                    hideBottomSheet()
                }
            }
            llRecentServiceHistory.setOnClickListener {
                bottomSheetLayoutBinding.tvTitle.text = "Recent Service History"
                bottomSheetLayoutBinding.bottomSheetRecyclerView.adapter = bottomSheetRecyclerAdapter
                bottomSheetRecyclerAdapter.setStaticList(listOf(
                    Cars360Document(0,"","","20 Jan 2022"),
                    Cars360Document(0,"","","20 Dec 2021"),
                    Cars360Document(0,"","","30 Oct 2021")
                ))
                showBottomSheet()
                bottomSheetLayoutBinding.ibMinimizeBottomSheet.setOnClickListener {
                    hideBottomSheet()
                }
            }
            llHealthReport.setOnClickListener {
                bottomSheetLayoutBinding.tvTitle.text = "Health Report"
                bottomSheetLayoutBinding.bottomSheetRecyclerView.adapter = bottomSheetRecyclerAdapter
                bottomSheetRecyclerAdapter.setStaticList(listOf(
                    Cars360Document(0,"","","20 Jan 2022"),
                    Cars360Document(0,"","","20 Dec 2021"),
                    Cars360Document(0,"","","30 Oct 2021")
                ))
                showBottomSheet()
                bottomSheetLayoutBinding.ibMinimizeBottomSheet.setOnClickListener {
                    hideBottomSheet()
                }
            }
        }

    }

    private fun showBottomSheet(){
        mCustomerBottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        binding.vCustomerTransparentBg.visibility = View.VISIBLE
        isBottomSheetVisible = true
    }
    private fun hideBottomSheet(){
        mCustomerBottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        binding.vCustomerTransparentBg.visibility = View.GONE
        isBottomSheetVisible = false
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.share_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        try {
            startActivity(
                Intent.createChooser(
                    Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT, "This message will contain appstore link")
                        type = "text/plain"
                    },
                    null
                )
            )
        } catch (e: Exception) {
            Toast.makeText(context, "Something went wrong - ${e.message}", Toast.LENGTH_SHORT)
                .show()
        }
        return true
    }

    @SuppressLint("SetTextI18n")
    private fun observeCustomerData() {
        //        viewModel.myUser.observe(viewLifecycleOwner,{
//
//        })

        val myUser = UserResponse(
            "Flat no -20," +
                    " 5th Floor, A-13, Chouhan Green Valley", "Bhilai", "India", "Sumeet Das",
            "", "sumeet1996das@gmail.com",
            "", "", "", "+919669867977",
            "Sumeet Das", "490020", "", "", "C.G."
        ,"","")
        binding.apply {
            tvName.text = myUser.name
            tvEmailId.text = myUser.email
            tvMobile.text = myUser.mobile
            tvState.text = myUser.state
            tvCity.text = myUser.city
            tvPostalCode.text = myUser.postalCode

            val formattedString = myUser.address?.replace(", ", ",\n")

            tvAddressLine.text = formattedString

//            val addressStringArray: List<String> =
//                myUser.address?.split(",")?.toList() ?: emptyList()
//            when (addressStringArray.size) {
//                1 -> {
//                    tvAddressLine.text = addressStringArray[0]
//                }
//                2 -> {
//                    tvAddressLine.text = addressStringArray[0] +
//                            "\n" +
//                            addressStringArray[1]
//                }
//                3 -> {
//                    tvAddressLine.text = addressStringArray[0] +
//                            "\n" +
//                            addressStringArray[1] +
//                            "\n" +
//                            addressStringArray[2]
//                }
//            }

        }

        viewModel.myCars.observe(viewLifecycleOwner, {
            //TODO remove Dummy Data
            binding.customerCarsRecyclerView.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = CarsRecyclerAdapter(
                    listOf(
                        CarEntity(
                            0, "", "BMW",
                            "https://i2.wp.com/thinkmarketingmagazine.com/wp-content/uploads/2012/08/bmw-logo.png?ssl=1",
                            "CL 500", "White", "CG-04-K-1223", "Petrol",
                            "Something", "22/10/2025",
                            "${Uri.parse("android.resource://com.sumeet.cars360/drawable/image_onboarding_range_rover")}",
                            null,
                            "",
                            ""
                        ),
                        CarEntity(
                            0, "", "BMW",
                            "https://i2.wp.com/thinkmarketingmagazine.com/wp-content/uploads/2012/08/bmw-logo.png?ssl=1",
                            "CL 500", "White", "CG-04-K-1223", "Petrol",
                            "Something", "22/10/2025",
                            "${Uri.parse("android.resource://com.sumeet.cars360/drawable/image_onboarding_range_rover")}",
                            null,
                            "",
                            ""
                        )
                    ), this@CustomerProfileFragment
                )
            }
//            if(it.isEmpty())
//                ViewVisibilityUtil.gone(binding.customerCarsRecyclerView)
//            else{
//                binding.customerCarsRecyclerView.apply {
//                    layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
//                    adapter = CarsRecyclerAdapter(it,this@CustomerHomeFragment)
//                }
//            }
        })
    }

    override fun onCarItemClicked(carEntity: CarEntity) {
        //TODO("Not yet implemented")
    }

    override fun onBottomSheetItemClicked() {
        //TODO("Not yet implemented")
    }

}