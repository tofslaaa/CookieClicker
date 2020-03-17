package com.clickerhunt.cookieclicker.database

import androidx.room.*

@Dao
interface ConfigurationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(configuration: Configuration)

    @Query("SELECT * FROM configuration WHERE id = 0")
    fun getConfiguration(): Configuration?
}

@Dao
interface StorageBoostsDao {

    @Insert
    fun insert(boost: StorageBoosts)

    @Delete
    fun delete(boost: StorageBoosts)

    @Update
    fun update(boost: StorageBoosts)

    @Query("SELECT * FROM storageboosts")
    fun getStorageBoosts(): List<StorageBoosts>
}

@Dao
interface UsedBoostsDao {

    @Insert
    fun insert(boost: UsedBoosts)

    @Delete
    fun delete(boost: UsedBoosts)

    @Update
    fun update(boost: UsedBoosts)

    @Query("SELECT * FROM usedboosts")
    fun getStorageBoosts(): List<UsedBoosts>
}