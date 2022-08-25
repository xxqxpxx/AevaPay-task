package com.aevapay.android.task.data.repo

import android.util.Log
import com.aevapay.android.task.data.response.RepoResponseItem
import com.aevapay.android.task.network.ApiService
import com.aevapay.android.task.network.Constants.NUMBER_OF_REPOS_PER_PAGE
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ReposRepository @Inject constructor(private val apiService: ApiService){

    private val TAG = "Planetary Repository"


    fun fetchPictures(page : Int): Flow<retrofit2.Response<List<RepoResponseItem>>> {
        return flow {
            val response = apiService.getRepository(NUMBER_OF_REPOS_PER_PAGE , page)
            Log.i(TAG, "fetchMain response $response")
            emit(response)
        }
    }

    fun sortByTitle(list: List<RepoResponseItem>): Flow<List<RepoResponseItem>>{
        return flow {
         //   emit(list.sortedBy { it.title })
        }
    }

    fun sortByDate(list: List<RepoResponseItem>): Flow<List<RepoResponseItem>>{
        return flow {
        //    emit(list.sortedBy { it.date })
        }
    }

}