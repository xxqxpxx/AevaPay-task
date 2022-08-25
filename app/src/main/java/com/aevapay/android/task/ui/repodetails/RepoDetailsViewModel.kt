package com.aevapay.android.task.ui.repodetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.aevapay.android.task.base.BaseViewModel
import com.aevapay.android.task.data.helper.ComplexPreferencesImpl
import com.aevapay.android.task.data.repo.LocalFavouriteReposRepository
import com.aevapay.android.task.data.response.RepoResponseItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RepoDetailsViewModel @Inject constructor() : BaseViewModel() {

    private val _repoFavDataObserver = MutableLiveData<Boolean>()
    val repoDataObserverFav: LiveData<Boolean> = _repoFavDataObserver

    lateinit var repo: RepoResponseItem
    var isFavRepo: Boolean = false

    private lateinit var localFavouriteReposRepository: LocalFavouriteReposRepository

    fun setComplexPref(complexPreferences: ComplexPreferencesImpl) {
        localFavouriteReposRepository = LocalFavouriteReposRepository(complexPreferences)
    }

    fun handleFavClicked(selected: Boolean) {
        isFavRepo = selected
        if (selected) {
            saveToLocalStorage()
        } else {
            removeFromLocalStorage()
        }
        _repoFavDataObserver.postValue(isFavRepo)
    }

    private fun removeFromLocalStorage() {
        viewModelScope.launch {
            localFavouriteReposRepository.removeFavouriteRepo(repo)
        }
    }

    private fun saveToLocalStorage() {
        viewModelScope.launch {
            localFavouriteReposRepository.saveFavouriteRepos(repo)
        }
    }

    fun setData(RepoResponseItem: RepoResponseItem) {
        repo = RepoResponseItem
    }

    fun handleIfAlreadyFavourite() {
        viewModelScope.launch {

            val list = localFavouriteReposRepository.getFavouriteReposList()
            if (list.contains(repo)) {
                updateFaviconandstatus()
            }

        }
    }

    private fun updateFaviconandstatus() {
        isFavRepo = true
        _repoFavDataObserver.postValue(true)
    }
}

