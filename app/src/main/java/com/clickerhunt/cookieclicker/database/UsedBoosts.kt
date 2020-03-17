package com.clickerhunt.cookieclicker.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class UsedBoosts(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val score: Int
)