package com.aevapay.android.task.data.repo

import com.aevapay.android.task.data.helper.ComplexPreferencesImpl
import com.aevapay.android.task.data.response.RepoResponseItem
import com.aevapay.android.task.network.Constants.REPOS_LIST_PREF_NAME

class LocalFavouriteReposRepository(private val complexPreferences: ComplexPreferencesImpl) {
    private val TAG = "LocalFavouritePlanetsRepository"

    fun saveFavouriteRepos(planet: RepoResponseItem) {
        val list = getFavouriteReposList()
        list.add(planet)
        complexPreferences.saveArrayList(list, REPOS_LIST_PREF_NAME)
    }

    fun getFavouriteReposList(): ArrayList<RepoResponseItem> {
        val list = complexPreferences.getArrayList(REPOS_LIST_PREF_NAME)

        return if (list.isNullOrEmpty()) {
            (arrayListOf())
        } else
            list
    }

    fun removeFavouriteRepo(planet: RepoResponseItem) {
        val list = getFavouriteReposList()
        list.remove(planet)
        complexPreferences.saveArrayList(list, REPOS_LIST_PREF_NAME)
    }

}