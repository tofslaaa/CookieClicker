package com.clickerhunt.cookieclicker.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UsedBoost(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val score: Int,
    val empty: Boolean,
    val updateTime: Long = System.currentTimeMillis()
)