package com.poseidon.blc.tree.service

import com.poseidon.blc.tree.entities.TreeListBean
import retrofit2.Call
import retrofit2.http.GET

interface TreeDataService {
    @GET("tree/json")
    fun getTreeList(): Call<TreeListBean>
}