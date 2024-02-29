package com.salt.feature.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.salt.core.base.BaseFragmentViewBinding
import com.salt.core.base.baseresponse.handleBaseResponse
import com.salt.core.ext.loadMoreListener
import com.salt.core.ext.simpleToast
import com.salt.core.ext.toArrayList
import com.salt.core.ext.use
import com.salt.core.ext.visibleView
import com.salt.core.ext.vmLoadDataRe
import com.salt.feature.databinding.FragmentMainBinding
import com.salt.feature.navigator.DetailNavigation
import com.salt.feature.ui.main.adapter.MainCatalogAdapter
import com.salt.feature.ui.main.adapter.MainListAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : BaseFragmentViewBinding<FragmentMainBinding>() {

    @Inject
    lateinit var detailNavigation: DetailNavigation

    private val recyclerAdapterCatalog by lazy { MainCatalogAdapter() }
    private val recyclerAdapterList by lazy { MainListAdapter() }

    private val viewModel by activityViewModels<MainViewModel>()

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentMainBinding
        get() = FragmentMainBinding::inflate

    override fun setContent() = bindFragment.use {
        rvFragmentMainCategory.setRecyclerLinearHorizontal(recyclerAdapterCatalog, 8)
        rvFragmentMainList.setRecyclerLinearVertical(recyclerAdapterList, 8)

        recyclerAdapterCatalog.setData(viewModel.categories)
        recyclerAdapterCatalog.setCheckPosition(0)
        recyclerAdapterCatalog.listener = {
            viewModel.categorySelected = it
            viewModel.currentPage = 1
            viewModel.getTopHeadlines()
        }
        recyclerAdapterList.listener = {
            val intent = detailNavigation.createIntent(requireContext(), it.url)
            startActivity(intent)
        }
    }

    override fun setListener() = bindFragment.use {
        srlFragmentMain.setOnRefreshListener {
            viewModel.currentPage = 1
            viewModel.getTopHeadlines()
        }
        nsvFragmentMain.loadMoreListener {
            if (!viewModel.isLoadMore && recyclerAdapterList.itemCount < viewModel.totalPage) {
                viewModel.currentPage = viewModel.currentPage + 1
                viewModel.isLoadMore = true
                viewModel.getTopHeadlines()
            }
        }
    }

    override fun getData() {
        viewModel.getTopHeadlines()
    }

    override fun setSubscribeToLiveData() {
        vmLoadDataRe(viewModel.getTopHeadlines) {
            it.handleBaseResponse(
                onLoading = {
                    if (viewModel.isLoadMore) bindFragment.pbActivityMain.visibleView(it)
                    else bindFragment.srlFragmentMain.isRefreshing = it
                },
                onError = { requireContext().simpleToast(it) },
                onSuccess = {
                    viewModel.totalPage = it.second
                    if (viewModel.isLoadMore) {
                        recyclerAdapterList.addDataS(it.first.toArrayList())
                        viewModel.isLoadMore = false
                    } else {
                        recyclerAdapterList.setData(it.first.toArrayList())
                    }
                }
            )
        }
    }

    companion object
}