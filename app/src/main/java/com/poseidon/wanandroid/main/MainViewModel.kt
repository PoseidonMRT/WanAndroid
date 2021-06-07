package com.poseidon.wanandroid.main

import androidx.hilt.lifecycle.ViewModelInject
import com.poseidon.blc.banner.entities.BannerBeans
import com.poseidon.blc.callback.UseCaseRequestCallback
import com.poseidon.blc.callback.UseCaseRequestFailed
import com.poseidon.lib.common.base.BaseViewModel
import com.poseidon.lib.common.base.ViewState
import retrofit2.Call

class MainViewModel @ViewModelInject constructor(var mainModel: MainModel) :
    BaseViewModel<MainViewState>() {


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
                TODO("Not yet implemented")
            }

            override fun onError(call: Call<BannerBeans>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    override fun getViewState(): MainViewState {
        return MainViewState(false)
    }
}

class MainViewState(isLoading: Boolean) : ViewState(isLoading) {
    var banners = arrayListOf<BannerData>()

    companion object {
        fun createMainViewState(state: MainViewState): MainViewState {
            var mainState: MainViewState = MainViewState(state.isLoading)
            mainState.banners = state.banners
            return mainState
        }
    }
}