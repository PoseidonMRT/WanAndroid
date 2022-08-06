package com.poseidon.wanandroid.di.usecase

import com.poseidon.blc.square.service.SquareDataService
import com.poseidon.blc.square.usecase.SquareUseCase
import com.poseidon.blc.tree.service.TreeDataService
import com.poseidon.blc.tree.usecase.TreeUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
class SquareModule {
    @Provides
    fun getSquareDataService(retrofit: Retrofit): SquareDataService {
        return retrofit.create(SquareDataService::class.java)
    }

    @Provides
    fun getSquareUseCase(squareDataService: SquareDataService): SquareUseCase {
        return SquareUseCase(squareDataService = squareDataService)
    }
}