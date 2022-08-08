package com.darothub.theweatherapp.auth

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.darothub.theweatherapp.R
import com.darothub.theweatherapp.com.darothub.theweatherapp.core.utils.CreateEncryptedSharedPref
import com.darothub.theweatherapp.com.darothub.theweatherapp.core.utils.SharedPreferenceHelper
import com.darothub.theweatherapp.com.darothub.theweatherapp.main.BaseFragment
import com.darothub.theweatherapp.core.utils.viewBinding
import com.darothub.theweatherapp.databinding.FragmentAuthenticationBinding
import java.util.concurrent.Executor


class AuthenticationFragment : BaseFragment(R.layout.fragment_authentication) {
    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo
    private val binding by viewBinding(FragmentAuthenticationBinding::bind)
    lateinit var sharedPrefHelper: SharedPreferenceHelper
    lateinit var username: String
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPrefHelper = SharedPreferenceHelper(CreateEncryptedSharedPref.create(requireContext()))
        executor = ContextCompat.getMainExecutor(requireContext())
        val biometricManager = BiometricManager.from(requireContext())
        biometricPrompt = BiometricPrompt(this, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(
                    errorCode: Int,
                    errString: CharSequence
                ) {
                    super.onAuthenticationError(errorCode, errString)
                    onError(getString(R.string.auth_error, errString))
                }

                override fun onAuthenticationSucceeded(
                    result: BiometricPrompt.AuthenticationResult
                ) {
                    super.onAuthenticationSucceeded(result)
                    binding.usernameInput.setText(username)
                    onSuccess(true)
                }
                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    onError(getString(R.string.auth_failed))
                }
            })

        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle(getString(R.string.biometric_login))
            .setSubtitle(getString(R.string.biometric_login_advice))
            .setNegativeButtonText("Use account password")
            .setAllowedAuthenticators(BIOMETRIC_STRONG)
            .build()

        binding.inputLayout.setEndIconOnClickListener {
            val encrypted = sharedPrefHelper.retrieve()
            if (encrypted.isNullOrBlank()) {
                onError(getString(R.string.first_user))
                return@setEndIconOnClickListener
            }
            username = encrypted
            biometricPrompt.authenticate(promptInfo)
        }
        binding.btn.setOnClickListener {
            val usernameHere = binding.usernameInput.text.toString()
            when(checkIfDeviceCanAuthenticate(biometricManager)){
                is BiometricState.ReqestEnrollment -> onRegisterBiometric()
                else -> {}
            }
            if (verifyUserName(usernameHere)) {
                biometricPrompt.authenticate(promptInfo)
                username = usernameHere
            } else {
                onError(getString(R.string.username_error))
            }
        }

    }
    private fun checkIfDeviceCanAuthenticate(biometricManager: BiometricManager):BiometricState =
        when (biometricManager.canAuthenticate(BIOMETRIC_STRONG)) {
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE ->{
                onError(getString(R.string.no_biometric_feature))
                BiometricState.NoHardware
            }
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE ->{
                onError(getString(R.string.biometric_not_available))
                BiometricState.NotAvailable
            }
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                BiometricState.ReqestEnrollment
            }
            else -> BiometricState.Available
        }
    private fun verifyUserName(username: String): Boolean = when {
        username.isBlank() -> false
        username.length < 4 -> false
        else -> true
    }

    override fun onError(error: String?) =
        Toast.makeText(
            requireContext(),
            error, Toast.LENGTH_SHORT
        ).show()

    override fun <T> onSuccess(data: T) {
        data as Boolean
        when (data) {
            true -> {
                sharedPrefHelper.save(username)
                findNavController().navigate(R.id.currentWeatherFragment)
            }
            else -> return
        }
    }

    override fun onRegisterBiometric() {
        super.onRegisterBiometric()
        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.biometric))
            .setMessage(getString(R.string.biometric_request_advice))
            .setCancelable(false)
            .setPositiveButton(getString(R.string.ok)){p0, p1 ->
                biometricEnrollment()
            }.show()
    }

    private fun biometricEnrollment(){
        val enrollIntent = Intent(Settings.ACTION_BIOMETRIC_ENROLL).apply {
            putExtra(
                Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
                BIOMETRIC_STRONG
            )
        }
        startActivity(enrollIntent)
    }

}

sealed class BiometricState {
    object NoHardware: BiometricState()
    object NotAvailable: BiometricState()
    object ReqestEnrollment: BiometricState()
    object Available: BiometricState()
}