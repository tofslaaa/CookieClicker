package com.clickerhunt.cookieclicker.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class StorageBoost(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val score: Int
)