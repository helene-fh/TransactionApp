package com.example.transactionapp.fragments

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import com.example.transactionapp.models.TransactionModel
import com.example.transactionapp.ui.theme.TransactionAppTheme
import com.example.transactionapp.viewModels.ViewModel
import kotlinx.coroutines.launch

@Composable
fun TransactionForm(viewModel: ViewModel) {

    var accountIdText by remember { mutableStateOf(TextFieldValue(text = "")) }
    var amountText by remember { mutableStateOf(TextFieldValue(text = "")) }

    Box(
        modifier = Modifier
            .padding(start = 25.dp, end = 25.dp, top = 25.dp)
            .border(1.5.dp, MaterialTheme.colorScheme.primary, RectangleShape)
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
        )
        {
            TransactionFormTextField(
                onlyNumbers = false,
                label = "Account ID",
                text = accountIdText
            ) { accountIdText = it }
            TransactionFormTextField(
                onlyNumbers = true,
                label = "Amount",
                text = amountText,
                onTextChange = { inputText ->
                    val filteredText = inputText.text.filter { it.isDigit() || it == '-' }
                    amountText = inputText.copy(
                        text = filteredText
                    )
                })
            Spacer(modifier = Modifier.height(10.dp))
            ButtonWithRoundCornerShape(
                viewModel = viewModel,
                accountId = accountIdText.text,
                amount = amountText.text.toIntOrNull() ?: 0,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionFormTextField(
    onlyNumbers: Boolean,
    label: String,
    text: TextFieldValue,
    onTextChange: (TextFieldValue) -> Unit
) {

    val keyboardOptions = if (onlyNumbers) {
        KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
    } else {
        KeyboardOptions.Default
    }

    OutlinedTextField(
        value = text,
        label = { Text(text = label) },
        onValueChange = { newValue -> onTextChange(newValue) },
        placeholder = { Icon(imageVector = Icons.Default.Edit, contentDescription = "edit") },
        keyboardOptions = keyboardOptions,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp, top = 10.dp)
    )
}

@Composable
fun ButtonWithRoundCornerShape(
    viewModel: ViewModel,
    accountId: String,
    amount: Int,
) {
    Button(
        onClick = {
            viewModel.viewModelScope.launch {
                    viewModel.createTransaction(request = TransactionModel(
                    accountId = accountId,
                    amount = amount,
                    sum = null))
            }
        },
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp, bottom = 10.dp),
        enabled = true
    ) {
        Text(text = "Submit")
    }
}

@Preview(showBackground = true)
@Composable
fun TransactionFormPreview() {
    val transactionVm = ViewModel()
    TransactionAppTheme() {
        TransactionForm(transactionVm)
    }
}
