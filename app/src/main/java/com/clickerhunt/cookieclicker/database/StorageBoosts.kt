package com.clickerhunt.cookieclicker.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class StorageBoosts(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val score: Int
)