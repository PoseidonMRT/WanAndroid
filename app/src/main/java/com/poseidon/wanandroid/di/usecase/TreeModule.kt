package com.poseidon.wanandroid.di.usecase

import com.poseidon.blc.tree.service.TreeDataService
import com.poseidon.blc.tree.usecase.TreeUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
class TreeModule {

    @Provides
    fun getTreeDataService(retrofit: Retrofit): TreeDataService {
        return retrofit.create(TreeDataService::class.java)
    }

    @Provides
    fun getTreeUseCase(treeDataService: TreeDataService): TreeUseCase {
        return TreeUseCase(treeDataService = treeDataService)
    }
}