package com.darothub.theweatherapp.com.darothub.theweatherapp.weather.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.darothub.theweatherapp.R
import com.darothub.theweatherapp.com.darothub.theweatherapp.domain.model.Hour
import com.darothub.theweatherapp.core.utils.viewBinding
import com.darothub.theweatherapp.databinding.FragmentForecastBinding


const val HOUR = "HourEntity"
class ForecastFragment : Fragment(R.layout.fragment_forecast) {
    private val binding by viewBinding(FragmentForecastBinding::bind)
    private lateinit var recyclerViewAdapter: DataAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.takeIf { it.containsKey(HOUR) }?.apply {
            val list = getSerializable(HOUR) as List<Hour>
            recyclerViewAdapter = DataAdapter()
            recyclerViewAdapter.setData(list)
            binding.rcv.adapter = recyclerViewAdapter
            binding.rcv.layoutManager = LinearLayoutManager(requireContext())
            binding.rcv.addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))

        }
    }
    companion object {
        @JvmStatic
        fun newInstance(param1: List<Hour>?) =
            ForecastFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(HOUR, param1 as ArrayList<Hour>)
                }
            }
    }
}