package com.poseidon.blc.knowledge.entities

data class KnowledgeListBean(
    val data: List<DataBean>,
    val errorCode: Int,
    val errorMsg: String
) {

    data class DataBean(
        val children: List<ChildrenBean>,
        val courseId: Int,
        val id: Int,
        val name: String,
        val order: Int,
        val parentChapterId: Int,
        val userControlSetTop: Boolean,
        val visible: Int
    ) {

        data class ChildrenBean(
            val children: List<Any?>,
            val courseId: Int,
            val id: Int,
            val name: String,
            val order: Int,
            val parentChapterId: Int,
            val userControlSetTop: Boolean,
            val visible: Int
        ) {

        }
    }
}