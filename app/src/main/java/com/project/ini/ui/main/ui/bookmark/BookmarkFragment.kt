package com.project.ini.ui.main.ui.bookmark

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.ini.data.models.ArticlesItem
import com.project.ini.data.repo.NewsRepository
import com.project.ini.databinding.FragmentBookmarkBinding
import com.project.ini.ui.adapter.NewsAdapter
import com.project.ini.ui.detail.DetailActivity
import com.project.ini.ui.factory.MainViewModelFactory
import com.project.ini.ui.main.ui.home.HomeViewModel

class BookmarkFragment : Fragment() {

    private var _binding: FragmentBookmarkBinding? = null
    private val binding get() = _binding!!

    private val newsAdapter = NewsAdapter()

    private val bookmarkViewModel by viewModels<BookmarkViewModel> {
        MainViewModelFactory(
            NewsRepository.getInstance(requireActivity().application)
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBookmarkBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setupRecyclerView()
        getBookmarkedNews()

        setListeners()

        return root
    }

    private fun setupRecyclerView() {
        binding.rvNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun getBookmarkedNews() {
        bookmarkViewModel.getAllBookmarks().observe(viewLifecycleOwner) {
            newsAdapter.setList(it as ArrayList<ArticlesItem>)

            showEmptyText(it.isEmpty())
        }
    }

    private fun setListeners() {
        binding.apply {
            newsAdapter.onItemClick = { news ->
                val iDetail = Intent(requireContext(), DetailActivity::class.java)
                iDetail.putExtra(DetailActivity.KEY_NEWS, news)
                startActivity(iDetail)
            }
        }
    }

    private fun showEmptyText(isEmpty: Boolean) {
        binding.apply {
            tvEmptyBookmark.isVisible = isEmpty
            rvNews.isVisible = !isEmpty
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}