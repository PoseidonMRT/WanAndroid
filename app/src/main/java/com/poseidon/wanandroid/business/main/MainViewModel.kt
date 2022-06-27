package com.poseidon.wanandroid.business.main

import android.util.Log
import com.poseidon.blc.banner.entities.BannerBeans
import com.poseidon.blc.callback.UseCaseRequestCallback
import com.poseidon.blc.callback.UseCaseRequestFailed
import com.poseidon.blc.common.ArticleBean
import com.poseidon.lib.common.base.BaseViewModel
import com.poseidon.lib.common.base.ViewState
import com.poseidon.lib.common.callback.LoadDataCallback
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(var mainModel: MainModel) :
    BaseViewModel<MainViewState>() {
    val TAG = "MainViewModel"
    var currentPageIndex = 0;

    fun loadData() {
        mainModel.getBannerList(object : UseCaseRequestCallback<BannerBeans> {
            override fun onSuccess(t: BannerBeans) {
                var state: MainViewState = MainViewState.createMainViewState(viewState.value!!)
                t.data.forEach { it ->
                    var bannerData = BannerData(it.imagePath)
                    state.banners.add(bannerData)
                }
                viewState.postValue(state)
            }

            override fun onFailed(call: Call<BannerBeans>, t: UseCaseRequestFailed) {
                Log.d(TAG, t.toString())
            }

            override fun onError(call: Call<BannerBeans>, t: Throwable) {
                Log.e(TAG, "getBannerList error", t)
            }
        })

        mainModel.getArticleList(object : LoadDataCallback<List<ArticleBean>> {
            override fun onSuccess(t: List<ArticleBean>) {
                var state: MainViewState = MainViewState.createMainViewState(viewState.value!!)
                state.recommendArticleBean = t
                viewState.postValue(state)
            }

            override fun onFailed(code: Int, msg: String) {

            }
        }, true, currentPageIndex)
    }

    fun setCurrentBottomItem(currentBottomItem: String) {
        var state: MainViewState = MainViewState.createMainViewState(viewState.value!!)
        state.currentSelectedItem = currentBottomItem
        viewState.postValue(state)
    }

    override fun getViewState(): MainViewState {
        return MainViewState(false)
    }
}

class MainViewState(isLoading: Boolean) : ViewState(isLoading) {
    var banners = arrayListOf<BannerData>()
    var recommendArticleBean: List<ArticleBean>? = null
    var currentSelectedItem: String = BottomItemScreen.HOME_ITEM

    companion object {
        fun createMainViewState(state: MainViewState): MainViewState {
            var mainState: MainViewState = MainViewState(state.isLoading)
            mainState.banners = state.banners
            mainState.recommendArticleBean = state.recommendArticleBean
            mainState.currentSelectedItem = state.currentSelectedItem
            return mainState
        }
    }
}