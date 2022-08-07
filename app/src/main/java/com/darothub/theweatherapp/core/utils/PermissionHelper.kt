package com.darothub.theweatherapp.core.utils

import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.registerForActivityResult
import androidx.fragment.app.Fragment

class PermissionHelper<I, O>(private val context: Fragment, private val listener: PermissionListener<O>) {
    private lateinit var launcher: ActivityResultLauncher<I>
    operator fun  invoke(contract: ActivityResultContract<I, O>) : PermissionHelper<I, O> {
        launcher = context.registerForActivityResult(contract) {
            listener.isGranted(it)
        }
        return this
    }

    fun launch(permission: I) {
        launcher.launch(permission)
    }

}

fun interface PermissionListener<in O> {
    fun isGranted(value: O)
}