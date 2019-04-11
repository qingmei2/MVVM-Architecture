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
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.request.RequestOptions
import com.github.qingmei2.autodispose.recyclerview.AutoDisposeViewHolder
import com.qingmei2.rhine.adapter.AutoDisposePagedListAdapter
import com.qingmei2.rhine.ext.reactivex.clicksThrottleFirst
import com.qingmei2.rhine.image.GlideApp
import com.qingmei2.sample.R
import com.qingmei2.sample.entity.Repo
import com.uber.autodispose.autoDisposable
import de.hdodenhof.circleimageview.CircleImageView
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class ReposPagedAdapter(lifecycleOwner: LifecycleOwner) :
        AutoDisposePagedListAdapter<Repo, RepoPagedViewHolder>(lifecycleOwner, diffCallback) {

    private val parentSubject: PublishSubject<String> = PublishSubject.create()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoPagedViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_repos_repo, parent, false)
        return RepoPagedViewHolder(view)
    }

    override fun onBindViewHolder(holder: RepoPagedViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)

        holder.binds(getItem(position)!!, position)
                .autoDisposable(holder)
                .subscribe(parentSubject)
    }

    fun getItemClickEvent(): Observable<String> {
        return parentSubject
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

class RepoPagedViewHolder(private val view: View) : AutoDisposeViewHolder(view) {

    private val clickSubject: PublishSubject<String> = PublishSubject.create()

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
    fun binds(data: Repo, position: Int): Observable<String> {
        GlideApp.with(ivAvatar.context)
                .load(data.owner.avatarUrl)
                .apply(RequestOptions().circleCrop())
                .into(ivAvatar)

        view.clicksThrottleFirst()
                .map { data.htmlUrl }
                .autoDisposable(this)
                .subscribe(clickSubject)
        btnAvatar.clicksThrottleFirst()
                .map { data.owner.htmlUrl }
                .autoDisposable(this)
                .subscribe(clickSubject)

        tvOwnerName.text = data.owner.login

        ivLanguageColor.setImageDrawable(getLanguageColor(data.language))
        ivLanguageColor.visibility = if (data.language == null) View.GONE else View.VISIBLE
        tvLanguage.text = data.language

        tvRepoName.text = data.fullName
        tvRepoDesc.text = data.description ?: "(No description, website, or topics provided.)"

        tvStar.text = "$data.stargazersCount"
        tvIssue.text = "$data.openIssuesCount"
        tvFork.text = "$data.forksCount"

        return clickSubject
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