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


class MainActivityViewModel(
    private val searchRepository: SearchRepository
) : BaseViewModel() {
    private val _searchResponse = MutableLiveData<List<SearchResponse.SearchResponseDetail>>()
    val searchResponse: LiveData<List<SearchResponse.SearchResponseDetail>> = _searchResponse

    private val _searchCount = MutableLiveData<String>()
    val searchCount: LiveData<String> = _searchCount

    private val _searchRequest = MutableLiveData<SearchRequest>()
    val searchRequest: LiveData<SearchRequest> = _searchRequest

    val text = MutableLiveData<String>("")


    fun getSearchResult(term: String) {
        setLoading(true)
        viewModelScope.launch {
            val response = searchRepository.search(term)
            when (response) {

                is Result.Success -> {
                    _searchResponse.postValue(response.data.data)
                    _searchCount.postValue(response.data.resultCount.toString())
                }
                is Result.Error -> {
                    handleException(response.exception)
                }
            }
        }
    }

    fun onClick() {
        getSearchResult(text.value.toString())
    }
}




