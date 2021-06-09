package com.poseidon.blc.knowledge.usecase

import com.poseidon.blc.callback.ERROR_CODE_ILLEGAL_DATA
import com.poseidon.blc.callback.UseCaseRequestCallback
import com.poseidon.blc.callback.UseCaseRequestFailed
import com.poseidon.blc.knowledge.entities.ArticleListInKnowledgeBean
import com.poseidon.blc.knowledge.entities.KnowledgeListBean
import com.poseidon.blc.knowledge.service.KnowledgeService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class KnowledgeUseCase @Inject constructor(var knowledgeService: KnowledgeService) {
    fun getKnowledgeList(requestCallback: UseCaseRequestCallback<KnowledgeListBean>) {
        knowledgeService.getKnowledgeList().enqueue(object : Callback<KnowledgeListBean> {
            override fun onResponse(
                call: Call<KnowledgeListBean>,
                response: Response<KnowledgeListBean>
            ) {
                if (response.isSuccessful) {
                    var knowledgeListBean = response.body()
                    if (knowledgeListBean != null) {
                        requestCallback.onSuccess(knowledgeListBean)
                    } else {
                        requestCallback.onFailed(
                            call, UseCaseRequestFailed(
                                ERROR_CODE_ILLEGAL_DATA, "knowledgeListBean beans is null"
                            )
                        )
                    }
                } else {
                    requestCallback.onFailed(
                        call, UseCaseRequestFailed(
                            response.code(), "getKnowledgeList request failed"
                        )
                    )
                }
            }

            override fun onFailure(call: Call<KnowledgeListBean>, t: Throwable) {
                requestCallback.onError(call, t)
            }
        })
    }

    fun getArticleListByKnowledgeAndPageIndex(
        pageIndex: Int,
        cid: Int,
        requestCallback: UseCaseRequestCallback<ArticleListInKnowledgeBean>
    ) {
        knowledgeService.getArticleListByKnowledgeTypeAndPageIndex(pageIndex, cid)
            .enqueue(object : Callback<ArticleListInKnowledgeBean> {
                override fun onResponse(
                    call: Call<ArticleListInKnowledgeBean>,
                    response: Response<ArticleListInKnowledgeBean>
                ) {
                    if (response.isSuccessful) {
                        var articleListInKnowledgeBean = response.body()
                        if (articleListInKnowledgeBean != null) {
                            requestCallback.onSuccess(articleListInKnowledgeBean)
                        } else {
                            requestCallback.onFailed(
                                call, UseCaseRequestFailed(
                                    ERROR_CODE_ILLEGAL_DATA,
                                    "articleListInKnowledgeBean beans is null"
                                )
                            )
                        }
                    } else {
                        requestCallback.onFailed(
                            call, UseCaseRequestFailed(
                                response.code(),
                                "getArticleListByKnowledgeAndPageIndex request failed"
                            )
                        )
                    }
                }

                override fun onFailure(call: Call<ArticleListInKnowledgeBean>, t: Throwable) {
                    requestCallback.onError(call, t)
                }
            })
    }
}