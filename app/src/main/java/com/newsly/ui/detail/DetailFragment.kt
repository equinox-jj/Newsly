package com.newsly.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.newsly.R
import com.newsly.data.model.ArticlesItem
import com.newsly.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import org.jsoup.Jsoup

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
            val newsParcel: ArticlesItem = args.newsParcel

            tvNewsDetailTitle.text = newsParcel.title

            ivNewsDetail.load(newsParcel.urlToImage) {
                crossfade(200)
                error(R.drawable.ic_no_image)
            }

            if (newsParcel.author != null) {
                val auth = Jsoup.parse(newsParcel.author).text()
                tvNewsDetailAuthor.text = auth
            } else {
                tvNewsDetailAuthor.text = getString(R.string.no_author)
            }

            if (newsParcel.content != null) {
                val content = Jsoup.parse(newsParcel.content).text()
                tvNewsDetailDescription.text = content
            } else {
                tvNewsDetailDescription.text = getString(R.string.no_desc)
            }

            btnReadFullArticle.setOnClickListener {
                val action = DetailFragmentDirections.actionDetailFragmentToArticleFragment(args.newsParcel)
                findNavController().navigate(action)
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}