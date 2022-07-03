package com.newsly.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.newsly.R
import com.newsly.data.model.ArticlesItem
import com.newsly.data.model.NewsResponse
import com.newsly.databinding.ItemNewsListBinding
import com.newsly.ui.home.HomeFragmentDirections
import com.newsly.utils.DiffUtils
import okhttp3.internal.immutableListOf
import org.jsoup.Jsoup

class ItemNewsAdapter : RecyclerView.Adapter<ItemNewsAdapter.NewsViewHolder>() {

    private var newsResults = immutableListOf<ArticlesItem>()

    inner class NewsViewHolder(private val binding: ItemNewsListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(newsResults: ArticlesItem) {
            binding.ivNewsImage.load(newsResults.urlToImage) {
                crossfade(100)
                error(R.drawable.ic_no_image)
            }
            binding.tvNewsTitle.text = newsResults.title
            if (newsResults.description != null) {
                val desc = Jsoup.parse(newsResults.description).text()
                binding.tvNewsDesc.text = desc
            } else binding.tvNewsDesc.text = buildString { append("No Description.") }
            binding.newsCardView.setOnClickListener {
                val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(newsResults)
                binding.newsCardView.findNavController().navigate(action)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = ItemNewsListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val currentNewsResult = newsResults[position]
        holder.bind(currentNewsResult)
    }

    override fun getItemCount(): Int = newsResults.size

    fun setData(newData: NewsResponse) {
        val newsDiffUtil = DiffUtils(newsResults, newData.articles)
        val diffUtilResult = DiffUtil.calculateDiff(newsDiffUtil)
        newsResults = newData.articles
        diffUtilResult.dispatchUpdatesTo(this)
    }
}