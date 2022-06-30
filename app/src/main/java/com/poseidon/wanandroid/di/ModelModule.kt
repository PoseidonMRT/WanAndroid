package com.poseidon.wanandroid.di

import com.poseidon.blc.banner.usecase.BannerUseCase
import com.poseidon.blc.home.usecase.HomeUseCase
import com.poseidon.blc.recommend.usecase.RecommendDataUseCase
import com.poseidon.blc.tree.usecase.TreeUseCase
import com.poseidon.blc.wechat.usecase.WechatUseCase
import com.poseidon.wanandroid.business.main.MainModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class ModelModule {
    @Provides
    fun getMainModel(
        bannerUseCase: BannerUseCase,
        recommendDataUseCase: RecommendDataUseCase,
        homeUseCase: HomeUseCase,
        treeUseCase: TreeUseCase,
        wechatUseCase: WechatUseCase
    ): MainModel {
        return MainModel(
            bannerUseCase = bannerUseCase,
            recommendDataUseCase = recommendDataUseCase,
            homeUseCase = homeUseCase,
            treeUseCase = treeUseCase,
            wechatUseCase = wechatUseCase
        )
    }
}