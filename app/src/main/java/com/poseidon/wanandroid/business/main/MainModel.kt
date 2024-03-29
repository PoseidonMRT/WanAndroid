package com.poseidon.wanandroid.business.main

import com.poseidon.blc.banner.entities.BannerBeans
import com.poseidon.blc.banner.usecase.BannerUseCase
import com.poseidon.blc.callback.UseCaseRequestCallback
import com.poseidon.blc.callback.UseCaseRequestFailed
import com.poseidon.blc.common.ArticleBean
import com.poseidon.blc.home.entities.HomeArticleListBean
import com.poseidon.blc.home.entities.HotArticleListBean
import com.poseidon.blc.home.usecase.HomeUseCase
import com.poseidon.blc.recommend.entities.RecommendArticleBean
import com.poseidon.blc.recommend.usecase.RecommendDataUseCase
import com.poseidon.blc.square.entities.SquareResultBean
import com.poseidon.blc.square.usecase.SquareUseCase
import com.poseidon.blc.tree.entities.TreeListBean
import com.poseidon.blc.tree.usecase.TreeUseCase
import com.poseidon.blc.wechat.entities.OfficialWechatListBeans
import com.poseidon.blc.wechat.usecase.WechatUseCase
import com.poseidon.lib.common.callback.LoadDataCallback
import retrofit2.Call
import javax.inject.Inject

class MainModel @Inject constructor(
    var bannerUseCase: BannerUseCase,
    var recommendDataUseCase: RecommendDataUseCase,
    var homeUseCase: HomeUseCase,
    var treeUseCase: TreeUseCase,
    var wechatUseCase: WechatUseCase,
    var squareUseCase: SquareUseCase
) {
    fun getSquareInfo(
        loadDataCallback: LoadDataCallback<List<ArticleBean>>,
        pageIndex: Int
    ) {
        squareUseCase.getSquareDataInfo(pageIndex,
            object : UseCaseRequestCallback<SquareResultBean> {
                override fun onSuccess(t: SquareResultBean) {
                    var articleList = arrayListOf<ArticleBean>()
                    articleList.addAll(t.data.datas)
                    loadDataCallback.onSuccess(articleList)
                }

                override fun onFailed(call: Call<SquareResultBean>, t: UseCaseRequestFailed) {
                    loadDataCallback.onFailed(t.code, t.msg)
                }

                override fun onError(call: Call<SquareResultBean>, t: Throwable) {
                    t.message?.let { loadDataCallback.onFailed(9999, it) }
                }

            });
    }

    fun getBannerList(useCaseRequestCallback: UseCaseRequestCallback<BannerBeans>) {
        bannerUseCase.getBannerData(useCaseRequestCallback = useCaseRequestCallback)
    }

    fun getTopHotArticleList(useCaseRequestCallback: UseCaseRequestCallback<HotArticleListBean>) {
        homeUseCase.getTopHotArticleList(useCaseRequestCallback)
    }

    fun getTreeList(useCaseRequestCallback: UseCaseRequestCallback<TreeListBean>) {
        treeUseCase.getTreeList(useCaseRequestCallback)
    }

    fun getWechatList(useCaseRequestCallback: UseCaseRequestCallback<OfficialWechatListBeans>) {
        wechatUseCase.getWechatOfficialList(useCaseRequestCallback)
    }

    fun getArticleList(
        loadDataCallback: LoadDataCallback<List<ArticleBean>>,
        isFirstLoad: Boolean,
        pageIndex: Int
    ) {
        if (isFirstLoad) {
            recommendDataUseCase.getRecommendArticleList(object :
                UseCaseRequestCallback<RecommendArticleBean> {
                override fun onSuccess(t: RecommendArticleBean) {
                    getHomeArticleList(loadDataCallback = loadDataCallback, t.data, pageIndex)
                }

                override fun onFailed(call: Call<RecommendArticleBean>, t: UseCaseRequestFailed) {
                    loadDataCallback.onFailed(t.code, t.msg)
                }

                override fun onError(call: Call<RecommendArticleBean>, t: Throwable) {
                    t.message?.let { loadDataCallback.onFailed(9999, it) }
                }
            })
        } else {
            getHomeArticleList(loadDataCallback = loadDataCallback, emptyList(), pageIndex)
        }
    }

    private fun getHomeArticleList(
        loadDataCallback: LoadDataCallback<List<ArticleBean>>,
        datas: List<ArticleBean>,
        pageIndex: Int
    ) {
        homeUseCase.getHomeArticleList(pageIndex = pageIndex,
            object : UseCaseRequestCallback<HomeArticleListBean> {
                override fun onSuccess(t: HomeArticleListBean) {
                    var articleList = arrayListOf<ArticleBean>()
                    articleList.addAll(datas)
                    articleList.addAll(t.data.datas)
                    loadDataCallback.onSuccess(articleList)
                }

                override fun onFailed(call: Call<HomeArticleListBean>, t: UseCaseRequestFailed) {
                    loadDataCallback.onFailed(t.code, t.msg)
                }

                override fun onError(call: Call<HomeArticleListBean>, t: Throwable) {
                    t.message?.let { loadDataCallback.onFailed(9999, it) }
                }
            })
    }
}