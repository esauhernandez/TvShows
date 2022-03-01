package com.example.tvshows.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
class QuerySearch(
    @PrimaryKey val id: String,

    val title: String,

    var timestamp: Long
)