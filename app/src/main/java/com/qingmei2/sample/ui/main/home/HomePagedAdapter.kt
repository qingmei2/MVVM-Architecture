package com.qingmei2.sample.ui.main.home

import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.request.RequestOptions
import com.qingmei2.rhine.adapter.AutoDisposeViewHolder
import com.qingmei2.rhine.image.GlideApp
import com.qingmei2.sample.R
import com.qingmei2.sample.entity.ReceivedEvent
import com.qingmei2.sample.entity.Type
import com.qingmei2.sample.utils.TimeConverter
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.subjects.PublishSubject

class HomePagedAdapter : PagedListAdapter<ReceivedEvent, HomePagedViewHolder>(diffCallback) {

    private val parentSubject: PublishSubject<String> = PublishSubject.create()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomePagedViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_home_received_event, parent, false)
        return HomePagedViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomePagedViewHolder, position: Int) {
        holder.binds(getItem(position)!!, position, parentSubject)
    }

    fun getItemClickEvent(): Observable<String> {
        return parentSubject
    }

    companion object {

        private val diffCallback: DiffUtil.ItemCallback<ReceivedEvent> =
                object : DiffUtil.ItemCallback<ReceivedEvent>() {

                    override fun areItemsTheSame(oldItem: ReceivedEvent, newItem: ReceivedEvent): Boolean {
                        return oldItem.id == newItem.id
                    }

                    override fun areContentsTheSame(oldItem: ReceivedEvent, newItem: ReceivedEvent): Boolean {
                        return oldItem == newItem
                    }
                }
    }
}

class HomePagedViewHolder(view: View) : AutoDisposeViewHolder(view) {

    private val tvEventContent: TextView = view.findViewById(R.id.tvEventContent)
    private val tvEventTime: TextView = view.findViewById(R.id.tvEventTime)
    private val ivAvatar: ImageView = view.findViewById(R.id.ivAvatar)
    private val ivEventType: ImageView = view.findViewById(R.id.ivEventType)

    fun binds(data: ReceivedEvent, position: Int, observer: Observer<String>) {
        GlideApp.with(ivAvatar.context)
                .load(data.actor.avatarUrl)
                .apply(RequestOptions().circleCrop())
                .into(ivAvatar)

        tvEventTime.text = TimeConverter.tramsTimeAgo(data.createdAt)
        tvEventContent.text = getTitle(data, observer)

        ivEventType.setImageDrawable(
                when (data.type) {
                    Type.WatchEvent ->
                        ContextCompat.getDrawable(ivEventType.context, R.mipmap.ic_star_yellow_light)
                    Type.CreateEvent, Type.ForkEvent, Type.PushEvent ->
                        ContextCompat.getDrawable(ivEventType.context, R.mipmap.ic_fork_green_light)
                    else -> null
                }
        )
    }

    private fun getTitle(data: ReceivedEvent, observer: Observer<String>): CharSequence {
        val actor = data.actor.displayLogin
        val action = when (data.type) {
            Type.WatchEvent -> "starred"
            Type.CreateEvent -> "created"
            Type.ForkEvent -> "forked"
            Type.PushEvent -> "pushed"
            else -> data.type.name
        }
        val repo = data.repo.name

        val actorSpan = object : ClickableSpan() {
            override fun onClick(widget: View?) {
                observer.onNext(data.actor.url)
            }
        }
        val repoSpan = object : ClickableSpan() {
            override fun onClick(widget: View?) {
                observer.onNext(data.repo.url)
            }
        }
        val styleSpan = StyleSpan(Typeface.BOLD)
        val styleSpan2 = StyleSpan(Typeface.BOLD)

        tvEventContent.movementMethod = LinkMovementMethod.getInstance()

        return SpannableStringBuilder().apply {
            append("$actor $action $repo")
            setSpan(actorSpan, 0, actor.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            setSpan(styleSpan, 0, actor.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            setSpan(repoSpan,
                    actor.length + action.length + 2,
                    actor.length + action.length + repo.length + 2,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            setSpan(styleSpan2,
                    actor.length + action.length + 2,
                    actor.length + action.length + repo.length + 2,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
    }
}