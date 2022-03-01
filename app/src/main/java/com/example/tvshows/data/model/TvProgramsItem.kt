package com.example.tvshows.data.model

data class TvProgramsItem(
    val _links: Links,
    val airdate: String,
    val airstamp: String,
    val airtime: String,
    val id: Int,
    val image: Image,
    val name: String,
    val number: Int,
    val rating: Rating,
    val runtime: Int,
    val season: Int,
    val score: Double,
    val show: Show,
    val summary: String,
    val type: String,
    val url: String
)