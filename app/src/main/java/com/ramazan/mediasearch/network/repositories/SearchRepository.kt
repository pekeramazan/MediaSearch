package com.ramazan.mediasearch.network.repositories

import androidx.annotation.VisibleForTesting
import com.ramazan.mediasearch.network.requests.SearchRequest
import com.ramazan.mediasearch.network.responses.SearchResponse
import com.ramazan.mediasearch.network.services.SearchService
import com.ramazan.mediasearch.network.utils.Result

interface SearchRepository {
    suspend fun search(term:String): Result<SearchResponse>

}


class DefaultSearchRepository(
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    internal val searchService: SearchService
) : SearchRepository {

    override suspend fun search(term : String): Result<SearchResponse> {
        return try {
            Result.Success(searchService.search(term))
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

}