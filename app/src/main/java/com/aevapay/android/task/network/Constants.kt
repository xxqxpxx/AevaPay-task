package com.aevapay.android.task.network

import com.aevapay.android.task.BuildConfig

object Constants {

    const val API_TIMEOUT: Long = 60

    const val BASE_URL = BuildConfig.GITHUB_BASE_URL

   // https://api.github.com/users/JakeWharton/repos?page=1&per_page=15

    const val REPOS_URL = "users/JakeWharton/repos"

    // const val getPictures = "planetary/apod?count=25&api_key=${BuildConfig.API_KEY}"

    const val PREFERENCE_NAME = "pref_aevapay"

    const val REPOS_LIST_PREF_NAME = "repos_list"

    const val NUMBER_OF_REPOS_PER_PAGE=15

    const val  STARTING_KEY =1


}