package com.example.neighbors.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.fragment.app.Fragment
import com.example.neighbors.R
import com.example.neighbors.databinding.ActivityMainBinding
import com.example.neighbors.databinding.SwitchItemBinding
import com.example.neighbors.di.DI
import com.example.neighbors.ui.fragments.list.ListNeighborsFragment
import com.example.neighbors.ui.toolbar.ToolbarHandler
import com.example.neighbors.ui.toolbar.ToolbarState

class MainActivity : AppCompatActivity(), NavigationListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var toolbarHandler: ToolbarHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        DI.inject(application)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        //Attach menu to binding
        binding.toolbar.inflateMenu(R.menu.menu)
        with(binding.toolbar.menu) {
            findItem(R.id.app_bar_switch)?.actionView?.let {
                toolbarHandler = ToolbarHandler(SwitchItemBinding.bind(it))
            }
        }

        //Show first fragment after menu is setup
        showFragment(ListNeighborsFragment(this))

        return true
    }

    override fun setToolbarState(state: ToolbarState, actionButton: View.OnClickListener?) {
        toolbarHandler.setToolbarState(state, actionButton)
    }

    override fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(binding.fragmentContainer.id, fragment)
            addToBackStack(null)
        }.commit()
    }

    override fun backFragment() {
        supportFragmentManager.popBackStack()
    }

    override fun updateTitle(title: Int) {
        binding.toolbar.setTitle(title)
    }


}