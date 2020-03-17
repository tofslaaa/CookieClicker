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
interface StorageBoostDao {

    @Insert
    fun insert(boost: StorageBoost)

    @Delete
    fun delete(boost: StorageBoost)

    @Update
    fun update(boost: StorageBoost)

    @Query("SELECT * FROM storageboost")
    fun getStorageBoosts(): List<StorageBoost>

    @Query("SELECT * FROM storageboost WHERE id = :id")
    fun getBoostById(id: Int): StorageBoost
}

@Dao
interface UsedBoostDao {

    @Insert
    fun insert(boost: UsedBoost)

    @Delete
    fun delete(boost: UsedBoost)

    @Update
    fun update(boost: UsedBoost)

    @Query("SELECT * FROM usedboost")
    fun getUsedBoosts(): List<UsedBoost>

    @Query("SELECT * FROM usedboost WHERE id = :id")
    fun getBoostById(id: Int): UsedBoost
}