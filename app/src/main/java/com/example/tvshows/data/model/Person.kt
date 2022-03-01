package com.example.tvshows.data.model

data class Person(
    val _links: Links,
    val birthday: Any,
    val country: Any,
    val deathday: Any,
    val gender: String,
    val id: Int,
    val image: Image,
    val name: String,
    val updated: Int,
    val url: String
)