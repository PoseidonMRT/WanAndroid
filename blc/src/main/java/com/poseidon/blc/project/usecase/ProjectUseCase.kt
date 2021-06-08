package com.poseidon.blc.project.usecase

import com.poseidon.blc.callback.ERROR_CODE_ILLEGAL_DATA
import com.poseidon.blc.callback.UseCaseRequestCallback
import com.poseidon.blc.callback.UseCaseRequestFailed
import com.poseidon.blc.project.entities.CategoryProjectListBean
import com.poseidon.blc.project.entities.LatestProjectListBean
import com.poseidon.blc.project.entities.ProjectTypeListBean
import com.poseidon.blc.project.service.ProjectService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class ProjectUseCase @Inject constructor(var projectService: ProjectService) {
    fun getLatestProjectList(
        id: Int,
        requestCallback: UseCaseRequestCallback<LatestProjectListBean>
    ) {
        projectService.getLatestProjectList(id).enqueue(object : Callback<LatestProjectListBean> {
            override fun onResponse(
                call: Call<LatestProjectListBean>,
                response: Response<LatestProjectListBean>
            ) {
                if (response.isSuccessful) {
                    var latestProjectListBean = response.body()
                    if (latestProjectListBean != null) {
                        requestCallback.onSuccess(latestProjectListBean)
                    } else {
                        requestCallback.onFailed(
                            call, UseCaseRequestFailed(
                                ERROR_CODE_ILLEGAL_DATA, "latestprojectlistbean beans is null"
                            )
                        )
                    }
                } else {
                    requestCallback.onFailed(
                        call, UseCaseRequestFailed(
                            response.code(), "getLatestProjectList request failed"
                        )
                    )
                }
            }

            override fun onFailure(call: Call<LatestProjectListBean>, t: Throwable) {
                requestCallback.onError(call, t)
            }
        })
    }

    fun getProjectType(requestCallback: UseCaseRequestCallback<ProjectTypeListBean>) {
        projectService.getProjectType().enqueue(object : Callback<ProjectTypeListBean> {
            override fun onResponse(
                call: Call<ProjectTypeListBean>,
                response: Response<ProjectTypeListBean>
            ) {
                if (response.isSuccessful) {
                    var projectTypeListBean = response.body()
                    if (projectTypeListBean != null) {
                        requestCallback.onSuccess(projectTypeListBean)
                    } else {
                        requestCallback.onFailed(
                            call, UseCaseRequestFailed(
                                ERROR_CODE_ILLEGAL_DATA, "projectTypeListBean beans is null"
                            )
                        )
                    }
                } else {
                    requestCallback.onFailed(
                        call, UseCaseRequestFailed(
                            response.code(), "getProjectType request failed"
                        )
                    )
                }
            }

            override fun onFailure(call: Call<ProjectTypeListBean>, t: Throwable) {
                requestCallback.onError(call, t)
            }
        })
    }

    fun getProjectListByTypeAndPage(
        pageIndex: Int,
        cid: Int,
        requestCallback: UseCaseRequestCallback<CategoryProjectListBean>
    ) {
        projectService.getProjectListByTypeAndPage(pageIndex, cid)
            .enqueue(object : Callback<CategoryProjectListBean> {
                override fun onResponse(
                    call: Call<CategoryProjectListBean>,
                    response: Response<CategoryProjectListBean>
                ) {
                    if (response.isSuccessful) {
                        var categoryProjectListBean = response.body()
                        if (categoryProjectListBean != null) {
                            requestCallback.onSuccess(categoryProjectListBean)
                        } else {
                            requestCallback.onFailed(
                                call, UseCaseRequestFailed(
                                    ERROR_CODE_ILLEGAL_DATA, "categoryProjectListBean beans is null"
                                )
                            )
                        }
                    } else {
                        requestCallback.onFailed(
                            call, UseCaseRequestFailed(
                                response.code(), "getProjectListByTypeAndPage request failed"
                            )
                        )
                    }
                }

                override fun onFailure(call: Call<CategoryProjectListBean>, t: Throwable) {
                    requestCallback.onError(call, t)
                }
            })
    }
}