package com.aevapay.android.task.ui.reposlanding

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aevapay.android.task.data.response.RepoResponseItem
import com.aevapay.android.task.databinding.ItemRepoBinding

class ReposFavsAdapter(
    private var repoList: List<RepoResponseItem>? = arrayListOf(),
    private val context: Context,
    private val onClickListener: OnClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    fun submitList(playerList: List<RepoResponseItem>) {
        this.repoList = playerList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return repoList?.size ?: 0
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolderRepo(
            ItemRepoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        repoList?.get(position)?.let {
            (holder as ViewHolderRepo).bind(context = context, repo = it, onClickListener)
        }
    }

    private class ViewHolderRepo(val binding: ItemRepoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            context: Context,
            repo: RepoResponseItem ,
            onSelect: OnClickListener
        ) {

            var attr =
                "Lang: " + repo.language + ", stars: " + repo.stargazers_count + ", watch: " + repo.watchers_count

            binding.txtRepoName.text = repo.name
            binding.txtRepoDescription.text = repo.description
            binding.txtRepoAttributes.text = attr


            binding.root.setOnClickListener {
                onSelect.clickListener(repo)
            }
        }
    }


    class OnClickListener(val clickListener: (astronomyResponse: RepoResponseItem) -> Unit) {
        fun onClick(astronomyResponse: RepoResponseItem) = clickListener(astronomyResponse)
    }


}