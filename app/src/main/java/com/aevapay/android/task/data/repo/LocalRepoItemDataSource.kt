package com.aevapay.android.task.data.repo

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.aevapay.android.task.data.response.RepoResponseItem


class LocalRepoItemDataSource(
    private val dataRepository: LocalFavouriteReposRepository
) : PagingSource<Int, RepoResponseItem>() {




    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RepoResponseItem> {

        var list: List<RepoResponseItem> = dataRepository.getFavouriteReposList()

        return LoadResult.Page(
                list,
                prevKey = 0,
                nextKey = 1
            )

    }

    override fun getRefreshKey(state: PagingState<Int, RepoResponseItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override val keyReuseSupported: Boolean = true
}
