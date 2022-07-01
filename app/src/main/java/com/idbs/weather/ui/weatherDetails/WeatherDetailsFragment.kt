package com.idbs.weather.ui.weatherDetails

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.idbs.weather.R
import com.idbs.weather.base.NetworkState
import com.idbs.weather.base.ProgressLoading
import com.idbs.weather.databinding.CitesFragmentBinding
import com.idbs.weather.databinding.WeatherDetailsFragmentBinding
import com.idbs.weather.ui.cites.CitesViewModel
import com.idbs.weather.ui.cites.CitiesModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

@AndroidEntryPoint
class WeatherDetailsFragment : Fragment() {

    private var _binding: WeatherDetailsFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<WeatherDetailsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = WeatherDetailsFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        setHasOptionsMenu(true)
    arguments?.getString("cityName")?.let {
        observeWeather(it)
    }

        return binding.root
    }
    private fun observeWeather(cityName:String){
        viewModel.cityWeatherRequest(cityName)
        lifecycleScope.launchWhenCreated {
            @OptIn(ExperimentalCoroutinesApi::class)
            viewModel.cityWeatherLoadingState.collect {
                if (it != null) {
                    when (it.status) {
                        NetworkState.Status.FAILED -> {
                            val message:String = if (!it.msg.isNullOrEmpty()) it.msg else getString(R.string.no_connection)
                          //  binding.error.visibility=View.VISIBLE
                            //binding.showError.text = message
                            ProgressLoading.dismiss()
                            Toast.makeText(requireContext(),message, Toast.LENGTH_SHORT).show()
                            viewModel.emptyNetWorkStateValue()
                        }
                        NetworkState.Status.RUNNING -> {
                            ProgressLoading.show(requireActivity())
                          //  binding.error.visibility=View.GONE
                        }
                        NetworkState.Status.SUCCESS -> {
                            ProgressLoading.dismiss()
                            if (it.data is CityWeatherResponseModel){

                                it.data.let {it ->
                                    if (it.weather.isNotEmpty()){
                                        binding.descriptionValue.text = it.weather[0].description?: ""
                                    }
                                    binding.temperatureValue.text = convertToCelsius(it.main.temp) ?: ""
                                    val humidityPercentage = "${it.main.humidity}%"
                                    binding.humidityValue.text = humidityPercentage
                                    binding.windspeedValue.text = it.wind.speed.toString()
                                    binding.cityName.text = it.name
                                    val subHeader = getString(R.string.weather_sub_title, it.name,showDate(it.dt.toLong()))
                                    binding.subHeader.text = subHeader


                                    // binding.error.visibility=View.VISIBLE
                                        //binding.showError.text = getString(R.string.no_data_founded)

                                }
                            }
                            viewModel.emptyNetWorkStateValue()
                        }
                    }
                }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
fun convertToCelsius(kelvinTemp:Double):String{
    var convertResult = (kelvinTemp - 273.15)
    val df = DecimalFormat("#")
    df.roundingMode = RoundingMode.CEILING
   convertResult =  df.format(convertResult).toDouble()
    Log.v("temp","$kelvinTemp :  ${(kelvinTemp - 273.15)}")
    return "$convertResult C"
}

fun showDate(dateLong:Long):String{
    val dt = Instant.ofEpochSecond(dateLong)
        .atZone(ZoneId.systemDefault())
        .toLocalDateTime()

    val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy - HH:mm")
    val output = formatter.format(dt)

    //   Log.d("parseTesting", date.month.toString()) // prints August
    Log.v("time"," is: ${output}")
    return output
}
}