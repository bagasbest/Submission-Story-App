package com.project.dicodingplayground.practice_modul.dependencyinjection.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.dicodingplayground.databinding.FragmentNewsBinding
import com.project.dicodingplayground.practice_modul.dependencyinjection.data.Result

/**
 * A placeholder fragment containing a simple view.
 */
class NewsFragment : Fragment() {

    private var _binding: FragmentNewsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var tabName: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tabName = arguments?.getString(ARG_TAB)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(requireActivity())
        val viewModel: NewsViewModel = ViewModelProvider(this, factory)[NewsViewModel::class.java]

        val newsAdapter = NewsAdapter { news ->
            if (news.isBookmarked) {
                viewModel.deleteNews(news)
            } else {
                viewModel.saveNews(news)
            }
        }
        if (tabName == TAB_NEWS) {
            viewModel.getHeadlineNews().observe(viewLifecycleOwner) { result ->

                if (result != null) {
                    when (result) {
                        is Result.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }
                        is Result.Success -> {
                            binding.progressBar.visibility = View.GONE
                            val newsData = result.data
                            if (newsData.isNotEmpty()) {
                                binding.tvNoData.visibility = View.GONE
                                newsAdapter.submitList(newsData)
                            } else {
                                binding.tvNoData.visibility = View.VISIBLE
                                newsAdapter.submitList(listOf())
                            }

                        }
                        is Result.Error -> {
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(
                                context,
                                "Terjadi kesalahan" + result.error,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        } else {
            viewModel.getBookmarkedNews().observe(viewLifecycleOwner) {
                binding.tvNoData.visibility = View.GONE
                binding.progressBar.visibility = View.GONE
                if (it.isNotEmpty()) {
                    newsAdapter.submitList(it)
                } else {
                    binding.tvNoData.visibility = View.VISIBLE
                    newsAdapter.submitList(listOf())
                }
            }
        }

        binding.rvNews.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
            adapter = newsAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val ARG_TAB = "tab_name"
        const val TAB_NEWS = "news"
        const val TAB_BOOKMARK = "bookmark"
    }
}