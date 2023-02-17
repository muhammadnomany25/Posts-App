package com.stc.stcassignment.presentation.article.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.stc.stcassignment.R
import com.stc.stcassignment.databinding.FragmentArticleDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArticleDetailFragment : Fragment() {

    private lateinit var binding: FragmentArticleDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentArticleDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViewsDetails()
    }

    private fun setViewsDetails() {
        binding.tvTitle.text = arguments?.getString("title")
        binding.tvContent.text = arguments?.getString("content")
        Glide.with(binding.root)
            .applyDefaultRequestOptions(RequestOptions().placeholder(R.drawable.article_img_placeholder)
                .diskCacheStrategy(
                    DiskCacheStrategy.ALL))
            .load(arguments?.getString("image"))
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(binding.ivImage)
    }

}