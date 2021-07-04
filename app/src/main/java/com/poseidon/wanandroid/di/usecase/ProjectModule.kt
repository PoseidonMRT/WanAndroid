package com.poseidon.wanandroid.di.usecase

import com.poseidon.blc.project.service.ProjectService
import com.poseidon.blc.project.usecase.ProjectUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
class ProjectModule {

    @Provides
    fun getProjectService(retrofit: Retrofit): ProjectService {
        return retrofit.create(ProjectService::class.java)
    }

    @Provides
    fun getProjectUseCase(projectService: ProjectService): ProjectUseCase {
        return ProjectUseCase(projectService = projectService)
    }
}