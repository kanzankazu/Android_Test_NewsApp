@file:Suppress("PackageDirectoryMismatch")

package com.salt.core.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragmentViewBinding<VB : ViewBinding> : Fragment() {
    private var localActivity: BaseActivityBindingView<*>? = null
    protected var mLayout = 0
    private var hasInitializedRootView = false
    private var rootView: View? = null
    private var isRestart: Boolean = false
    protected open fun setActivityResult() {}
    protected open fun setSubscribeToLiveData() {}
    protected open fun getBundleData() {}
    protected open fun setContent() {}
    protected open fun setListener() {}
    protected open fun getData() {}

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (activity is BaseActivityBindingView<*>) {
            localActivity = context as? BaseActivityBindingView<*>
        }
    }

    private var _binding: ViewBinding? = null
    abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB
    val bindFragment: VB
        get() = _binding as VB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        if (rootView == null) _binding = bindingInflater(inflater, container, false)
        return getPersistentView(_binding)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!hasInitializedRootView) {
            hasInitializedRootView = true
            getBundleData()
            setContent()
            setListener()
            getData()
            setActivityResult()
            setSubscribeToLiveData()
        }
    }

    private fun getPersistentView(layout: ViewBinding?): View? {
        if (rootView == null) rootView = layout?.root
        else (rootView?.parent as? ViewGroup)?.removeView(rootView)
        return rootView
    }
}
