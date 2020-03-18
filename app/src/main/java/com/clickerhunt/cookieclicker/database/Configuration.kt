package com.clickerhunt.cookieclicker.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Configuration(
    @PrimaryKey(autoGenerate = false) val id: Int = 0,
    var vibrationIsOn: Boolean = false,
    var cookiesCount: Int = 0,

    val updateTime: Long = System.currentTimeMillis()
)