package com.darothub.theweatherapp.com.darothub.theweatherapp.weather.ui

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.darothub.theweatherapp.R
import com.darothub.theweatherapp.com.darothub.theweatherapp.domain.UIStateListener
import com.darothub.theweatherapp.core.utils.hide
import com.darothub.theweatherapp.core.utils.show
import com.darothub.theweatherapp.databinding.StateLayoutBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

abstract class BaseFragment(@LayoutRes val layout: Int): Fragment(layout), UIStateListener {
    private lateinit var uiStateListener: UIStateListener
    lateinit var stateLayoutBinding: StateLayoutBinding
    lateinit var loaderDialog: AlertDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        uiStateListener = this
        setupLoaderDialog()
    }
    override fun <T> onSuccess(data: T) {
        loaderDialog.dismiss()
    }

    override fun <T> onCurrentWeather(data: T) {
        loaderDialog.dismiss()
    }

    override fun <T> onForecast(data: T) {
        loaderDialog.dismiss()
    }
    override fun onError(error: String?) {
        stateLayoutBinding.errorTv.text = getString(R.string.error, error)
        stateLayoutBinding.errorTv.show()
        stateLayoutBinding.progressBar.hide()
        loaderDialog.show()
    }

    override fun loading() {
        stateLayoutBinding.progressBar.show()
        loaderDialog.show()
    }
    private fun setupLoaderDialog() {
        val builder = MaterialAlertDialogBuilder(requireContext())
        stateLayoutBinding = StateLayoutBinding.inflate(layoutInflater)
        builder.setView(stateLayoutBinding.root)
        builder.setCancelable(false)
        loaderDialog = builder.create()
        loaderDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }
}