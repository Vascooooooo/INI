package com.project.ini.ui.main.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.ini.data.models.ArticlesItem
import com.project.ini.data.repo.NewsRepository
import com.project.ini.databinding.FragmentHomeBinding
import com.project.ini.ui.adapter.NewsAdapter
import com.project.ini.ui.detail.DetailActivity
import com.project.ini.ui.detail.DetailActivity.Companion.KEY_NEWS
import com.project.ini.ui.factory.MainViewModelFactory

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val newsAdapter = NewsAdapter()

    private val homeViewModel by viewModels<HomeViewModel> {
        MainViewModelFactory(
            NewsRepository.getInstance(requireActivity().application)
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setupRecyclerView()
        observeViewModel()

        setListeners()

        return root
    }

    private fun setupRecyclerView() {
        binding.rvNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun setListeners() {
        binding.apply {
            btnRetry.setOnClickListener {
                homeViewModel.getNews()
            }

            newsAdapter.onItemClick = { news ->
                val iDetail = Intent(requireContext(), DetailActivity::class.java)
                iDetail.putExtra(KEY_NEWS, news)
                startActivity(iDetail)
            }
        }
    }

    private fun observeViewModel() {
        homeViewModel.apply {
            isLoading.observe(viewLifecycleOwner) {
                showLoading(it)
            }

            isError.observe(viewLifecycleOwner) {
                showError(it)
            }

            toastMessage.observe(viewLifecycleOwner) {
                binding.tvError.text = it
            }

            listNews.observe(viewLifecycleOwner) {
                newsAdapter.setList(it as ArrayList<ArticlesItem>)
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.isVisible = isLoading
    }

    private fun showError(isError: Boolean) {
        binding.layoutError.isVisible = isError
        binding.rvNews.isVisible = !isError
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}