package com.ramazan.mediasearch.core

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import com.ramazan.mediasearch.BuildConfig
import com.ramazan.mediasearch.di.networkModule
import com.ramazan.mediasearch.di.repositoryModule
import com.ramazan.mediasearch.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level


class MediaSearchApplication: Application() {
    override fun onCreate() {
        super.onCreate()


        initKoin()


    }
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }
    private fun initKoin() {
        startKoin {
            androidLogger(if (BuildConfig.DEBUG) Level.DEBUG else Level.INFO)
            androidContext(this@MediaSearchApplication)
            modules(
                listOf(
                    viewModelModule,
                    repositoryModule,
                   networkModule
                )
            )
        }
    }

}