package com.darothub.theweatherapp.com.darothub.theweatherapp.main

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.location.Location
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
    private var uiStateListener: UIStateListener? = null
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

    override fun <T> onWeatherResponse(data: T) {
        loaderDialog.dismiss()
    }
    override fun onError(error: String?) {
        stateLayoutBinding.errorTv.text = getString(R.string.error, error)
        setErrorView()
        loaderDialog.show()
    }

    override fun loading() {
        setSuccessView()
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
    private fun setErrorView() {
        stateLayoutBinding.errorTv.show()
        stateLayoutBinding.progressBar.hide()
        stateLayoutBinding.adviceTv.hide()
    }
    private fun setSuccessView(){
        stateLayoutBinding.progressBar.show()
        stateLayoutBinding.adviceTv.show()
    }
    override fun onDestroy() {
        super.onDestroy()
        uiStateListener = null
    }

    override fun onLocationReady(location: Location) {}
}