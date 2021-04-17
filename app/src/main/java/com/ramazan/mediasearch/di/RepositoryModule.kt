package com.ramazan.mediasearch.di

import com.ramazan.mediasearch.network.repositories.DefaultSearchRepository
import com.ramazan.mediasearch.network.repositories.SearchRepository
import org.koin.core.module.Module
import org.koin.dsl.module

val repositoryModule: Module =module {
    single<SearchRepository> { DefaultSearchRepository(get()) }

}