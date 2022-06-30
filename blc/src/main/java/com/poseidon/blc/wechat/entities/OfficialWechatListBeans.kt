package com.poseidon.blc.wechat.entities

data class OfficialWechatListBeans(
    val data: List<WechatInfo>,
    val errorCode: Int,
    val errorMsg: String
) {

    data class WechatInfo(
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
