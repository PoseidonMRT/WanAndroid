package com.poseidon.wanandroid.business.main

import android.util.Log
import com.poseidon.blc.banner.entities.BannerBeans
import com.poseidon.blc.callback.UseCaseRequestCallback
import com.poseidon.blc.callback.UseCaseRequestFailed
import com.poseidon.blc.common.ArticleBean
import com.poseidon.blc.home.entities.HotArticleListBean
import com.poseidon.blc.tree.entities.TreeListBean
import com.poseidon.blc.wechat.entities.OfficialWechatListBeans
import com.poseidon.lib.common.base.BaseViewModel
import com.poseidon.lib.common.base.ViewState
import com.poseidon.lib.common.callback.LoadDataCallback
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(var mainModel: MainModel) :
    BaseViewModel<MainViewState>() {
    val tag = "MainViewModel"
    var currentPageIndex = 0

    fun loadData() {
        var currentItem = viewState.value?.currentSelectedItem;
        when (currentItem) {
            BottomItemScreen.HOME_ITEM -> {
                loadHomeData()
            }
            BottomItemScreen.SQUARE_ITEM -> {
                loadSquareData()
            }
            BottomItemScreen.TREE_ITEM -> {
                loadTreeData()
            }
            BottomItemScreen.WECHAT_ITEM -> {
                loadWechatData()
            }
        }
    }

    private fun loadHomeData() {
        mainModel.getBannerList(object : UseCaseRequestCallback<BannerBeans> {
            override fun onSuccess(t: BannerBeans) {
                var state: MainViewState = MainViewState.createMainViewState(viewState.value!!)
                t.data.forEach { it ->
                    var bannerData = BannerData(it.imagePath)
                    state.banners.add(bannerData)
                }
                viewState.value = state
                viewState.postValue(state)
            }

            override fun onFailed(call: Call<BannerBeans>, t: UseCaseRequestFailed) {
                Log.d(tag, t.toString())
            }

            override fun onError(call: Call<BannerBeans>, t: Throwable) {
                Log.e(tag, "getBannerList error", t)
            }
        })

        mainModel.getTopHotArticleList(object : UseCaseRequestCallback<HotArticleListBean> {
            override fun onSuccess(t: HotArticleListBean) {
                if (t.errorCode == 0 && t.data.isNotEmpty()) {
                    var state: MainViewState = MainViewState.createMainViewState(viewState.value!!)
                    state.hotArticleList = t.data
                    viewState.value = state
                    viewState.postValue(state)
                }
            }

            override fun onFailed(call: Call<HotArticleListBean>, t: UseCaseRequestFailed) {
                Log.d(tag, t.toString())
            }

            override fun onError(call: Call<HotArticleListBean>, t: Throwable) {
                Log.e(tag, "getTopHotArticleList error", t)
            }

        })

        mainModel.getArticleList(object : LoadDataCallback<List<ArticleBean>> {
            override fun onSuccess(t: List<ArticleBean>) {
                var state: MainViewState = MainViewState.createMainViewState(viewState.value!!)
                state.recommendArticleBean = t
                viewState.value = state
                viewState.postValue(state)
            }

            override fun onFailed(code: Int, msg: String) {
                Log.d(tag, "getArticleList failed code:$code,msg:$msg")
            }
        }, true, currentPageIndex)
    }

    private fun loadSquareData() {

    }

    private fun loadTreeData() {
        mainModel.getTreeList(object : UseCaseRequestCallback<TreeListBean> {
            override fun onSuccess(t: TreeListBean) {
                var state: MainViewState = MainViewState.createMainViewState(viewState.value!!)
                state.treeGroupList = t.data
                viewState.value = state
                viewState.postValue(state)
            }

            override fun onFailed(call: Call<TreeListBean>, t: UseCaseRequestFailed) {
                Log.d(tag, t.toString())
            }

            override fun onError(call: Call<TreeListBean>, t: Throwable) {
                Log.e(tag, "getTreeList error", t)
            }

        })
    }

    private fun loadWechatData() {
        mainModel.getWechatList(object : UseCaseRequestCallback<OfficialWechatListBeans> {
            override fun onSuccess(t: OfficialWechatListBeans) {
                var state: MainViewState = MainViewState.createMainViewState(viewState.value!!)
                state.wechatInfo = t.data
                viewState.value = state
                viewState.postValue(state)
            }

            override fun onFailed(call: Call<OfficialWechatListBeans>, t: UseCaseRequestFailed) {
                Log.d(tag, t.toString())
            }

            override fun onError(call: Call<OfficialWechatListBeans>, t: Throwable) {
                Log.e(tag, "getWechatList error", t)
            }

        })
    }

    fun updateCurrentBottomItem(currentBottomItem: String) {
        var state: MainViewState = MainViewState.createMainViewState(viewState.value!!)
        state.currentSelectedItem = currentBottomItem
        viewState.value = state
        viewState.postValue(state)
        loadData()
    }

    override fun getViewState(): MainViewState {
        return MainViewState(false)
    }
}

class MainViewState(isLoading: Boolean) : ViewState(isLoading) {
    var banners = arrayListOf<BannerData>()
    var recommendArticleBean: List<ArticleBean>? = null
    var currentSelectedItem: String = BottomItemScreen.HOME_ITEM
    var hotArticleList: List<HotArticleListBean.HotArticleBean>? = null
    var treeGroupList: List<TreeListBean.TreeGroup>? = null
    var wechatInfo: List<OfficialWechatListBeans.WechatInfo>? = null

    companion object {
        fun createMainViewState(state: MainViewState): MainViewState {
            var mainState = MainViewState(state.isLoading)
            mainState.banners = state.banners
            mainState.recommendArticleBean = state.recommendArticleBean
            mainState.currentSelectedItem = state.currentSelectedItem
            mainState.hotArticleList = state.hotArticleList
            mainState.treeGroupList = state.treeGroupList
            mainState.wechatInfo = state.wechatInfo
            return mainState
        }
    }
}