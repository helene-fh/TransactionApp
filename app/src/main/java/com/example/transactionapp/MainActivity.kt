package com.example.transactionapp

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.transactionapp.fragments.HistoricalTransactions
import com.example.transactionapp.fragments.TransactionForm
import com.example.transactionapp.ui.theme.TransactionAppTheme
import com.example.transactionapp.viewModels.ViewModel

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = ViewModel()
        setContent {
            TransactionAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column{
                        TransactionForm(viewModel = viewModel)
                        HistoricalTransactions(viewModel = viewModel)
                    }
                }
            }
        }
    }
}
