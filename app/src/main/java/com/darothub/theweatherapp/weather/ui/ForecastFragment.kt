package com.darothub.theweatherapp.com.darothub.theweatherapp.weather.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.darothub.theweatherapp.R
import com.darothub.theweatherapp.com.darothub.theweatherapp.core.entities.HourEntity
import com.darothub.theweatherapp.core.utils.viewBinding
import com.darothub.theweatherapp.databinding.FragmentForecastBinding


const val HOUR = "HourEntity"
class ForecastFragment : Fragment(R.layout.fragment_forecast) {
    private val binding by viewBinding(FragmentForecastBinding::bind)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        arguments?.takeIf { it.containsKey(ARG_OBJECT) }?.apply {
//            val list = getSerializable(ARG_OBJECT) as List<HourEntity>
//            recyclerViewAdapter = DataAdapter()
//            recyclerViewAdapter.setData(list)
//            binding.rcv.adapter = recyclerViewAdapter
//            binding.rcv.layoutManager = LinearLayoutManager(requireContext())
//            binding.rcv.addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))
//
//        }
    }
    companion object {
        @JvmStatic
        fun newInstance(param1: List<HourEntity>) =
            ForecastFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(HOUR, param1 as ArrayList<HourEntity>)
                }
            }
    }
}