package com.idbs.weather.ui.teams

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.idbs.weather.R
import com.idbs.weather.databinding.FragmentTeamsListBinding
import com.idbs.weather.network.LoadingState

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TeamsList : Fragment(R.layout.fragment_teams_list),TeamsListAdapter.ComponentActionsClickListener {
    lateinit var binding: FragmentTeamsListBinding
    private val viewModel by viewModels<TeamViewModel>()
    private val args :TeamsListArgs by navArgs()
    lateinit var mAdapter: TeamsListAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding =FragmentTeamsListBinding.bind(view)
        setHasOptionsMenu(true)
        observeTeamList()
        mAdapter = TeamsListAdapter(this)
        binding.recycler.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(context)
        }

    }
    private fun observeTeamList(){
        viewModel.teamRequest(args.leagueId)
        viewModel.teamList.observe(viewLifecycleOwner,{
            it.let {
                if (!it.teams.isNullOrEmpty()){
                    mAdapter.updateDate(it.teams)
                }
                Log.v("league"," team list ${it.teams.size}")

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

    override fun onItemClicked(v: View, item: TeamsListResponsModel.Team) {
       Log.v("team"," selected on os ${item.name} go to its details...")
    }

}