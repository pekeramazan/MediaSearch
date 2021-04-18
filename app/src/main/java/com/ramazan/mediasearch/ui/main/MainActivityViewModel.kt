package com.ramazan.mediasearch.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.ramazan.mediasearch.core.BaseViewModel
import com.ramazan.mediasearch.network.repositories.SearchRepository
import com.ramazan.mediasearch.network.requests.SearchRequest
import com.ramazan.mediasearch.network.responses.SearchResponse
import kotlinx.coroutines.launch
import com.ramazan.mediasearch.network.utils.Result


class MainActivityViewModel() : BaseViewModel()




