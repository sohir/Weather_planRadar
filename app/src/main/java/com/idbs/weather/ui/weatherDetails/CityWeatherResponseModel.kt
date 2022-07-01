package com.idbs.weather.ui.weatherDetails


import com.idbs.weather.utilit.UrlsConstants.IMG_BASE_URL
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@JsonClass(generateAdapter = true)
data class CityWeatherResponseModel(
    @Json(name = "base")
    var base: String = "",
    @Json(name = "clouds")
    var clouds: Clouds = Clouds(),
    @Json(name = "cod")
    var cod: Int = 0,
    @Json(name = "coord")
    var coord: Coord = Coord(),
    @Json(name = "dt")
    var dt: Int = 0,
    @Json(name = "id")
    var id: Int = 0,
    @Json(name = "main")
    var main: Main = Main(),
    @Json(name = "name")
    var name: String = "",
    @Json(name = "sys")
    var sys: Sys = Sys(),
    @Json(name = "timezone")
    var timezone: Int = 0,
    @Json(name = "visibility")
    var visibility: Int = 0,
    @Json(name = "weather")
    var weather: List<Weather> = listOf(),
    @Json(name = "wind")
    var wind: Wind = Wind()
) {
    @JsonClass(generateAdapter = true)
    data class Clouds(
        @Json(name = "all")
        var all: Int = 0
    )

    @JsonClass(generateAdapter = true)
    data class Coord(
        @Json(name = "lat")
        var lat: Double = 0.0,
        @Json(name = "lon")
        var lon: Double = 0.0
    )

    @JsonClass(generateAdapter = true)
    data class Main(
        @Json(name = "feels_like")
        var feelsLike: Double = 0.0,
        @Json(name = "humidity")
        var humidity: Int = 0,
        @Json(name = "pressure")
        var pressure: Int = 0,
        @Json(name = "temp")
        var temp: Double = 0.0,
        @Json(name = "temp_max")
        var tempMax: Double = 0.0,
        @Json(name = "temp_min")
        var tempMin: Double = 0.0
    )

    @JsonClass(generateAdapter = true)
    data class Sys(
        @Json(name = "country")
        var country: String = "",
        @Json(name = "id")
        var id: Int = 0,
        @Json(name = "sunrise")
        var sunrise: Int = 0,
        @Json(name = "sunset")
        var sunset: Int = 0,
        @Json(name = "type")
        var type: Int = 0
    )

    @JsonClass(generateAdapter = true)
    data class Weather(
        @Json(name = "description")
        var description: String = "",
        @Json(name = "icon")
        var icon: String = "",
        @Transient
        var image : String = "$IMG_BASE_URL$icon.png",
        @Json(name = "id")
        var id: Int = 0,
        @Json(name = "main")
        var main: String = ""
    )

    @JsonClass(generateAdapter = true)
    data class Wind(
        @Json(name = "deg")
        var deg: Int = 0,
        @Json(name = "speed")
        var speed: Double = 0.0
    )
}