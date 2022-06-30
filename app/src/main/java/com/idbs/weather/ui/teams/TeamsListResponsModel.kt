package com.idbs.weather.ui.teams


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TeamsListResponsModel(
    @Json(name = "competition")
    var competition: Competition = Competition(),
    @Json(name = "count")
    var count: Int = 0,
    @Json(name = "filters")
    var filters: Filters = Filters(),
    @Json(name = "season")
    var season: Season = Season(),
    @Json(name = "teams")
    var teams: List<Team> = listOf()
) {
    @JsonClass(generateAdapter = true)
    data class Competition(
        @Json(name = "area")
        var area: Area = Area(),
        @Json(name = "code")
        var code: String = "",
        @Json(name = "id")
        var id: Int = 0,
        @Json(name = "lastUpdated")
        var lastUpdated: String = "",
        @Json(name = "name")
        var name: String = "",
        @Json(name = "plan")
        var plan: String = ""
    ) {
        @JsonClass(generateAdapter = true)
        data class Area(
            @Json(name = "id")
            var id: Int = 0,
            @Json(name = "name")
            var name: String = ""
        )
    }

    @JsonClass(generateAdapter = true)
    class Filters

    @JsonClass(generateAdapter = true)
    data class Season(
        @Json(name = "currentMatchday")
        var currentMatchday: Int = 0,
        @Json(name = "endDate")
        var endDate: String = "",
        @Json(name = "id")
        var id: Int = 0,
        @Json(name = "startDate")
        var startDate: String = "",
        @Json(name = "winner")
        var winner: Any? = null
    )

    @JsonClass(generateAdapter = true)
    data class Team(
        @Json(name = "address")
        var address: String = "",
        @Json(name = "area")
        var area: Area = Area(),
        @Json(name = "clubColors")
        var clubColors: String? = null,
        @Json(name = "crestUrl")
        var crestUrl: String? = null,
        @Json(name = "email")
        var email: String? = null,
        @Json(name = "founded")
        var founded: Int = 0,
        @Json(name = "id")
        var id: Int = 0,
        @Json(name = "lastUpdated")
        var lastUpdated: String = "",
        @Json(name = "name")
        var name: String = "",
        @Json(name = "phone")
        var phone: String? = null,
        @Json(name = "shortName")
        var shortName: String = "",
        @Json(name = "tla")
        var tla: String? = null,
        @Json(name = "venue")
        var venue: String? = "",
        @Json(name = "website")
        var website: String = ""
    ) {
        @JsonClass(generateAdapter = true)
        data class Area(
            @Json(name = "id")
            var id: Int = 0,
            @Json(name = "name")
            var name: String = ""
        )
    }
}