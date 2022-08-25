package com.aevapay.android.task.ui.reposlanding

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.aevapay.android.task.base.BaseViewModel
import com.aevapay.android.task.data.helper.ComplexPreferencesImpl
import com.aevapay.android.task.data.repo.LocalFavouriteReposRepository
import com.aevapay.android.task.data.repo.LocalRepoItemDataSource
import com.aevapay.android.task.data.repo.RepoItemDataSource
import com.aevapay.android.task.data.repo.ReposRepository
import com.aevapay.android.task.data.response.RepoResponseItem
import com.aevapay.android.task.network.Constants.NUMBER_OF_REPOS_PER_PAGE
import com.aevapay.android.task.network.ResultModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ReposLandingViewModel @Inject constructor(private val repository: ReposRepository) :
    BaseViewModel() {


    private val _reposDataObserver = MutableLiveData<ResultModel<List<RepoResponseItem>>>()
    val reposDataObserver: LiveData<ResultModel<List<RepoResponseItem>>> = _reposDataObserver

    private val _reposFavDataObserver = MutableLiveData<ArrayList<RepoResponseItem>>()
    val reposDataObserverFav: LiveData<ArrayList<RepoResponseItem>> = _reposFavDataObserver


    var list: List<RepoResponseItem> = arrayListOf()


    var _repoListPaging = Pager(PagingConfig(NUMBER_OF_REPOS_PER_PAGE)) {
        RepoItemDataSource(repository)
    }.flow.cachedIn(viewModelScope).asLiveData()

    lateinit var _favRepoList: LiveData<PagingData<RepoResponseItem>>


    private fun fetchPicturesData2() {
        viewModelScope.launch {
            _reposDataObserver.postValue(ResultModel.Loading(isLoading = true))

            _repoListPaging = Pager(PagingConfig(NUMBER_OF_REPOS_PER_PAGE)) {
                RepoItemDataSource(repository)
            }.flow.cachedIn(viewModelScope).asLiveData()

            _reposDataObserver.postValue(ResultModel.Loading(isLoading = false))

        }
    }


    fun refresh() {
        fetchPicturesData2()
    }

    private lateinit var localFavouriteReposRepository: LocalFavouriteReposRepository

    fun setComplexPref(complexPreferences: ComplexPreferencesImpl) {
        localFavouriteReposRepository = LocalFavouriteReposRepository(complexPreferences)
    }


    fun handleIfAlreadyFavourite() {
        viewModelScope.launch {

            var list = localFavouriteReposRepository.getFavouriteReposList()
            if (!list.isEmpty()) {
                updateFaviconandstatus(list)
            }

        }
    }

    private fun updateFaviconandstatus(list: ArrayList<RepoResponseItem>) {
        _reposFavDataObserver.postValue(list)

        _favRepoList = Pager(PagingConfig(localFavouriteReposRepository.getFavouriteReposList().size)) {
            LocalRepoItemDataSource(localFavouriteReposRepository)
        }.flow.cachedIn(viewModelScope).asLiveData()

    }
}