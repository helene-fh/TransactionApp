package com.example.transactionapp.models

import com.google.gson.annotations.SerializedName

data class TransactionModel(
    @SerializedName("account_id")
    var accountId: String,
    @SerializedName("amount")
    var amount: Int,
    @SerializedName("transaction_id")
    var transactionId : String? = null,
    var sum : Int?
)
