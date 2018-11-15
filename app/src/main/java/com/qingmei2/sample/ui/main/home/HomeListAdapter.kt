package com.qingmei2.sample.ui.main.home

import android.arch.paging.DataSource
import android.arch.paging.PagedListAdapter
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.qingmei2.rhine.functional.Consumer
import com.qingmei2.sample.R
import com.qingmei2.sample.databinding.ItemHomeReceivedEventBinding
import com.qingmei2.sample.entity.ReceivedEvent

class HomeListAdapter : PagedListAdapter<ReceivedEvent, HomeItemViewHolder<ItemHomeReceivedEventBinding>>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeItemViewHolder<ItemHomeReceivedEventBinding> =
            HomeItemViewHolder(
                    DataBindingUtil.inflate(
                            LayoutInflater.from(parent.context),
                            R.layout.item_home_received_event,
                            parent, false
                    )
            )

    override fun onBindViewHolder(holder: HomeItemViewHolder<ItemHomeReceivedEventBinding>, position: Int) =
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

class HomeItemViewHolder<T : ViewDataBinding>(private val binding: T) : RecyclerView.ViewHolder(binding.root) {

    fun bindTo(event: ReceivedEvent?) {
        val eventBinding = binding as ItemHomeReceivedEventBinding
        eventBinding.data = event
        eventBinding.actorEvent = object : Consumer<String> {
            override fun accept(t: String) {

            }
        }
        eventBinding.repoEvent = object : Consumer<String> {
            override fun accept(t: String) {

            }
        }
    }
}