package com.idbs.weather.ui.league


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LeagueResponsModel(
    @Json(name = "competitions")
    var competitions: List<Competition> = listOf(),
    @Json(name = "count")
    var count: Int = 0,
    @Json(name = "filters")
    var filters: Filters = Filters()
) {
    @JsonClass(generateAdapter = true)
    data class Competition(
        @Json(name = "area")
        var area: Area = Area(),
        @Json(name = "code")
        var code: String? = "-",
        @Json(name = "currentSeason")
        var currentSeason: CurrentSeason? = null,
        @Json(name = "emblemUrl")
        var emblemUrl: Any? = null,
        @Json(name = "id")
        var id: Int = 0,
        @Json(name = "lastUpdated")
        var lastUpdated: String = "",
        @Json(name = "name")
        var name: String = "",
        @Json(name = "numberOfAvailableSeasons")
        var numberOfAvailableSeasons: Int = 0,
        @Json(name = "plan")
        var plan: String = ""
    ) {
        @JsonClass(generateAdapter = true)
        data class Area(
            @Json(name = "countryCode")
            var countryCode: String = "",
            @Json(name = "ensignUrl")
            var ensignUrl: Any? = null,
            @Json(name = "id")
            var id: Int = 0,
            @Json(name = "name")
            var name: String = ""
        )

        @JsonClass(generateAdapter = true)
        data class CurrentSeason(
            @Json(name = "currentMatchday")
            var currentMatchday: Int? = null,
            @Json(name = "endDate")
            var endDate: String = "",
            @Json(name = "id")
            var id: Int = 0,
            @Json(name = "startDate")
            var startDate: String = "",
            @Json(name = "winner")
            var winner: Any? = null
        )
    }

    @JsonClass(generateAdapter = true)
    class Filters
}