package com.clickerhunt.cookieclicker.cookie

import androidx.lifecycle.MutableLiveData
import com.clickerhunt.cookieclicker.database.AppDatabase
import io.reactivex.Observable
import io.reactivex.disposables.Disposables
import io.reactivex.rxkotlin.Singles
import io.reactivex.rxkotlin.withLatestFrom
import java.util.concurrent.TimeUnit

object CookieManager {

    private var disposable = Disposables.disposed()
    val addedCookies = MutableLiveData<Int>()

    fun initialize() {
        val disposable = Singles.zip(
            AppDatabase.instance.usedBoostsDao().getUsedBoostsRx().firstOrError(),
            AppDatabase.instance.configurationDao().getConfigurationRx().firstOrError()
        ).subscribe { (boosts, configuration) ->
            if (boosts.isNotEmpty()) {
                val sum = boosts.sumBy { it.score }
                val times = (System.currentTimeMillis() - configuration.updateTime) / 10000
                val addedCookies = sum * times.toInt()
                AppDatabase.instance.configurationDao()
                    .upsert(configuration.copy(cookiesCount = configuration.cookiesCount + addedCookies))
            }
        }
    }

    fun start() {
        val configurationDao = AppDatabase.instance.configurationDao()
        disposable = Observable.interval(10, TimeUnit.SECONDS)
            .withLatestFrom(
                AppDatabase.instance.usedBoostsDao().getUsedBoostsRx(),
                configurationDao.getConfigurationRx()
            )
            .filter { (_, boosts, _) -> boosts.any { !it.empty } }
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