package com.example.transactionapp.models

data class TransactionWithBalance(
    val transaction: TransactionModel,
    val balance : Int?
)


