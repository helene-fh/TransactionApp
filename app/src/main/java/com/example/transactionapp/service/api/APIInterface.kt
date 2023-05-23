package com.example.transactionapp.service.api

import com.example.transactionapp.models.AccountModel
import com.example.transactionapp.models.TransactionModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface APIInterface {

    @GET("accounts/{accountId}")
    suspend fun getAccount(@Path("accountId") accountId: String): Response<AccountModel>

    @GET("transactions/")
    suspend fun getAllTransactions(): Response<List<TransactionModel>>

    @Headers("Content-Type: application/json")
    @POST("transactions/")
    suspend fun createTransaction(@Body request: TransactionModel): Response<TransactionModel>
}