package com.aevapay.android.task.ui.reposlanding

import android.content.Intent
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aevapay.android.task.base.BaseActivity
import com.aevapay.android.task.data.helper.ComplexPreferencesImpl
import com.aevapay.android.task.data.response.RepoResponseItem
import com.aevapay.android.task.databinding.ActivityRepoLandingBinding
import com.aevapay.android.task.network.ResultModel
import com.aevapay.android.task.ui.repodetails.RepoDetailsActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ReposLandingActivity : BaseActivity<ActivityRepoLandingBinding>() {

    private val viewModel: ReposLandingViewModel by viewModels()

    private lateinit var reposAdapter: ReposAdapter

    private lateinit var reposFavouriteAdapter: ReposFavsAdapter

    override fun getViewBinding() = ActivityRepoLandingBinding.inflate(layoutInflater)

    private lateinit var complexPreferences: ComplexPreferencesImpl

    override fun setupView() {

        complexPreferences = ComplexPreferencesImpl(this)
        viewModel.setComplexPref(complexPreferences)
        handleFavouriteState()


        setupViewModelObservers()
        initListeners()
        fillData()
        pagingObserver()
    }

    override fun onResume() {
        super.onResume()
        handleFavouriteState()
    }

    private fun handleFavouriteState() {
        viewModel.handleIfAlreadyFavourite()
    }


    private fun fillData() {
        initRepoList()
    }

    private fun initListeners() {

        binding.layoutError.button.setOnClickListener {
            viewModel.refresh()
            hideErrorAndRefresh()
        }


    }


    private fun hideErrorAndRefresh() {
        handleError(isError = false)
    }

    private fun handleRepoSelection(repo: RepoResponseItem?) {
        val myIntent = Intent(this, RepoDetailsActivity::class.java)
        myIntent.putExtra("repoDetails", repo)
        startActivity(myIntent)
    }

    var reposOnClickListener = (ReposAdapter.OnClickListener { item ->
        handleRepoSelection(item)
    })

    private fun initRepoList() {
        reposAdapter = ReposAdapter(
            context = this@ReposLandingActivity,
            onClickListener = reposOnClickListener
        )
        binding.recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.recyclerView.adapter = reposAdapter

        reposAdapter.addLoadStateListener { loadState ->
            when (val state = loadState.source.refresh) {
                is LoadState.NotLoading -> {
                    handleProgress(isLoading = false)
                }
                is LoadState.Loading -> {
                    handleProgress(isLoading = true)
                }
                is LoadState.Error -> {
                    onFail()
                }
            }
        }
    }

    override fun setupViewModelObservers() {
        viewModel.reposDataObserver.observe(this, reposDataObserver)

        viewModel.reposDataObserverFav.observe(this) {
            handleFavouriteIconState(it)
        }

    }

    private fun pagingObserver() {

        viewModel._repoListPaging.observe(this) {
            lifecycleScope.launch {
                reposAdapter.submitData(it)
            }
        }


    }

    private fun handleProgress(isLoading: Boolean) {
        if (isLoading)
            binding.progressBar.visibility = View.VISIBLE
        else
            binding.progressBar.visibility = View.GONE
    }

    private fun onFail() {
        handleProgress(isLoading = false)
        handleError(isError = true)
    }

    private fun handleError(isError: Boolean) {
        if (isError) {
            binding.layoutError.layoutError.visibility = View.VISIBLE
        } else {
            binding.layoutError.layoutError.visibility = View.GONE
        }
    }

    var reposFavOnClickListener = (ReposFavsAdapter.OnClickListener { repo ->
        handleRepoSelection(repo)
    })

    private fun handleFavouriteIconState(list: ArrayList<RepoResponseItem>) {

        if (list.isNotEmpty()) {
            binding.txtMyfavourites.visibility = View.VISIBLE
            binding.rcvFavourites.visibility = View.VISIBLE

            reposFavouriteAdapter = ReposFavsAdapter(
                list,
                context = this@ReposLandingActivity,
                reposFavOnClickListener
            )
            binding.rcvFavourites.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
            binding.rcvFavourites.adapter = reposFavouriteAdapter


        } else {
            binding.txtMyfavourites.visibility = View.GONE
            binding.rcvFavourites.visibility = View.GONE
        }
    }


    private val reposDataObserver = Observer<ResultModel<List<RepoResponseItem>>> { result ->
        lifecycleScope.launch {
            when (result) {
                is ResultModel.Loading -> {
                    handleProgress(isLoading = result.isLoading ?: false)
                } // Loading
                is ResultModel.Success -> {
                    onSuccess(data = result.data)
                } // Success
                is ResultModel.Failure -> {
                    onFail()
                }
            }
        }
    }

    private fun onSuccess(data: List<RepoResponseItem>?) {
        handleProgress(isLoading = false)
        //    reposAdapter.submitList((data ?: arrayListOf()))
    } // fun of onSuccess
}
