package com.clickerhunt.cookieclicker.cookie

import androidx.lifecycle.MutableLiveData
import com.clickerhunt.cookieclicker.database.AppDatabase
import io.reactivex.Observable
import io.reactivex.disposables.Disposables
import io.reactivex.rxkotlin.withLatestFrom
import java.util.concurrent.TimeUnit

object CookieManager {

    private var disposable = Disposables.disposed()
    val addedCookies = MutableLiveData<Int>()

    fun start() {
        val configurationDao = AppDatabase.instance.configurationDao()
        disposable = Observable.interval(1, TimeUnit.SECONDS)
            .withLatestFrom(
                AppDatabase.instance.usedBoostsDao().getUsedBoostsRx(),
                configurationDao.getConfigurationRx()
            )
            .subscribe { (_, boosts, configuration) ->
                val summary = boosts.sumBy { it.score }
                addedCookies.postValue(summary)
                configurationDao.upsert(configuration.copy(cookiesCount = configuration.cookiesCount + summary))
            }
    }

    fun stop() {
        disposable.dispose()
    }

}