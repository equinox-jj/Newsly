package com.newsly.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.newsly.data.ApiResource
import com.newsly.databinding.FragmentHomeBinding
import com.newsly.ui.adapter.ItemNewsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModels by viewModels<HomeViewModel>()
    private val mAdapter by lazy { ItemNewsAdapter() }
    private var snackBar: Snackbar? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecycler()
        initViewModel()

    }

    private fun initRecycler() {
        binding.rvNewsList.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }

    private fun initViewModel() {
        viewModels.getNewsResponse()
        viewModels.newsResponses.observe(viewLifecycleOwner) { newsResponse ->
            when (newsResponse) {
                is ApiResource.Loading -> {
                    binding.shimmerNewsList.visibility = View.VISIBLE
                    binding.rvNewsList.visibility = View.GONE
                    binding.ivErrorHome.visibility = View.GONE
                    binding.tvErrorHome.visibility = View.GONE
                }
                is ApiResource.Success -> {
                    newsResponse.data?.let { mAdapter.setData(it) }
                    binding.shimmerNewsList.visibility = View.GONE
                    binding.rvNewsList.visibility = View.VISIBLE
                    binding.ivErrorHome.visibility = View.GONE
                    binding.tvErrorHome.visibility = View.GONE
                }
                is ApiResource.Error -> {
                    binding.shimmerNewsList.visibility = View.GONE
                    binding.rvNewsList.visibility = View.GONE
                    binding.ivErrorHome.visibility = View.VISIBLE
                    binding.tvErrorHome.visibility = View.VISIBLE
                    showErrorSnackBar()
                }
            }
        }
    }

    private fun showErrorSnackBar() {
        snackBar = Snackbar.make(
            requireView(),
            "Error When Loading Data",
            Snackbar.LENGTH_INDEFINITE
        ).setAction("Retry") {
            viewModels.retryFailed()
        }
        snackBar?.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}