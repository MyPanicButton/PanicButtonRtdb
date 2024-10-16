package com.example.panicbuttonrtdb.prensentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.panicbuttonrtdb.viewmodel.ViewModel

@Composable
fun HistoryScreen(navController: NavController, viewModel: ViewModel, houseNumber: String) {
    val historyList by viewModel.getHistoryByHouseNumber(houseNumber).observeAsState(emptyList())

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        Text(text = "History for House Number: $houseNumber", style = MaterialTheme.typography.titleLarge)

        LazyColumn {
            items(historyList) { historyItem ->
                HistoryItem(
                    name = historyItem.name,
                    message = historyItem.message,
                    time = historyItem.time,
                    priority = historyItem.priority
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navController.popBackStack() }) {
            Text(text = "Back to Dashboard")
        }
    }
}

// Item untuk setiap entri di halaman History
@Composable
fun HistoryItem(name: String, message: String, time: String, priority: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Nama: $name", style = MaterialTheme.typography.bodyLarge)
            Text(text = "Pesan: $message", style = MaterialTheme.typography.bodyMedium)
//            Text(text = "Waktu: $time", stylea = MaterialTheme.typography.bodySmall)
            Text(text = "Waktu: $time")
            Text(text = "Prioritas: $priority", style = MaterialTheme.typography.bodySmall)
        }
    }
}
