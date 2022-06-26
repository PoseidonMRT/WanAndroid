package com.poseidon.wanandroid.business.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomItemScreen(val route: String, val title: String, val icon: ImageVector) {

    object HOME : BottomItemScreen(HOME_ITEM, "首页", Icons.Default.Home)
    object STAR : BottomItemScreen(COLLECTION_ITEM, "收藏", Icons.Default.Favorite)
    companion object {
        const val HOME_ITEM = "home"
        const val COLLECTION_ITEM = "collection"
    }
}