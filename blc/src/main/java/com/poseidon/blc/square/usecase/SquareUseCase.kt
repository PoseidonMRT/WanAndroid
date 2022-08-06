package com.poseidon.blc.square.usecase

import android.util.Log
import com.poseidon.blc.callback.ERROR_CODE_ILLEGAL_DATA
import com.poseidon.blc.callback.UseCaseRequestCallback
import com.poseidon.blc.callback.UseCaseRequestFailed
import com.poseidon.blc.square.entities.SquareResultBean
import com.poseidon.blc.square.entities.SquareResultData
import com.poseidon.blc.square.service.SquareDataService
import com.poseidon.blc.tree.entities.TreeListBean
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class SquareUseCase @Inject constructor(var squareDataService: SquareDataService) {
    val tag = "SquareUseCase"
    fun getSquareDataInfo(
        curIndex: Int,
        requestCallback: UseCaseRequestCallback<SquareResultBean>
    ) {
        squareDataService.getSquareData(curIndex).enqueue(object : Callback<SquareResultBean> {
            override fun onResponse(
                call: Call<SquareResultBean>,
                response: Response<SquareResultBean>
            ) {
                if (response.isSuccessful) {
                    var squareResultBean = response.body()
                    if (squareResultBean == null) {
                        requestCallback.onFailed(
                            call, UseCaseRequestFailed(
                                ERROR_CODE_ILLEGAL_DATA, "getSquareDataInfo beans is null"
                            )
                        )
                        return
                    }

                    Log.d(tag, squareResultBean.toString())
                    if (squareResultBean.errorCode != 0) {
                        requestCallback.onFailed(
                            call, UseCaseRequestFailed(
                                squareResultBean.errorCode, squareResultBean.errorMsg
                            )
                        )
                        return
                    }

                    requestCallback.onSuccess(squareResultBean)
                } else {
                    requestCallback.onFailed(
                        call, UseCaseRequestFailed(
                            response.code(), "getSquareDataInfo request failed"
                        )
                    )
                }
            }

            override fun onFailure(call: Call<SquareResultBean>, t: Throwable) {

                requestCallback.onError(call, t)
            }

        })
    }
}