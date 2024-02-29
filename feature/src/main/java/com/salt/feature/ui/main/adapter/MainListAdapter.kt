package com.salt.feature.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.salt.core.component.recyclerview.base.BaseRecyclerViewFilterAdapter
import com.salt.core.ext.loadImage
import com.salt.core.ext.use
import com.salt.core.ext.visibleView
import com.salt.data.api.news.model.TopHeadline
import com.salt.feature.databinding.ItemActivityMainFragmentListBigBinding
import com.salt.feature.databinding.ItemActivityMainFragmentListSmallBinding

class MainListAdapter : BaseRecyclerViewFilterAdapter<TopHeadline.Article>() {

    var listener: (TopHeadline.Article) -> Unit = {}

    override fun onMultipleViewType(position: Int, data: TopHeadline.Article): Int {
        return if (position == 0 && data.urlToImage.isNotEmpty()) BIG else SMALL
    }

    override fun onCreateView(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == BIG) MainListAdapterHolder(ItemActivityMainFragmentListBigBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        else MainListAdapterSmallHolder(ItemActivityMainFragmentListSmallBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindView(data: TopHeadline.Article, position: Int, holder: RecyclerView.ViewHolder) {
        when (holder) {
            is MainListAdapterHolder -> holder.setContent(data)
            is MainListAdapterSmallHolder -> holder.setContent(data)
        }
    }

    inner class MainListAdapterHolder(private val inflate: ItemActivityMainFragmentListBigBinding) : RecyclerView.ViewHolder(inflate.root) {
        fun setContent(data: TopHeadline.Article) = inflate.use {
            ivItemActivityMainFragmentList.loadImage(data.urlToImage)
            tvItemActivityMainFragmentList.text = data.title
            tvItemActivityMainFragmentListDate.text = data.publishedAt
            root.setOnClickListener { listener.invoke(data) }
        }
    }

    inner class MainListAdapterSmallHolder(private val inflate: ItemActivityMainFragmentListSmallBinding) : RecyclerView.ViewHolder(inflate.root) {
        fun setContent(data: TopHeadline.Article) = inflate.use {
            ivItemActivityMainFragmentListSmall.visibleView(data.urlToImage.isNotEmpty())
            ivItemActivityMainFragmentListSmall.loadImage(data.urlToImage)
            tvItemActivityMainFragmentList.text = data.title
            tvItemActivityMainFragmentListDate.text = data.publishedAt
            root.setOnClickListener { listener.invoke(data) }
        }
    }

    companion object {
        val BIG: Int = 0
        val SMALL: Int = 1
    }
}
