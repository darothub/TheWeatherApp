package com.darothub.theweatherapp.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.darothub.theweatherapp.R
import com.darothub.theweatherapp.core.utils.viewBinding
import com.darothub.theweatherapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding by viewBinding(ActivityMainBinding::inflate)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}