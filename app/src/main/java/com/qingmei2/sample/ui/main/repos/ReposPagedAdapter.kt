package com.qingmei2.sample.ui.main.repos

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.request.RequestOptions
import com.qingmei2.architecture.core.image.GlideApp
import com.qingmei2.sample.R
import com.qingmei2.sample.entity.Repo
import de.hdodenhof.circleimageview.CircleImageView

class ReposPagedAdapter : PagedListAdapter<Repo, RepoPagedViewHolder>(diffCallback) {

    private val liveData: MutableLiveData<String> = MutableLiveData()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoPagedViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_repos_repo, parent, false)
        return RepoPagedViewHolder(view)
    }

    override fun onBindViewHolder(holder: RepoPagedViewHolder, position: Int) {
        holder.binds(getItem(position)!!, position, liveData)
    }

    fun getItemClickEvent(): LiveData<String> {
        return liveData
    }

    companion object {

        private val diffCallback: DiffUtil.ItemCallback<Repo> =
                object : DiffUtil.ItemCallback<Repo>() {

                    override fun areItemsTheSame(oldItem: Repo, newItem: Repo): Boolean {
                        return oldItem.id == newItem.id
                    }

                    override fun areContentsTheSame(oldItem: Repo, newItem: Repo): Boolean {
                        return oldItem == newItem
                    }
                }
    }
}

class RepoPagedViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

    private val ivAvatar: ImageView = view.findViewById(R.id.ivAvatar)
    private val btnAvatar: ConstraintLayout = view.findViewById(R.id.btnAvatar)

    private val tvOwnerName: TextView = view.findViewById(R.id.tvOwnerName)
    private val ivLanguageColor: CircleImageView = view.findViewById(R.id.ivLanguageColor)

    private val tvLanguage: TextView = view.findViewById(R.id.tvLanguage)
    private val tvRepoName: TextView = view.findViewById(R.id.tvRepoName)
    private val tvRepoDesc: TextView = view.findViewById(R.id.tvRepoDesc)
    private val tvStar: TextView = view.findViewById(R.id.tvStar)
    private val tvIssue: TextView = view.findViewById(R.id.tvIssue)
    private val tvFork: TextView = view.findViewById(R.id.tvFork)

    @SuppressLint("SetTextI18n")
    fun binds(data: Repo, position: Int, observer: MutableLiveData<String>) {
        GlideApp.with(ivAvatar.context)
                .load(data.owner.avatarUrl)
                .apply(RequestOptions().circleCrop())
                .into(ivAvatar)

        view.setOnClickListener {
            observer.postValue(data.htmlUrl)
        }
        btnAvatar.setOnClickListener {
            observer.postValue(data.owner.htmlUrl)
        }

        tvOwnerName.text = data.owner.login

        ivLanguageColor.setImageDrawable(getLanguageColor(data.language))
        ivLanguageColor.visibility = if (data.language == null) View.GONE else View.VISIBLE
        tvLanguage.text = data.language

        tvRepoName.text = data.fullName
        tvRepoDesc.text = data.description ?: "(No description, website, or topics provided.)"

        tvStar.text = "${data.stargazersCount}"
        tvIssue.text = "${data.openIssuesCount}"
        tvFork.text = "${data.forksCount}"
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