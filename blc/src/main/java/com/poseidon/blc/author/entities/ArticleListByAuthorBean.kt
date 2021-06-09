package com.poseidon.blc.author.entities

data class ArticleListByAuthorBean(
    val data: DataBean,
    val errorCode: Int,
    val errorMsg: String
) {

    data class DataBean(
        val curPage: Int,
        val datas: List<DatasBean>,
        val offset: Int,
        val over: Boolean,
        val pageCount: Int,
        val size: Int,
        val total: Int
    ) {

        data class DatasBean(
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
            val publishTime: Int,
            val realSuperChapterId: Int,
            val selfVisible: Int,
            val shareDate: Int,
            val shareUser: String,
            val superChapterId: Int,
            val superChapterName: String,
            val tags: List<TagsBean>,
            val title: String,
            val type: Int,
            val userId: Int,
            val visible: Int,
            val zan: Int
        ) {

            data class TagsBean(
                val name: String,
                val url: String
            )
        }
    }
}