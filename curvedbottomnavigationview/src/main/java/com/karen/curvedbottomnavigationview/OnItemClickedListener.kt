package com.karen.curvedbottomnavigationview

interface OnItemClickedListener {
    fun onItemClicked(item: com.karen.curvedbottomnavigationview.NavigationItem)
    fun onCenterItemClicked()
    fun onCenterItemReClicked()
    fun onItemReClicked(item: com.karen.curvedbottomnavigationview.NavigationItem)
}
