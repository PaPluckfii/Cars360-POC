package com.sumeet.cars360.ui.customer.fragments


import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.firebase.auth.FirebaseAuth
import com.sumeet.cars360.R
import com.sumeet.cars360.data.local.preferences.CustomerLoginType
import com.sumeet.cars360.data.local.preferences.ReadPrefs
import com.sumeet.cars360.data.local.preferences.SavePrefs
import com.sumeet.cars360.data.remote.model.user_cars.CarDetailsResponse
import com.sumeet.cars360.data.remote.old_model.Cars360Document
import com.sumeet.cars360.databinding.CustomerProfileBottomSheetBinding
import com.sumeet.cars360.databinding.FragmentCustomerProfileBinding
import com.sumeet.cars360.ui.customer.CustomerActivity
import com.sumeet.cars360.ui.customer.CustomerViewModel
import com.sumeet.cars360.ui.customer.util.*
import com.sumeet.cars360.ui.onboarding.OnBoardingActivity
import com.sumeet.cars360.util.ButtonClickHandler
import com.sumeet.cars360.util.Resource
import com.sumeet.cars360.util.ViewVisibilityUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CustomerProfileFragment : Fragment(), CarItemClickListener, ProfileBottomSheetItemClickListener {

    private lateinit var binding: FragmentCustomerProfileBinding
    private val viewModel: CustomerViewModel by activityViewModels()

    private lateinit var mCustomerBottomSheetBehavior: BottomSheetBehavior<CoordinatorLayout>
    private lateinit var bottomSheetLayoutBinding: CustomerProfileBottomSheetBinding
    private var isBottomSheetVisible: Boolean = false
    private lateinit var bottomSheetRecyclerAdapter: ProfileBottomSheetRecyclerAdapter
    private lateinit var readPrefs:ReadPrefs

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
        readPrefs = ReadPrefs(requireContext())

        when(readPrefs.readCustomerLoginType()){
            is CustomerLoginType.LoggedIn -> {
                setUpProfileData()
                observeCustomerData()
                setUpBottomSheet()
            }
            else -> {
                ViewVisibilityUtil.gone(binding.clCustomerProfileSheet)
                ViewVisibilityUtil.gone(binding.customerCarsRecyclerView)
                ViewVisibilityUtil.gone(binding.llProfileButtons)
                ViewVisibilityUtil.visible(binding.llNonLoginButton)

                binding.btnGoHome.setOnClickListener {
                    if(ButtonClickHandler.buttonClicked()){
                        SavePrefs(requireContext()).resetAppData()
                        Toast.makeText(context, "Logged Out", Toast.LENGTH_SHORT)
                            .show()
                        startActivity(
                            Intent(activity, OnBoardingActivity::class.java)
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        ).also { activity?.finish() }
                    }
                }
            }
        }

    }

    private fun setUpProfileData() {
        binding.apply {
            if (readPrefs.readUserName()?.isEmpty()==true){
                getAndSetUserData()
            }
            setTextInView(tvName,readPrefs.readUserName())
            setTextInView(tvMobile,readPrefs.readUserMobileNumber())
            setTextInView(tvEmailId,readPrefs.readUserEmail())
            setTextInView(tvAddressLine,readPrefs.readUserAddress())
            setTextInView(tvCity,readPrefs.readUserCity())
            setTextInView(tvState,readPrefs.readUserState())
            if (readPrefs.readUserPostalCode()?.isNotEmpty() == true){
                setTextInView(tvPostalCode,"Pin Code - ${readPrefs.readUserPostalCode()}")
            }else{
                setTextInView(tvPostalCode,"")
            }
            Glide.with(binding.root).load(readPrefs.readProfileImage())
                .error(R.drawable.ic_dummy_profile_pic).into(ivProfileImage)

        }

    }

    private fun getAndSetUserData() {
        FirebaseAuth.getInstance().uid?.let { viewModel.getCustomerByUserId(it) }
        viewModel.customerDetails.observeOnce(this, Observer {
            it?.let {
                when(it){
                    is Resource.Loading -> {}
                    is Resource.Error -> {}
                    is Resource.Success -> {
                        viewModel.setUserData(requireContext(),it.data?.userResponse!![0])
                        setUpProfileData()
                    }
                }
            }
        })
    }


    private fun setTextInView(textView: TextView,text:String?){
        println("viewText: $text")
        if (text.isNullOrEmpty()){
            textView.visibility = View.GONE
        } else{
            textView.visibility = View.VISIBLE
            textView.text = text
        }
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
            btnEditProfile.setOnClickListener {
                Toast.makeText(context, "Profile Details are disabled for now", Toast.LENGTH_SHORT)
                    .show()
            }
            btnLogout.setOnClickListener {
                SavePrefs(requireContext()).resetAppData()
                Toast.makeText(context, "Logged Out", Toast.LENGTH_SHORT)
                    .show()
                startActivity(
                    Intent(activity, OnBoardingActivity::class.java)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                ).also { activity?.finish() }

            }
//            llInvoices.setOnClickListener {
//                bottomSheetLayoutBinding.tvTitle.text = "Invoices"
//                bottomSheetLayoutBinding.bottomSheetRecyclerView.adapter = bottomSheetRecyclerAdapter
//                bottomSheetRecyclerAdapter.setStaticList(listOf(
//                    Cars360Document(0,"","","20 Jan 2022"),
//                    Cars360Document(0,"","","20 Dec 2021"),
//                    Cars360Document(0,"","","30 Oct 2021")
//                ))
//                showBottomSheet()
//                bottomSheetLayoutBinding.ibMinimizeBottomSheet.setOnClickListener {
//                    hideBottomSheet()
//                }
//            }
//            llRecentServiceHistory.setOnClickListener {
//                bottomSheetLayoutBinding.tvTitle.text = "Recent Service History"
//                bottomSheetLayoutBinding.bottomSheetRecyclerView.adapter = bottomSheetRecyclerAdapter
//                bottomSheetRecyclerAdapter.setStaticList(listOf(
//                    Cars360Document(0,"","","20 Jan 2022"),
//                    Cars360Document(0,"","","20 Dec 2021"),
//                    Cars360Document(0,"","","30 Oct 2021")
//                ))
//                showBottomSheet()
//                bottomSheetLayoutBinding.ibMinimizeBottomSheet.setOnClickListener {
//                    hideBottomSheet()
//                }
//            }
//            llHealthReport.setOnClickListener {
//                bottomSheetLayoutBinding.tvTitle.text = "Health Report"
//                bottomSheetLayoutBinding.bottomSheetRecyclerView.adapter = bottomSheetRecyclerAdapter
//                bottomSheetRecyclerAdapter.setStaticList(listOf(
//                    Cars360Document(0,"","","20 Jan 2022"),
//                    Cars360Document(0,"","","20 Dec 2021"),
//                    Cars360Document(0,"","","30 Oct 2021")
//                ))
//                showBottomSheet()
//                bottomSheetLayoutBinding.ibMinimizeBottomSheet.setOnClickListener {
//                    hideBottomSheet()
//                }
//            }
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

    private fun observeCustomerData() {

        "+91${FirebaseAuth.getInstance().currentUser?.phoneNumber}".let { viewModel.getCustomerCarDetailsByMobileNumber(it) }
        viewModel.customerCarDetailsData.observe(viewLifecycleOwner, Observer {

            when(it){
                is Resource.Loading -> {}
                is Resource.Error -> {}
                is Resource.Success -> {
                    binding.customerCarsRecyclerView.apply {
                        layoutManager = LinearLayoutManager(context)
                        adapter = it.data?.carDetailsResponse?.let { data ->
                            CarsRecyclerAdapter(
                                data,
                                this@CustomerProfileFragment
                            )
                        }
                    }
                }
            }
        })
    }

    override fun onCarItemClicked(carDetailsResponse: CarDetailsResponse) {
        //TODO("Not yet implemented")
    }

    override fun onBottomSheetItemClicked() {
        //TODO("Not yet implemented")
    }
}