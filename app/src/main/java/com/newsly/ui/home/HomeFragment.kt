package com.newsly.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.newsly.data.ApiResource
import com.newsly.databinding.FragmentHomeBinding
import com.newsly.ui.adapter.ItemNewsAdapter
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModels by viewModels<HomeViewModel>()
    private val mAdapter by lazy { ItemNewsAdapter() }

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
                is ApiResource.Success -> {
                    newsResponse.data?.let {
                        mAdapter.setData(it)
                        Timber.tag("HomeFragment Success").d(it.toString())
                    }
                }
                is ApiResource.Loading -> {

                }
                is ApiResource.Error -> {
                    Toast.makeText(
                        requireContext(),
                        newsResponse.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}