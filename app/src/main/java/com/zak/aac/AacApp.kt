package com.zak.aac

import android.app.Application
import timber.log.Timber

/**
 *
 * @author zak
 * @date 2021/7/19
 * @email linhenji@163.com / linhenji17@gmail.com
 */
class AacApp: Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}