package com.poseidon.blc.recommend.entities

data class RecommendArticleBean(
    val data: List<DataBean>,
    val errorCode: Int,
    val errorMsg: String
) {

    data class DataBean(
        val apkLink: String,
        val audit: Int,
        val author: String,
        val canEdit: Boolean,
        val chapterId: Int,
        val chapterName: String,
        val collect: Boolean,
        val courseId: Int,
        val desc: String,
        val descMd: String,
        val envelopePic: String,
        val fresh: Boolean,
        val host: String,
        val id: Int,
        val link: String,
        val niceDate: String,
        val niceShareDate: String,
        val origin: String,
        val prefix: String,
        val projectLink: String,
        val publishTime: Long,
        val realSuperChapterId: Int,
        val selfVisible: Int,
        val shareDate: String,
        val shareUser: String,
        val superChapterId: Int,
        val superChapterName: String,
        val tags: List<Any?>,
        val title: String,
        val type: Int,
        val userId: Int,
        val visible: Int,
        val zan: Long
    ) {
        override fun toString(): String {
            return "DataBean(apkLink='$apkLink', audit=$audit, author='$author', canEdit=$canEdit, chapterId=$chapterId, chapterName='$chapterName', collect=$collect, courseId=$courseId, desc='$desc', descMd='$descMd', envelopePic='$envelopePic', fresh=$fresh, host='$host', id=$id, link='$link', niceDate='$niceDate', niceShareDate='$niceShareDate', origin='$origin', prefix='$prefix', projectLink='$projectLink', publishTime=$publishTime, realSuperChapterId=$realSuperChapterId, selfVisible=$selfVisible, shareDate=$shareDate, shareUser='$shareUser', superChapterId=$superChapterId, superChapterName='$superChapterName', tags=$tags, title='$title', type=$type, userId=$userId, visible=$visible, zan=$zan)"
        }
    }

    override fun toString(): String {
        return "RecommendArticleBean(data=$data, errorCode=$errorCode, errorMsg='$errorMsg')"
    }
}