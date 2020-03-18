package com.clickerhunt.cookieclicker.database

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.IGNORE

@Dao
interface ConfigurationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(configuration: Configuration)

    @Query("SELECT * FROM configuration WHERE id = 0")
    fun getConfiguration(): LiveData<Configuration>
}

@Dao
interface StorageBoostDao {

    @Insert(onConflict = IGNORE)
    fun insert(boost: StorageBoost)

    @Delete
    fun delete(boost: StorageBoost)

    @Query("DELETE FROM storageboost WHERE id=:id")
    fun delete(id: Int)

    @Update
    fun update(boost: StorageBoost)

    @Query("SELECT * FROM storageboost")
    fun getStorageBoosts(): LiveData<List<StorageBoost>>

    @Query("SELECT * FROM storageboost WHERE id = :id")
    fun getBoostById(id: Int): StorageBoost
}

@Dao
interface UsedBoostDao {

    @Insert(onConflict = IGNORE)
    fun insert(boost: UsedBoost)

    @Delete
    fun delete(boost: UsedBoost)

    @Update
    fun update(boost: UsedBoost)

    @Query("SELECT * FROM usedboost ORDER BY empty ASC, updateTime ASC")
    fun getUsedBoosts(): LiveData<List<UsedBoost>>

    @Query("SELECT * FROM usedboost WHERE id = :id")
    fun getBoostById(id: Int): UsedBoost

    @Query("SELECT * FROM usedboost WHERE empty LIMIT 1")
    fun getEmptyBoost(): UsedBoost?
}