package com.aevapay.android.task.network

import com.aevapay.android.task.data.response.AstronomyResponse
import com.aevapay.android.task.data.response.RepoResponseItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET(Constants.REPOS_URL)
    suspend fun getRepository(
        @Query("per_page") per_page: Int?,
        @Query("page") page: Int?,
    ): Response<List<RepoResponseItem>>

}