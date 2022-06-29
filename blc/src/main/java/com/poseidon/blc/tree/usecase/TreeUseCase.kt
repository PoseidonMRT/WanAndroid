package com.poseidon.blc.tree.usecase

import android.util.Log
import com.poseidon.blc.callback.ERROR_CODE_ILLEGAL_DATA
import com.poseidon.blc.callback.UseCaseRequestCallback
import com.poseidon.blc.callback.UseCaseRequestFailed
import com.poseidon.blc.tree.entities.TreeListBean
import com.poseidon.blc.tree.service.TreeDataService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class TreeUseCase @Inject constructor(var treeDataService: TreeDataService) {
    val tag = "TreeUseCase"
    fun getTreeList(requestCallback: UseCaseRequestCallback<TreeListBean>) {
        treeDataService.getTreeList().enqueue(object : Callback<TreeListBean> {
            override fun onResponse(call: Call<TreeListBean>, response: Response<TreeListBean>) {
                if (response.isSuccessful) {
                    var treeListBean = response.body()
                    if (treeListBean == null) {
                        requestCallback.onFailed(
                            call, UseCaseRequestFailed(
                                ERROR_CODE_ILLEGAL_DATA, "getTreeList beans is null"
                            )
                        )
                        return
                    }

                    Log.d(tag, treeListBean.toString())
                    if (treeListBean.errorCode != 0) {
                        requestCallback.onFailed(
                            call, UseCaseRequestFailed(
                                treeListBean.errorCode, treeListBean.errorMsg
                            )
                        )
                        return
                    }

                    requestCallback.onSuccess(treeListBean)
                } else {
                    requestCallback.onFailed(
                        call, UseCaseRequestFailed(
                            response.code(), "getRecommendArticleList request failed"
                        )
                    )
                }
            }

            override fun onFailure(call: Call<TreeListBean>, t: Throwable) {
                requestCallback.onError(call, t)
            }
        })
    }
}