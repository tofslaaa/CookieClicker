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
        private var _instance: AppDatabase? = null
        val instance: AppDatabase get() = _instance!!
        private val LOCK = Any()

        operator fun invoke(context: Context) = _instance ?: synchronized(LOCK) {
            _instance ?: buildDatabase(context).also { _instance = it }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context,
            AppDatabase::class.java, "cookies.db"
        ).allowMainThreadQueries()
            .addCallback(object : Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    thread {
                        val dao = _instance!!.usedBoostsDao()
                        val configurationDao = _instance!!.configurationDao()
                        if (BuildConfig.DEBUG) {
                            configurationDao.upsert(Configuration(cookiesCount = 1000))
                        } else{
                            configurationDao.upsert(Configuration())
                        }
                        (1..3).map { UsedBoost(score = 0, empty = true) }.forEach { dao.insert(it) }
                    }
                }
            })
            .build()
    }


}