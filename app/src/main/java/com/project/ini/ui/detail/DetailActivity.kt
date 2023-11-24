package com.project.ini.ui.detail

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.project.ini.R
import com.project.ini.data.models.ArticlesItem
import com.project.ini.data.repo.NewsRepository
import com.project.ini.databinding.ActivityDetailBinding
import com.project.ini.ui.factory.DetailViewModelFactory
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val news by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(KEY_NEWS, ArticlesItem::class.java)!!
        } else {
            intent.getParcelableExtra(KEY_NEWS)!!
        }
    }
    private val detailViewModel by viewModels<DetailViewModel> {
        DetailViewModelFactory(
            news.url,
            NewsRepository.getInstance(application)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        detailViewModel.checkBookmark()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            setView()
        }

        setListeners()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setView() {
        binding.apply {
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

            btnBookmark.isVisible = true
        }
    }

    private fun setListeners() {
        binding.apply {
            toolbar.setNavigationOnClickListener { finish() }

            btnBookmark.apply {
                isVisible = true
                setOnClickListener {
                    if (detailViewModel.isBookmarked.value!!) {
                        detailViewModel.delete()
                        showToast("News deleted from Bookmark!")
                    } else {
                        detailViewModel.insert(news)
                        showToast("News added to Bookmark!")
                    }
                    detailViewModel.checkBookmark()
                }
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val KEY_NEWS = "key_news"
    }
}