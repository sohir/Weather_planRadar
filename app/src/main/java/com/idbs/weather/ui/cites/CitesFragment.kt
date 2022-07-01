package com.idbs.weather.ui.cites

import android.app.Dialog
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.idbs.weather.R
import com.idbs.weather.base.NetworkState
import com.idbs.weather.base.ProgressLoading
import com.idbs.weather.databinding.CitesFragmentBinding
import com.idbs.weather.databinding.InsertDataDialogBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class CitesFragment : Fragment() , CitesListAdapter.ComponentActionsClickListener{

    private var _binding: CitesFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<CitesViewModel>()
    lateinit var insertCityDialog: Dialog

    val mAdapter: CitesListAdapter by lazy { CitesListAdapter(this) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = CitesFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        setHasOptionsMenu(true)
        binding.recycler.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapter
        }
        binding.fab.setOnClickListener {
            showDialog()
        }
        observeCities()
        observeInsertCity( )
        return binding.root
    }
    private fun insertCity(name:CitiesModel.Cities){
        var count = 1
        viewModel.insertCity(name)
    }
private fun observeCities(){
    lifecycleScope.launchWhenCreated {
        @OptIn(ExperimentalCoroutinesApi::class)
        viewModel.citiesLoadingState.collect {
         if (it != null) {
             when (it.status) {
                 NetworkState.Status.FAILED -> {
                     val message:String = if (!it.msg.isNullOrEmpty()) it.msg else getString(R.string.no_connection)
                     binding.error.visibility=View.VISIBLE
                     binding.showError.text = message
                     ProgressLoading.dismiss()
                     Toast.makeText(requireContext(),message,Toast.LENGTH_SHORT).show()
                     viewModel.emptyNetWorkStateValue()
                 }
                 NetworkState.Status.RUNNING -> {
                     ProgressLoading.show(requireActivity())
                     binding.error.visibility=View.GONE
                 }
                 NetworkState.Status.SUCCESS -> {
                    ProgressLoading.dismiss()
                     if (it.data is List<*>){
                         Log.v("data"," is ${it.data}")
                         it.data.let {list ->
                             if (list.isEmpty()){
                                 binding.recycler.visibility = View.GONE
                                 binding.error.visibility=View.VISIBLE
                                 binding.showError.text = getString(R.string.no_data_founded)
                             }else {
                                 mAdapter.updateDate(list as ArrayList<CitiesModel.Cities>)
                                 binding.recycler.visibility = View.VISIBLE
                             }
                         }
                     }
                     viewModel.emptyNetWorkStateValue()
                 }
             }
         }
     }
    }

}
    private fun observeInsertCity(){
        lifecycleScope.launchWhenCreated {
            viewModel.insertCityLoadingState.collect {
                if (it != null) {
                    when (it.status) {
                        NetworkState.Status.FAILED -> {
                            val message:String = if (!it.msg.isNullOrEmpty()) it.msg else getString(R.string.no_connection)
                            binding.error.visibility=View.VISIBLE
                            binding.showError.text = message
                            ProgressLoading.dismiss()
                            Toast.makeText(requireContext(),message,Toast.LENGTH_SHORT).show()
                            viewModel.emptyNetWorkStateValue()
                        }
                        NetworkState.Status.RUNNING -> {
                            ProgressLoading.show(requireActivity())
                            binding.error.visibility=View.GONE
                        }
                        NetworkState.Status.SUCCESS -> {
                            ProgressLoading.dismiss()
                            viewModel.citiesRequest()
                            viewModel.emptyNetWorkStateValue()
                        }
                    }
                }
            }
        }

    }
    override fun onItemClicked(v: View, item: CitiesModel.Cities) {
        Toast.makeText(requireContext(),item.name,Toast.LENGTH_SHORT).show()
        Log.v("click"," item clicked ${item}")
        findNavController().navigate(CitesFragmentDirections.actionCitesFragmentToWeatherDetailsFragment(item.name))

    }

    override fun onInfoClicked(v: View, item: CitiesModel.Cities) {
        Log.v("click"," info clicked")
        Toast.makeText(requireContext(),"id: ${item.id}",Toast.LENGTH_SHORT).show()
    }
    fun showDialog() {
        insertCityDialog = Dialog(requireContext(), R.style.CustomDialog)
        val bindingDialog: InsertDataDialogBinding = DataBindingUtil.inflate(
            LayoutInflater.from(requireContext()),
            R.layout.insert_data_dialog, null, false
        )
        insertCityDialog.setContentView(bindingDialog.root)
        bindingDialog.lifecycleOwner = this
        insertCityDialog.setCancelable(false)
        insertCityDialog.show()
        insertCityDialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        bindingDialog.insertBtn.setOnClickListener {
            val cityName = bindingDialog.nameEt.text?.toString()?.trim()
            if (cityName.isNullOrEmpty())
                bindingDialog.nameTextField.error = resources.getString(R.string.required_field)
            else
                insertCity(CitiesModel.Cities(name = cityName))
            bindingDialog.nameTextField.error = null
            insertCityDialog.dismiss()

        }
        bindingDialog.closeDialog.setOnClickListener {
            insertCityDialog.dismiss()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}