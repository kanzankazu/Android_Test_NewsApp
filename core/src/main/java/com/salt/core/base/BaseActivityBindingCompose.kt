@file:Suppress("UNCHECKED_CAST")

package com.salt.core.base

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable

abstract class BaseActivityBindingCompose : ComponentActivity() {

    protected open fun setActivityResult() {}
    protected open fun setSubscribeToLiveData() {}
    protected open fun getBundleData() {}

    @Composable
    protected abstract fun SetContentCompose()
    protected open fun getData() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setActivityResult()
        getBundleData()
        setContent {
            SetContentCompose()
        }
        getData()
        setSubscribeToLiveData()
    }

    /**@return example R.menu.menu*/
    open fun setOptionMenu(): Int = -1

    /**when (id) { R.id.menuId -> sampleFun() }*/
    open fun setOptionMenuListener(id: Int, item: MenuItem) {}

    /**example MenuItem xxx = menu.findItem(R.id.xxx);*/
    open fun setOptionMenuValidation(menu: Menu) {}

    open fun onBackPressedListener(): Boolean = false

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        if (setOptionMenu() != -1) menuInflater.inflate(setOptionMenu(), menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        setOptionMenuListener(id, item)
        return super.onOptionsItemSelected(item)
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        setOptionMenuValidation(menu)
        return super.onPrepareOptionsMenu(menu)
    }
}
