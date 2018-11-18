package com.qingmei2.sample.ui.main.home

import android.arch.paging.PagedListAdapter
import android.databinding.DataBindingUtil
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.qingmei2.rhine.ext.jumpBrowser
import com.qingmei2.rhine.functional.Consumer
import com.qingmei2.sample.R
import com.qingmei2.sample.base.BaseApplication
import com.qingmei2.sample.databinding.ItemHomeReceivedEventBinding
import com.qingmei2.sample.entity.ReceivedEvent

class HomeListAdapter : PagedListAdapter<ReceivedEvent, HomeItemViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeItemViewHolder =
            HomeItemViewHolder(
                    DataBindingUtil.inflate(
                            LayoutInflater.from(parent.context),
                            R.layout.item_home_received_event,
                            parent, false
                    )
            )

    override fun onBindViewHolder(holder: HomeItemViewHolder, position: Int) =
            holder.bindTo(getItem(position))

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<ReceivedEvent>() {

            override fun areItemsTheSame(oldItem: ReceivedEvent, newItem: ReceivedEvent): Boolean =
                    oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: ReceivedEvent, newItem: ReceivedEvent): Boolean =
                    oldItem == newItem
        }
    }
}

class HomeItemViewHolder(private val binding: ItemHomeReceivedEventBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bindTo(event: ReceivedEvent?) {
        binding.apply {
            data = event
            actorEvent = object : Consumer<String> {
                override fun accept(t: String) {
                    BaseApplication.INSTANCE.jumpBrowser(t)
                }
            }
            repoEvent = object : Consumer<String> {
                override fun accept(t: String) {
                    BaseApplication.INSTANCE.jumpBrowser(t)
                }
            }
        }
    }
}