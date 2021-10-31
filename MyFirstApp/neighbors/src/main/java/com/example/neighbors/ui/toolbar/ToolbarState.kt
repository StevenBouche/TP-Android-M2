package com.example.neighbors.ui.toolbar

import com.example.neighbors.R

enum class ToolbarState(val switchEnabled: Boolean, val favoriteEnabled: Boolean, val iconAction: Int?) {

    LIST(true, true, R.drawable.ic_baseline_person_add_24),
    NEW(false, false,null),
    EDIT(false, false,null),
    DETAIL(false, false, R.drawable.ic_baseline_edit_24);

}