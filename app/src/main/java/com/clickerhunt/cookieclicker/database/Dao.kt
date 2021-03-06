package com.clickerhunt.cookieclicker.database

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.IGNORE
import io.reactivex.Observable

@Dao
interface ConfigurationDao {

    fun upsert(configuration: Configuration) {
        upsertWithTimestamp(configuration.copy(updateTime = System.currentTimeMillis()))
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsertWithTimestamp(configuration: Configuration)

    @Query("UPDATE configuration SET cookiesCount = cookiesCount + :added, updateTime=:timeMillis")
    fun addCookiesCount(added: Int, timeMillis:Long = System.currentTimeMillis())

    @Query("UPDATE configuration SET vibrationIsOn = :enabled, updateTime=:timeMillis")
    fun enableVibration(enabled: Boolean, timeMillis:Long = System.currentTimeMillis())

    @Query("SELECT * FROM configuration WHERE id = 0")
    fun getConfiguration(): LiveData<Configuration>

    @Query("SELECT * FROM configuration WHERE id = 0")
    fun getConfigurationRx(): Observable<Configuration>
}

@Dao
interface StorageBoostDao {

    @Insert(onConflict = IGNORE)
    fun insert(boost: StorageBoost)

    @Delete
    fun delete(boost: StorageBoost)

    @Query("DELETE FROM storageboost WHERE id=:id")
    fun delete(id: Int): Int

    @Update
    fun update(boost: StorageBoost)

    @Query("SELECT * FROM storageboost")
    fun getStorageBoosts(): LiveData<List<StorageBoost>>

    @Query("SELECT * FROM storageboost WHERE id = :id")
    fun getBoostById(id: Int): StorageBoost?
}

@Dao
interface UsedBoostDao {

    fun insert(boost: UsedBoost) {
        insertWithTimestamp(boost.copy(updateTime = System.currentTimeMillis()))
    }

    @Insert(onConflict = IGNORE)
    fun insertWithTimestamp(boost: UsedBoost)

    @Delete
    fun delete(boost: UsedBoost)

    fun update(boost: UsedBoost) {
        updateWithTimestamp(boost.copy(updateTime = System.currentTimeMillis()))
    }

    @Update
    fun updateWithTimestamp(boost: UsedBoost)

    @Query("SELECT * FROM usedboost ORDER BY empty ASC, updateTime ASC")
    fun getUsedBoosts(): LiveData<List<UsedBoost>>

    @Query("SELECT * FROM usedboost ORDER BY empty ASC, updateTime ASC")
    fun getUsedBoostsRx(): Observable<List<UsedBoost>>

    @Query("SELECT * FROM usedboost WHERE id = :id")
    fun getBoostById(id: Int): UsedBoost

    @Query("SELECT * FROM usedboost WHERE empty LIMIT 1")
    fun getEmptyBoost(): UsedBoost?
}