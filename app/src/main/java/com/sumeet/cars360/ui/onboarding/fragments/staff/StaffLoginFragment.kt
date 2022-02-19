package com.sumeet.cars360.ui.onboarding.fragments.staff

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.sumeet.cars360.data.local.preferences.SavePrefs
import com.sumeet.cars360.data.local.preferences.UserType
import com.sumeet.cars360.databinding.FragmentStaffLoginBinding
import com.sumeet.cars360.ui.admin.AdminActivity
import com.sumeet.cars360.ui.customer.CustomerActivity
import com.sumeet.cars360.ui.onboarding.AuthViewModel
import com.sumeet.cars360.ui.staff.StaffActivity
import com.sumeet.cars360.util.Resource
import com.sumeet.cars360.util.ViewVisibilityUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StaffLoginFragment : Fragment() {

    private lateinit var binding: FragmentStaffLoginBinding
    private val viewModel: AuthViewModel by activityViewModels()

    private lateinit var savePrefs: SavePrefs

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStaffLoginBinding.inflate(
            LayoutInflater.from(context),
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleListeners()
    }

    private fun handleListeners() {
        binding.btnLogin.setOnClickListener {
            if (checkValidity()) {
                viewModel.signInStaff(
                    binding.etEmail.text.toString(),
                    binding.etPassword.text.toString()
                )

                viewModel.currentUserOperation.observe(viewLifecycleOwner) { firebaseId ->
                    savePrefs = SavePrefs(requireContext())
                    when (firebaseId) {
                        is Resource.Success -> {

                            firebaseId.data?.let { it1 -> viewModel.findUserByFirebaseId(it1) }

                            viewModel.userDataFromServer.observe(viewLifecycleOwner) {
                                when (it) {
                                    is Resource.Loading -> {}
                                    is Resource.Success -> {
                                        savePrefs.saveLoginStatus(true)
                                        firebaseId.data?.let { it1 ->
                                            savePrefs.saveUserId(it1)
                                        }
                                        if (it.data?.userResponse?.get(0)?.userTypeId == "1")
                                            savePrefs.saveUserType(UserType.Admin)
                                        else
                                            savePrefs.saveUserType(UserType.Employee)

                                        startActivity(
                                            Intent(activity, AdminActivity::class.java)
                                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                        ).also { activity?.finish() }
                                    }
                                    is Resource.Error -> {
                                        ViewVisibilityUtil.visibilityExchanger(
                                            binding.llLogin,
                                            binding.progressBar
                                        )
                                        Toast.makeText(
                                            context,
                                            "No User found with these credentials ${it.message}",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                            }


                        }
                        is Resource.Loading -> {
                            ViewVisibilityUtil.visibilityExchanger(
                                binding.progressBar,
                                binding.llLogin
                            )
                        }
                        is Resource.Error -> {
                            ViewVisibilityUtil.visibilityExchanger(
                                binding.llLogin,
                                binding.progressBar
                            )
                            Toast.makeText(
                                context,
                                "No User found with these credentials",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }
        binding.btnGoBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnSkipLogin.setOnClickListener {
            startActivity(
                Intent(activity, AdminActivity::class.java)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            ).also {
                activity?.finish()
            }
        }

    }

    private fun checkValidity(): Boolean {
        var validity = true
        if(binding.etEmail.text.isNullOrEmpty()){
            binding.tilEmail.error = "Email Cannot be Empty"
            validity = false
        }
        if(binding.etPassword.text.isNullOrEmpty()){
            binding.tilPassword.error = "Password Cannot be Empty"
            validity = false
        }
        return validity
    }

}