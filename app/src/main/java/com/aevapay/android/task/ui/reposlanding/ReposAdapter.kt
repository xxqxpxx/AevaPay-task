package com.aevapay.android.task.ui.reposlanding

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.aevapay.android.task.data.response.RepoResponseItem
 import com.aevapay.android.task.databinding.ItemRepoBinding
 import kotlinx.android.synthetic.main.item_planet_attributes.view.txt_repo_name

class ReposAdapter(
    private val context: Context,
    private val onClickListener:  OnClickListener
) : PagingDataAdapter<RepoResponseItem, ReposAdapter.ViewHolder>(DataDifferntiator) {


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val binding = ItemRepoBinding.bind(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val repo = getItem(position)
        if (repo != null) {


        with(holder) {


            var attr =
                "Lang: " + repo.language + ", stars: " + repo.stargazers_count + ", watch: " + repo.watchers_count

            binding.txtRepoName.text = repo.name
            binding.txtRepoDescription.text = repo.description
            binding.txtRepoAttributes.text = attr


            binding.rootLayout.setOnClickListener {
                if (repo != null) {
                    onClickListener.clickListener(repo)
                }
            }

        }
            }
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            ItemRepoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ).root
        )
    }

    object DataDifferntiator : DiffUtil.ItemCallback<RepoResponseItem>() {

        override fun areItemsTheSame(oldItem: RepoResponseItem, newItem: RepoResponseItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: RepoResponseItem, newItem: RepoResponseItem): Boolean {
            return oldItem == newItem
        }
    }


    class OnClickListener(val clickListener: (RepoResponseItem: RepoResponseItem) -> Unit) {
        fun onClick(RepoResponseItem: RepoResponseItem) = clickListener(RepoResponseItem)
    }


}