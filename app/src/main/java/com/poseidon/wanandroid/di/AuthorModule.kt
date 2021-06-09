package com.poseidon.wanandroid.di

import com.poseidon.blc.author.service.AuthorService
import com.poseidon.blc.author.usecase.AuthorUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import retrofit2.Retrofit

@Module
@InstallIn(ActivityComponent::class)
class AuthorModule {

    @Provides
    fun getAuthorService(retrofit: Retrofit): AuthorService {
        return retrofit.create(AuthorService::class.java)
    }

    @Provides
    fun getAuthUseCase(authorService: AuthorService): AuthorUseCase {
        return AuthorUseCase(authorService = authorService)
    }
}