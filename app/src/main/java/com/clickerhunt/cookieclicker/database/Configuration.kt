package com.clickerhunt.cookieclicker.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Configuration(
    @PrimaryKey(autoGenerate = false) val id: Int = 0,
    var soundIsOn: Boolean = false,
    var vibrationIsOn: Boolean = false,
    var cookiesCount: Int = 0
)