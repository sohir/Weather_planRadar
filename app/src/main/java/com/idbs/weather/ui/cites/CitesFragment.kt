package com.idbs.weather.ui.cites

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.idbs.weather.R

class CitesFragment : Fragment() {

    companion object {
        fun newInstance() = CitesFragment()
    }

    private lateinit var viewModel: CitesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.cites_fragment, container, false)
    }


}