package com.ramazan.mediasearch.di


import androidx.lifecycle.SavedStateHandle
import com.ramazan.mediasearch.ui.main.MainActivityViewModel
import com.ramazan.mediasearch.ui.search.detail.SearchDetailViewModel
import com.ramazan.mediasearch.ui.search.list.SearchListViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
@ExperimentalCoroutinesApi
val viewModelModule=module{
    // Main
    viewModel { MainActivityViewModel() }
    viewModel { SearchListViewModel(get()) }
    viewModel { SearchDetailViewModel() }



}