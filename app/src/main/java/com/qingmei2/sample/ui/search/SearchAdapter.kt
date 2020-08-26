package com.qingmei2.sample.ui.search

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.request.RequestOptions
import com.qingmei2.architecture.core.image.GlideApp
import com.qingmei2.sample.R
import com.qingmei2.sample.entity.Repo
import com.qingmei2.sample.utils.TimeConverter
import de.hdodenhof.circleimageview.CircleImageView

class SearchAdapter : PagingDataAdapter<Repo, SearchViewHolder>(diffCallBack) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_serach_repo, parent, false)
        return SearchViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.binds(getItem(position)!!, itemEventObservable)
    }

    private val itemEventObservable: MutableLiveData<String> = MutableLiveData()

    fun observeItemEvent(): LiveData<String> {
        return itemEventObservable
    }

    companion object {

        private val diffCallBack: DiffUtil.ItemCallback<Repo> = object : DiffUtil.ItemCallback<Repo>() {
            override fun areItemsTheSame(oldItem: Repo, newItem: Repo): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Repo, newItem: Repo): Boolean {
                return oldItem == newItem
            }
        }
    }
}

class SearchViewHolder(
        private val view: View
) : RecyclerView.ViewHolder(view) {

    private val ivAvatar = view.findViewById<ImageView>(R.id.ivAvatar)
    private val tvOwnerName = view.findViewById<TextView>(R.id.tvOwnerName)

    private val tvRepoName = view.findViewById<TextView>(R.id.tvRepoName)
    private val tvRepoDesc = view.findViewById<TextView>(R.id.tvRepoDesc)

    private val tvStarCount = view.findViewById<TextView>(R.id.tvStarCount)
    private val ivLanguage = view.findViewById<CircleImageView>(R.id.ivLanguage)
    private val tvLanguage = view.findViewById<TextView>(R.id.tvLanguage)
    private val tvEventTime = view.findViewById<TextView>(R.id.tvEventTime)

    fun binds(data: Repo, itemEventObservable: MutableLiveData<String>) {
        GlideApp.with(ivAvatar.context)
                .load(data.owner.avatarUrl)
                .apply(RequestOptions().circleCrop()
                        .placeholder(R.mipmap.ic_github)
                        .error(R.mipmap.ic_github))
                .into(ivAvatar)
        tvOwnerName.text = data.owner.login

        tvRepoName.text = data.fullName
        tvRepoDesc.text = data.description

        tvStarCount.text = data.stargazersCount.toString()
        ivLanguage.setImageDrawable(getLanguageColor(data.language))
        tvLanguage.text = data.language
        tvEventTime.text = TimeConverter.tramsTimeAgo(data.updatedAt)

        view.setOnClickListener {
            itemEventObservable.postValue(data.htmlUrl)
        }
    }

    private fun getLanguageColor(language: String?): Drawable {
        if (language == null) return ColorDrawable(Color.TRANSPARENT)

        val colorProvider: (Int) -> Drawable = { resId ->
            ColorDrawable(ContextCompat.getColor(view.context, resId))
        }

        return colorProvider(
                when (language) {
                    "Kotlin" -> R.color.color_language_kotlin
                    "Java" -> R.color.color_language_java
                    "JavaScript" -> R.color.color_language_js
                    "Python" -> R.color.color_language_python
                    "HTML" -> R.color.color_language_html
                    "CSS" -> R.color.color_language_css
                    "Shell" -> R.color.color_language_shell
                    "C++" -> R.color.color_language_cplus
                    "C" -> R.color.color_language_c
                    "ruby" -> R.color.color_language_ruby
                    else -> R.color.color_language_other
                })
    }
}
