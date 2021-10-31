package com.example.neighbors.ui

import android.view.View
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.example.neighbors.ui.toolbar.ToolbarState

interface NavigationListener {

    fun showFragment(fragment: Fragment)
    fun backFragment()
    fun updateTitle(@StringRes title: Int)
    fun setToolbarState(state: ToolbarState, actionButton: View.OnClickListener?)

}