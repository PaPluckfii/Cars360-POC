package com.sumeet.cars360.ui.onboarding.staff

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.sumeet.cars360.data.local.preferences.SavePrefs
import com.sumeet.cars360.data.wrapper.UserType
import com.sumeet.cars360.databinding.FragmentStaffLoginBinding
import com.sumeet.cars360.ui.admin.AdminActivity
import com.sumeet.cars360.util.FormDataResource
import com.sumeet.cars360.util.ViewVisibilityUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StaffLoginFragment : Fragment() {

    private lateinit var binding: FragmentStaffLoginBinding
    private val viewModel: StaffLoginViewModel by viewModels()

    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private lateinit var savePrefs: SavePrefs

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        savePrefs = SavePrefs(requireContext())
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
        setHasOptionsMenu(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        findNavController().popBackStack()
        return true
    }

    private fun handleListeners() {
        binding.btnLogin.setOnClickListener {
            if (checkValidity()) {
                ViewVisibilityUtil.visibilityExchanger(binding.progressBar, binding.llLogin)
                mAuth.signInWithEmailAndPassword(
                    binding.etEmail.text.toString(),
                    binding.etPassword.text.toString()
                )
                    .addOnSuccessListener {
                        checkUserDataFromServer()
                    }.addOnFailureListener {
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

    private fun checkUserDataFromServer() {
        mAuth.uid?.let { viewModel.findUserByFirebaseId(it) }
        viewModel.userDataFromServer.observe(viewLifecycleOwner) {
            when (it) {
                is FormDataResource.Loading -> {
                }
                is FormDataResource.Success -> {
                    savePrefs.saveLoginStatus(true)
                    mAuth.currentUser?.uid.let { id ->
                        savePrefs.saveUserId(id.toString())
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
                is FormDataResource.Error -> {
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

    private fun checkValidity(): Boolean {
        var validity = true
        if (binding.etEmail.text.isNullOrEmpty()) {
            binding.tilEmail.error = "Email Cannot be Empty"
            validity = false
        }
        if (binding.etPassword.text.isNullOrEmpty()) {
            binding.tilPassword.error = "Password Cannot be Empty"
            validity = false
        }
        return validity
    }

}