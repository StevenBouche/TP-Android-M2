package com.example.neighbors.ui.toolbar

import android.view.View
import androidx.core.view.isVisible
import com.example.neighbors.dal.repositories.ModeDataSource
import com.example.neighbors.dal.repositories.NeighborRepository
import com.example.neighbors.databinding.SwitchItemBinding
import com.example.neighbors.di.DI

class ToolbarHandler(private val bindingSwitch: SwitchItemBinding) {

    private var repository: NeighborRepository = DI.repository

    init {
        addActionSwitch()
    }

    fun setToolbarState(state: ToolbarState, actionButton: View.OnClickListener?){
        //bindingSwitch.actionFavorite.isVisible = state.favoriteEnabled
        bindingSwitch.switchMode.isEnabled = state.switchEnabled
        bindingSwitch.actionUser.isVisible = state.iconAction != null
        bindingSwitch.actionUser.setOnClickListener(actionButton)
        state.iconAction?.let { bindingSwitch.actionUser.setImageResource(it) }
    }

    /**
     * Add action switch data source repository
     */
    private fun addActionSwitch() {
        bindingSwitch.switchMode.setOnCheckedChangeListener { _, b -> actionModeDataSource(b) }
        actionModeDataSource(bindingSwitch.switchMode.isChecked)
    }

    /**
     * Set mode data source in function of state switch
     */
    private fun actionModeDataSource(value: Boolean){
        val mode = ModeDataSource.getModeFunctionOfCheck(value);
        repository.modeDataSource = mode
        bindingSwitch.textView.text = mode.text
    }

}