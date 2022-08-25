package com.aevapay.android.task.ui.repodetails

import androidx.activity.viewModels
import androidx.appcompat.content.res.AppCompatResources
import com.aevapay.android.task.R
import com.aevapay.android.task.base.BaseActivity
import com.aevapay.android.task.data.helper.ComplexPreferencesImpl
import com.aevapay.android.task.data.response.RepoResponseItem
import com.aevapay.android.task.databinding.ActivityRepoDetailsBinding
import com.bumptech.glide.Glide

class RepoDetailsActivity : BaseActivity<ActivityRepoDetailsBinding>() {

    private val viewModel: RepoDetailsViewModel by viewModels()

    override fun getViewBinding() = ActivityRepoDetailsBinding.inflate(layoutInflater)

    lateinit var RepoResponseItem: RepoResponseItem

    private lateinit var complexPreferences: ComplexPreferencesImpl

    override fun setupView() {
        complexPreferences = ComplexPreferencesImpl(this)
        viewModel.setComplexPref(complexPreferences)

        setupViewModelObservers()
        initListeners()
        getData()
        setupToolBar()
        fillData()
        handleFavouriteState()

    }

    private fun handleFavouriteState() {
        viewModel.handleIfAlreadyFavourite()
    }

    private fun initListeners() {
        binding.imgFavourite.setOnClickListener {
            viewModel.handleFavClicked(!viewModel.isFavRepo)
        }
    }

    private fun handleFavouriteIconState(show: Boolean) {
        if (show)
            Glide.with(this)
                .load(AppCompatResources.getDrawable(this, R.drawable.ic_favorite_filled))
                .into(binding.imgFavourite)
        else
            Glide.with(this)
                .load(AppCompatResources.getDrawable(this, R.drawable.ic_favorite_border))
                .into(binding.imgFavourite)
    }

    private fun setupToolBar() {
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun getData() {
        val value = intent.extras?.get("repoDetails")
        RepoResponseItem = value as RepoResponseItem
        viewModel.setData(RepoResponseItem)
    }

    private fun fillData() {
        val attr = "Lang: " + RepoResponseItem.language +
                ", stars: " + RepoResponseItem.stargazers_count +
                ", watch: " + RepoResponseItem.watchers_count


        binding.txtRepoTitle.text = RepoResponseItem.name
        binding.txtDate.text = RepoResponseItem.created_at
        binding.txtDescription.text = RepoResponseItem.description

         binding.txtRepoAttributes.text = attr

    }

    override fun setupViewModelObservers() {
        viewModel.repoDataObserverFav.observe(this) {
            handleFavouriteIconState(it)
        }
    }
}
