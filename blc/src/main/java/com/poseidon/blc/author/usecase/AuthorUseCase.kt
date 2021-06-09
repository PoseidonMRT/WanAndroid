package com.poseidon.blc.author.usecase

import com.poseidon.blc.author.entities.ArticleListByAuthorBean
import com.poseidon.blc.author.service.AuthorService
import com.poseidon.blc.callback.ERROR_CODE_ILLEGAL_DATA
import com.poseidon.blc.callback.UseCaseRequestCallback
import com.poseidon.blc.callback.UseCaseRequestFailed
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class AuthorUseCase @Inject constructor(var authorService: AuthorService) {
    fun getArticleListByAuthorAndPageIndex(
        pageIndex: Int,
        authorName: String,
        requestCallback: UseCaseRequestCallback<ArticleListByAuthorBean>
    ) {
        authorService.getArticleListByAuthorAndPageIndex(pageIndex, authorName)
            .enqueue(object : Callback<ArticleListByAuthorBean> {
                override fun onResponse(
                    call: Call<ArticleListByAuthorBean>,
                    response: Response<ArticleListByAuthorBean>
                ) {
                    if (response.isSuccessful) {
                        var articleListByAuthorBean = response.body()
                        if (articleListByAuthorBean != null) {
                            requestCallback.onSuccess(articleListByAuthorBean)
                        } else {
                            requestCallback.onFailed(
                                call, UseCaseRequestFailed(
                                    ERROR_CODE_ILLEGAL_DATA, "articleListByAuthorBean beans is null"
                                )
                            )
                        }
                    } else {
                        requestCallback.onFailed(
                            call, UseCaseRequestFailed(
                                response.code(), "getArticleListByAuthorAndPageIndex request failed"
                            )
                        )
                    }
                }

                override fun onFailure(call: Call<ArticleListByAuthorBean>, t: Throwable) {
                    requestCallback.onError(call, t)
                }
            })
    }
}