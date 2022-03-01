package com.example.tvshows.data.model

data class Character(
    val _links: Links,
    val id: Int,
    val image: Image,
    val name: String,
    val url: String
)