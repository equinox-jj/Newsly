package com.newsly.ui.article

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.newsly.R
import com.newsly.data.model.ArticlesItem
import com.newsly.databinding.FragmentArticleBinding
import com.newsly.databinding.FragmentDetailBinding
import com.newsly.ui.detail.DetailFragmentArgs
import com.newsly.ui.detail.DetailFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArticleFragment : Fragment() {

    private var _binding: FragmentArticleBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<ArticleFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentArticleBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        iniView()
    }

    private fun iniView() {
        binding.apply {
            val newsResult: ArticlesItem = args.webParcel

            binding.wvArticle.webViewClient = object : WebViewClient() {}
            val websiteUrl: String = newsResult.url
            binding.wvArticle.loadUrl(websiteUrl)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}