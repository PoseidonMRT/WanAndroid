package com.poseidon.blc.project.service

import com.poseidon.blc.project.entities.CategoryProjectListBean
import com.poseidon.blc.project.entities.LatestProjectListBean
import com.poseidon.blc.project.entities.ProjectTypeListBean
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ProjectService {
    @GET("article/listproject/{pageIndex}/json")
    fun getLatestProjectList(@Path("pageIndex") id: Int): Call<LatestProjectListBean>

    @GET("project/tree/json")
    fun getProjectType(): Call<ProjectTypeListBean>

    @GET("project/list/{pageIndex}/json?cid={cid}")
    fun getProjectListByTypeAndPage(
        @Path("pageIndex") pageIndex: Int,
        @Path("cid") cid: Int
    ): Call<CategoryProjectListBean>
}