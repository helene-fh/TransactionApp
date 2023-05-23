package com.example.transactionapp.models

import com.google.gson.annotations.SerializedName

data class AccountModel(
    @SerializedName("account_id")
    val accountId: String,
    @SerializedName("balance")
    val balance: Int
)
