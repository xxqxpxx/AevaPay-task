package com.aevapay.android.task.ui.planetdetails

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
    private val TAG = "PlanetListViewModel"

    private val _planetsFavDataObserver = MutableLiveData<Boolean>()
   val planetsDataObserverFav: LiveData<Boolean> = _planetsFavDataObserver

    lateinit var planet : RepoResponseItem
    var isFavPlanet : Boolean = false

    private lateinit var localFavouriteReposRepository : LocalFavouriteReposRepository

    fun setComplexPref(complexPreferences: ComplexPreferencesImpl) {
        localFavouriteReposRepository = LocalFavouriteReposRepository(complexPreferences)
    }

    fun handleFavClicked(selected: Boolean) {
        isFavPlanet = selected
        if (selected){
            saveToLocalStorage()
        }else{
            removeFromLocalStorage()
        }
        _planetsFavDataObserver.postValue(isFavPlanet)
    }

    private fun removeFromLocalStorage() {
        viewModelScope.launch {
           localFavouriteReposRepository.removeFavouritePlanet(planet)
        }
    }

    private fun saveToLocalStorage() {
        viewModelScope.launch {
           localFavouriteReposRepository.saveFavouriteRepos(planet)
        }
    }

    fun setPlanetData(RepoResponseItem: RepoResponseItem) {
        planet =  RepoResponseItem
    }

    fun handleIfAlreadyFavourite() {
        viewModelScope.launch {

            val list = localFavouriteReposRepository.getFavouriteReposList()
            if (list.contains(planet)) {
                updateFaviconandstatus()
            }

        }
    }

    private fun updateFaviconandstatus(){
        isFavPlanet = true
       _planetsFavDataObserver.postValue(true)
    }
}

