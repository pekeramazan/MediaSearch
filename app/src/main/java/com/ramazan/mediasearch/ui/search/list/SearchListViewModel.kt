package com.ramazan.mediasearch.ui.search.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ramazan.mediasearch.core.BaseViewModel
import com.ramazan.mediasearch.data.Event
import com.ramazan.mediasearch.network.repositories.SearchRepository
import com.ramazan.mediasearch.network.responses.SearchResponse
import com.ramazan.mediasearch.network.utils.Result
import kotlinx.coroutines.launch

class SearchListViewModel(private val searchRepository: SearchRepository) : BaseViewModel() {

    val _event = MutableLiveData<Event<SearchListViewEvent>>()
    val event: LiveData<Event<SearchListViewEvent>> = _event

    val searchText = MutableLiveData<String>("")

    private val _searchResponse = MutableLiveData<List<SearchResponse.SearchResponseDetail>>()
    val searchResponse: LiveData<List<SearchResponse.SearchResponseDetail>> = _searchResponse

    private val _filteredList = MutableLiveData<List<SearchResponse.SearchResponseDetail>>()
    val filteredList: LiveData<List<SearchResponse.SearchResponseDetail>> = _filteredList

    private val _searchCount = MutableLiveData<String>()
    val searchCount: LiveData<String> = _searchCount

    private val _empty = MutableLiveData<Boolean>(false)
    val empty: LiveData<Boolean>
        get() = _empty

    fun sendEvent(event: SearchListViewEvent) {
        _event.value = Event(event)
    }

    private fun checkCharacterLimit(): Boolean {
        var result: Boolean = true

        if (searchText.value.isNullOrEmpty()) {
            sendEvent(SearchListViewEvent.ShowInputError(SearchListInputEnums.SEARCH_TEXT_EMPTY))
            result = false

        } else if (searchText.value!!.length < 2) {
            sendEvent(SearchListViewEvent.ShowInputError(SearchListInputEnums.SEARCH_TEXT_CHR))
            result = false

        }
        return result
    }

    fun getSearchResult(term: String) {
        if (checkCharacterLimit()) {
            setLoading(true)
            viewModelScope.launch {
                val response = searchRepository.search(term)
                when (response) {

                    is Result.Success -> {
                        setLoading(false)
                        _searchResponse.postValue(response.data.data)
                        _searchCount.postValue(response.data.resultCount.toString())
                        if (response.data.data.size == 0) {
                            _empty.postValue(true)
                        } else {
                            _empty.postValue(false)

                        }
                    }
                    is Result.Error -> {
                        setLoading(false)
                        sendEvent(SearchListViewEvent.Error)
                        handleException(response.exception)
                    }
                }
            }
        }

    }

    fun onSearch() {

        getSearchResult(searchText.value.toString())

    }

    fun setSearchText(text: String) {
        text.let {
            searchText.value = it
        }

    }

    fun filterButtons(filter: SearchCategoryEnums.Categories){
      if(!_searchResponse.value.isNullOrEmpty()) {
          when(filter){
              SearchCategoryEnums.Categories.MOVIE ->{
                  sendEvent(SearchListViewEvent.Filter(SearchCategoryEnums.Categories.MOVIE))

              }
              SearchCategoryEnums.Categories.MUSIC ->{
                  sendEvent(SearchListViewEvent.Filter(SearchCategoryEnums.Categories.MUSIC))

              }
              SearchCategoryEnums.Categories.APPS ->{
                  sendEvent(SearchListViewEvent.Filter(SearchCategoryEnums.Categories.APPS))

              }
              SearchCategoryEnums.Categories.BOOK ->{
                  sendEvent(SearchListViewEvent.Filter(SearchCategoryEnums.Categories.BOOK))

              }
          }
      }






    }

}