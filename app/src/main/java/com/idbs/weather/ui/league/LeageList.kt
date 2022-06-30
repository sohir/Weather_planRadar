package com.idbs.weather.ui.league

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager

import com.idbs.weather.R
import com.idbs.weather.databinding.FragmentLeageListBinding
import com.idbs.weather.network.LoadingState
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LeageList : Fragment(R.layout.fragment_leage_list) ,LeagueListAdapter.ComponentActionsClickListener{
    lateinit var binding: FragmentLeageListBinding
    private val viewModel by viewModels<LeagueViewModel>()
    lateinit var mAdapter:LeagueListAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding =FragmentLeageListBinding.bind(view)
        setHasOptionsMenu(true)
        observeLeagueList()
        mAdapter = LeagueListAdapter(this)
        binding.recycler.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(context)
        }

    }
    private fun observeLeagueList(){
        viewModel.leagueList.observe(viewLifecycleOwner,{
            it.let {
                if (!it.competitions.isNullOrEmpty()){
                    mAdapter.updateDate(it.competitions)
                }
                Log.v("league"," league list ${it.competitions}")
            }
        })
        viewModel.networkState.observe(viewLifecycleOwner,  {
            if (it != null) {
                Log.v("league", "from loading state ${it.status}")
                when (it.status) {
                    LoadingState.Status.FAILED -> {
                        binding.recycler.visibility = View.GONE
                        binding.loader.visibility = View.VISIBLE
                        binding.error.visibility = View.VISIBLE
                        val message =
                            if (it.errorResponse?.message.isNullOrEmpty()) it.msg else it.errorResponse?.message
                            Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
                        binding.showError.text = message
                        viewModel.emptyLoadingState()
                    }
                    LoadingState.Status.RUNNING -> {
                        binding.recycler.visibility = View.GONE
                        binding.loader.visibility = View.GONE
                        binding.error.visibility = View.GONE
                    }
                    LoadingState.Status.SUCCESS -> {
                        binding.recycler.visibility = View.VISIBLE
                        binding.loader.visibility = View.GONE
                        binding.error.visibility = View.GONE
                    }
                }
            }
        })
    }

    override fun onItemClicked(v: View, item: LeagueResponsModel.Competition) {
        findNavController().navigate(LeageListDirections.actionLeageListToTeamsList(item.id.toString()))
    }

}