package com.ramazan.mediasearch.network.services

import com.ramazan.mediasearch.network.requests.SearchRequest
import com.ramazan.mediasearch.network.responses.SearchResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {
    @GET("search")
    suspend fun search(
        @Query("term") term :String
    ): SearchResponse
}