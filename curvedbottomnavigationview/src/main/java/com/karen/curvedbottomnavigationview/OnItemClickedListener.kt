package com.karen.curvedbottomnavigationview

interface OnItemClickedListener {
    fun onItemClicked(item: NavigationItem)
    fun onItemReClicked(item: NavigationItem)
    fun onCenterItemClicked()
    fun onCenterItemReClicked()
}
