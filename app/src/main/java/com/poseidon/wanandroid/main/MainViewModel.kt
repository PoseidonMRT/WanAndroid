package com.poseidon.wanandroid.main

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import com.poseidon.blc.banner.entities.BannerBeans
import com.poseidon.blc.callback.UseCaseRequestCallback
import com.poseidon.blc.callback.UseCaseRequestFailed
import com.poseidon.blc.recommend.entities.RecommendArticleBean
import com.poseidon.lib.common.base.BaseViewModel
import com.poseidon.lib.common.base.ViewState
import retrofit2.Call

class MainViewModel @ViewModelInject constructor(var mainModel: MainModel) :
    BaseViewModel<MainViewState>() {
    val TAG = "MainViewModel"

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

        mainModel.getRecommendArticleList(object : UseCaseRequestCallback<RecommendArticleBean> {
            override fun onSuccess(t: RecommendArticleBean) {
                var state: MainViewState = MainViewState.createMainViewState(viewState.value!!)
                state.recommendArticleBean = t
                viewState.postValue(state)
            }

            override fun onFailed(call: Call<RecommendArticleBean>, t: UseCaseRequestFailed) {
                Log.d(TAG, t.toString())
            }

            override fun onError(call: Call<RecommendArticleBean>, t: Throwable) {
                Log.e(TAG, "getRecommendArticleList error", t)
            }
        })
    }

    override fun getViewState(): MainViewState {
        return MainViewState(false)
    }
}

class MainViewState(isLoading: Boolean) : ViewState(isLoading) {
    var banners = arrayListOf<BannerData>()
    var recommendArticleBean: RecommendArticleBean? = null

    companion object {
        fun createMainViewState(state: MainViewState): MainViewState {
            var mainState: MainViewState = MainViewState(state.isLoading)
            mainState.banners = state.banners
            mainState.recommendArticleBean = state.recommendArticleBean
            return mainState
        }
    }
}