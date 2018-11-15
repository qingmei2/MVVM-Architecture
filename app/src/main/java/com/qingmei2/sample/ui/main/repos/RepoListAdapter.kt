package com.qingmei2.sample.ui.main.repos

import android.arch.paging.PagedListAdapter
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.qingmei2.rhine.ext.jumpBrowser
import com.qingmei2.rhine.functional.Consumer
import com.qingmei2.sample.R
import com.qingmei2.sample.base.BaseApplication
import com.qingmei2.sample.databinding.ItemReposRepoBinding
import com.qingmei2.sample.entity.Repo

class RepoListAdapter : PagedListAdapter<Repo, RepoItemViewHolder<ItemReposRepoBinding>>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoItemViewHolder<ItemReposRepoBinding> =
            RepoItemViewHolder(
                    DataBindingUtil.inflate(
                            LayoutInflater.from(parent.context),
                            R.layout.item_repos_repo,
                            parent, false
                    )
            )

    override fun onBindViewHolder(holder: RepoItemViewHolder<ItemReposRepoBinding>, position: Int) =
            holder.bindTo(getItem(position))

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Repo>() {

            override fun areItemsTheSame(oldItem: Repo, newItem: Repo): Boolean =
                    oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Repo, newItem: Repo): Boolean =
                    oldItem == newItem
        }
    }
}

class RepoItemViewHolder<T : ViewDataBinding>(private val binding: T) : RecyclerView.ViewHolder(binding.root) {

    fun bindTo(repo: Repo?) {
        val eventBinding = binding as ItemReposRepoBinding
        eventBinding.data = repo
        eventBinding.repoEvent = object : Consumer<String> {
            override fun accept(t: String) {
                BaseApplication.INSTANCE.jumpBrowser(t)
            }
        }
    }
}