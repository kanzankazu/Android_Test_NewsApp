package com.salt.feature.ui.main.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.salt.core.component.recyclerview.base.BaseRecyclerViewFilterAdapter
import com.salt.core.ext.use
import com.salt.feature.R
import com.salt.feature.databinding.ItemActivityMainFragmentCategoryBinding

class MainCatalogAdapter : BaseRecyclerViewFilterAdapter<String>() {

    var listener: (String) -> Unit = {}

    override fun onCreateView(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MainCatalogAdapterHolder(ItemActivityMainFragmentCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindView(data: String, position: Int, holder: RecyclerView.ViewHolder) {
        when (holder) {
            is MainCatalogAdapterHolder -> holder.setContent(data)
        }
    }

    inner class MainCatalogAdapterHolder(private val inflate: ItemActivityMainFragmentCategoryBinding) : RecyclerView.ViewHolder(inflate.root) {
        @SuppressLint("NotifyDataSetChanged")
        fun setContent(data: String) = inflate.use {
            val isChecked = getCheckPosition() == adapterPosition
            tvItemActivityMainFragmentCategory.background = ContextCompat.getDrawable(root.context, if (isChecked) R.color.colorAccent else R.drawable.bg_gradient_w75tw75)
            tvItemActivityMainFragmentCategory.text = data
            root.setOnClickListener {
                listener.invoke(data)
                setCheckPosition(adapterPosition)
                notifyDataSetChanged()
            }
        }
    }
}
