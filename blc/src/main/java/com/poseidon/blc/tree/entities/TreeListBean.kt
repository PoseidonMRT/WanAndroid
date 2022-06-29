package com.poseidon.blc.tree.entities

data class TreeListBean(
    val data: List<TreeGroup>,
    val errorCode: Int,
    val errorMsg: String
) {
    data class TreeGroup(

        val author: String,
        val children: List<TreeItem>,
        val courseId: Int,
        val cover: String,
        val desc: String,
        val id: Int,
        val lisense: String,
        val lisenseLink: String,
        val name: String,
        val order: Int,
        val parentChapterId: Int,
        val userControlSetTop: Boolean,
        val visible: Int
    ) {
        data class TreeItem(
            val author: String,
            val children: List<String>,
            val courseId: Int,
            val cover: String,
            val desc: String,
            val id: Int,
            val lisense: String,
            val lisenseLink: String,
            val name: String,
            val order: Int,
            val parentChapterId: Int,
            val userControlSetTop: Boolean,
            val visible: Int
        ) {

        }
    }
}