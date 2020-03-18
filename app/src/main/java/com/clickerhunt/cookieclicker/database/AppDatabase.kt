package com.clickerhunt.cookieclicker.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.clickerhunt.cookieclicker.BuildConfig
import kotlin.concurrent.thread

@Database(
    entities = [Configuration::class, StorageBoost::class, UsedBoost::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun configurationDao(): ConfigurationDao
    abstract fun storageBoostsDao(): StorageBoostDao
    abstract fun usedBoostsDao(): UsedBoostDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context,
            AppDatabase::class.java, "cookies.db"
        ).allowMainThreadQueries()
            .addCallback(object : Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    thread {
                        val dao = instance!!.usedBoostsDao()
                        val configurationDao = instance!!.configurationDao()
                        if (BuildConfig.DEBUG) {
                            configurationDao.upsert(Configuration(cookiesCount = 1000))
                        }
                        (1..3).map { UsedBoost(score = 0, empty = true) }.forEach { dao.insert(it) }
                    }
                }
            })
            .build()
    }


}