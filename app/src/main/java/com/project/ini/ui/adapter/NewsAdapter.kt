package com.project.ini.ui.adapter

import android.annotation.SuppressLint
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.project.ini.R
import com.project.ini.data.models.ArticlesItem
import com.project.ini.databinding.ItemNewsRowBinding
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {
    private val listNews: ArrayList<ArticlesItem> = arrayListOf()
    var onItemClick: ((ArticlesItem) -> Unit)? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setList(newsList: ArrayList<ArticlesItem>) {
        this.listNews.clear()
        this.listNews.addAll(newsList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemNewsRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            holder.bind(listNews[position])
        }
    }

    override fun getItemCount() = listNews.size

    inner class ViewHolder(private var binding: ItemNewsRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @RequiresApi(Build.VERSION_CODES.O)
        @SuppressLint("SimpleDateFormat")
        fun bind(news: ArticlesItem) {
            with(binding) {
                Glide.with(binding.root)
                    .load(news.urlToImage)
                    .placeholder(R.drawable.inilogo)
                    .apply(RequestOptions().override(800, 600))
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(binding.ivImgNews)

                val formatter = DateTimeFormatter.ofPattern("h:mm a, d MMM")

                val instant = Instant.parse(news.publishedAt)
                val formattedDateTime = formatter.format(instant.atZone(ZoneId.of("UTC")))

                tvNewsTitle.text = news.title
                tvTime.text = formattedDateTime

                root.setOnClickListener {
                    onItemClick?.invoke(news)
                }
            }
        }
    }
}