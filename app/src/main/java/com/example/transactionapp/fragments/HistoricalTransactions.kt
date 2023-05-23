package com.example.transactionapp.fragments

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import com.example.transactionapp.models.TransactionWithBalance
import com.example.transactionapp.core.Constants
import com.example.transactionapp.viewModels.ViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HistoricalTransactions(viewModel: ViewModel){

    LaunchedEffect(key1 = Unit, block = {
        viewModel.getAccount()
    }
    )
    Column(
        modifier = Modifier
            .padding(all = 25.dp)
    ){
        Text(
            text = "Historical Transactions",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .padding(vertical = 10.dp)
                .padding(start = 10.dp)
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxHeight()
        ){

            val transactionsWithBalance = viewModel.transactions.map { transaction ->

               val balance = if (transaction.accountId == Constants.ACCOUNT_ID) {
                   transaction.sum
                } else {
                   0
                }
                TransactionWithBalance(transaction = transaction, balance = balance)
            }

            items(transactionsWithBalance) { transactionWithBalance ->
                val transaction = transactionWithBalance.transaction
                val balance = transactionWithBalance.balance

                if (transaction.accountId != Constants.ACCOUNT_ID){
                    if (transaction.amount > 0) {
                        TextWithBorder(text = "Transferred $${transaction.amount} to account: ${transaction.accountId}")
                    } else {
                        TextWithBorder(text = "Transferred $${transaction.amount} from account: ${transaction.accountId}")
                    }
                } else {
                    if (transaction.amount > 0) {
                        TextWithBorder(text = "Transferred $${transaction.amount} to account: ${transaction.accountId}. The current account balance is $${balance}")
                    } else {
                        TextWithBorder(text = "Transferred $${transaction.amount} from account: ${transaction.accountId}. The current account balance is $${balance}")
                    }
                }
            }
        }
    }
}

@Composable
fun TextWithBorder(text: String){
    Text(
        text = text,
        modifier = Modifier
            .border(1.5.dp, MaterialTheme.colorScheme.primary, RectangleShape)
            .fillMaxWidth()
            .padding(all = 25.dp)
    )

    Spacer(modifier = Modifier.height(20.dp))
}