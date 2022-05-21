package com.newsly.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.newsly.data.model.ArticlesItem
import com.newsly.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<DetailFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        iniView()
    }

    private fun iniView() {
        binding.apply {
            val newsResult: ArticlesItem = args.newsParcel

            ivNewsDetail.load(newsResult.urlToImage) {
                crossfade(200)
            }
            tvNewsDetailTitle.text = newsResult.title
            tvNewsDetailDescription.text = newsResult.description
            btnReadFullArticle.setOnClickListener {
                val action = DetailFragmentDirections.actionDetailFragmentToArticleFragment(newsResult)
                binding.btnReadFullArticle.findNavController().navigate(action)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}