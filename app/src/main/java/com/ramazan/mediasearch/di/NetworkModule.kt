package com.ramazan.mediasearch.di

import com.ramazan.mediasearch.network.utils.NetworkClient
import org.koin.core.module.Module
import org.koin.dsl.module

val networkModule: Module =module{
    single { NetworkClient.provideSearchService(get()) }
    single { NetworkClient.provideClient() }

}