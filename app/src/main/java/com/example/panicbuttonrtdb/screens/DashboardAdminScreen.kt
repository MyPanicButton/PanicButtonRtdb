package com.example.panicbuttonrtdb.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.panicbuttonrtdb.viewmodel.MonitorRecord
import com.example.panicbuttonrtdb.viewmodel.ViewModel

@Composable
fun DashboardAdminScreen(viewModel: ViewModel) {

    val monitorData by viewModel.monitorData.observeAsState(initial = emptyList())

    // Panggil fungsi untuk fetch data ketika screen dibuka
    LaunchedEffect(Unit) {
        viewModel.fetchMonitorData()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Admin Dashboard", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Monitor Data:", style = MaterialTheme.typography.titleLarge)

        // Tampilkan data dalam bentuk daftar
        LazyColumn {
            items(monitorData) { record ->
                MonitorItem(record)
            }
        }
    }
}

@Composable
fun MonitorItem(record: MonitorRecord) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .border(1.dp, Color.Gray)
            .padding(8.dp)
    ) {
        Text(text = "Name: ${record.name}")
        Text(text = "House Number: ${record.houseNumber}")
        Text(text = "Timestamp: ${record.time}")
    }
}