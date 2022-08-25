package com.aevapay.android.task.data.repo

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.aevapay.android.task.data.response.RepoResponseItem
import kotlinx.coroutines.flow.catch

class RepoItemDataSource(
    private val dataRepository: ReposRepository
) : PagingSource<Int, RepoResponseItem>() {


    companion object {
        private const val STARTING_PAGE_INDEX = 1
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RepoResponseItem> {
        val position = params.key ?: STARTING_PAGE_INDEX
        var list: List<RepoResponseItem> = emptyList()
        val result = dataRepository.fetchPictures(position)

        try {
            result.collect { response ->
                list = response.body() ?: emptyList()
                LoadResult.Page(
                    data = list,
                    prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                    nextKey = if (list.isEmpty()) null else position + 1
                )
            }

            return LoadResult.Page(
                list,
                prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (list.isEmpty()) null else position + 1
            )

        }catch (e:Exception){
          return  LoadResult.Error( e)

        }
    }

    override fun getRefreshKey(state: PagingState<Int, RepoResponseItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}
