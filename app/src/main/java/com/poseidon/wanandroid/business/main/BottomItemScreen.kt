package com.poseidon.wanandroid.business.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.poseidon.wanandroid.R

sealed class BottomItemScreen(val route: String, val title: Int, val icon: ImageVector) {

    object HOME : BottomItemScreen(HOME_ITEM, R.string.main_item_home, Icons.Default.Home)
    object SQUARE :
        BottomItemScreen(SQUARE_ITEM, R.string.main_item_square, Icons.Default.Dashboard)

    object TREE : BottomItemScreen(TREE_ITEM, R.string.main_item_tree, Icons.Default.List)
    object WECHAT :
        BottomItemScreen(WECHAT_ITEM, R.string.main_item_wechat, Icons.Default.CleanHands)

    companion object {
        const val HOME_ITEM = "home"
        const val SQUARE_ITEM = "square"
        const val TREE_ITEM = "tree"
        const val WECHAT_ITEM = "wechat"
    }
}