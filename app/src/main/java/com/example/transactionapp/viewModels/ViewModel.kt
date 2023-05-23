package com.example.transactionapp.viewModels

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.transactionapp.core.Constants
import com.example.transactionapp.models.AccountModel
import com.example.transactionapp.models.TransactionModel
import com.example.transactionapp.service.RetrofitClient
import com.example.transactionapp.service.api.APIInterface
import kotlinx.coroutines.launch
import java.lang.Exception

class ViewModel : ViewModel() {

    private val _transactions = mutableStateListOf<TransactionModel>()
    private val _account = mutableStateOf<AccountModel?>(null)
    private val _newBalance = mutableStateOf<Int?>(null)

    val transactions: List<TransactionModel>
        get() = _transactions

    private val retrofit = RetrofitClient.getInstance()
    private val apiInterface = retrofit.create(APIInterface::class.java)

    private fun getTransactionsList(){
        viewModelScope.launch {
            try{
                val response = apiInterface.getAllTransactions()
                if(response.isSuccessful) {
                    val responseData = response.body()
                    Log.i("Success", responseData.toString())
                    if(responseData != null){
                        _transactions.clear()
                        for (transaction in responseData) {
                            if (transaction.accountId == Constants.ACCOUNT_ID){
                                _newBalance.value = _newBalance.value?.plus(transaction.amount)
                                transaction.sum = _newBalance.value
                            }
                            _transactions.add(index = 0, element = transaction)
                        }
                    }
                }
            } catch (e: Exception){
                e.localizedMessage?.let { Log.e("Error", it) }
            }
        }
    }

    fun createTransaction(request: TransactionModel){
        viewModelScope.launch {
            try {
                val response = apiInterface.createTransaction(request = TransactionModel(accountId = request.accountId, amount = request.amount, sum = request.sum))
                if(response.isSuccessful){
                    val responseData = response.body()
                    Log.i("Success", responseData.toString())
                    if(responseData != null){
                            if (responseData.accountId == Constants.ACCOUNT_ID){
                                _newBalance.value = _newBalance.value?.plus(responseData.amount)
                                responseData.sum = _newBalance.value
                            }
                            _transactions.add(index = 0, element = responseData)
                    }
                }
            } catch (e: Exception){
                e.localizedMessage?.let { Log.e("Error", it) }
            }
        }
    }

    fun getAccount(){
        viewModelScope.launch {
            try{
                val response = apiInterface.getAccount(accountId = Constants.ACCOUNT_ID)
                if(response.isSuccessful) {
                    val responseData = response.body()
                    Log.i("Success", responseData.toString())
                    if(responseData != null){
                        _account.value = responseData
                        _newBalance.value = responseData.balance
                        getTransactionsList()
                    }
                }
            } catch (e: Exception){
                e.localizedMessage?.let { Log.e("Error", it) }
            }
        }
    }
}