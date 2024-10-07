package com.example.panicbuttonrtdb.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.navigation.NavController
import com.example.panicbuttonrtdb.viewmodel.MonitorRecord
import com.example.panicbuttonrtdb.viewmodel.ViewModel

@Composable
fun DashboardAdminScreen(navController: NavController, viewModel: ViewModel, onLogout: () -> Unit) {

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

        Button(onClick = onLogout) {
            Text(text = "Logout")
        }

        Text(text = "Monitor Data:", style = MaterialTheme.typography.titleLarge)

        // Tampilkan data dalam bentuk daftar
        LazyColumn {
            items(monitorData) { record ->
                MonitorItem(record, onClick = { navController.navigate("history/${record.houseNumber}") })
            }
        }
    }
}

@Composable
fun MonitorItem(record: MonitorRecord, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() }
            .border(1.dp, Color.Gray)
            .padding(8.dp)
    ) {
        Text(text = "Name: ${record.name}")
        Text(text = "House Number: ${record.houseNumber}")
        Text(text = "Message: ${record.message}")
        Text(text = "Priority: ${record.priority}")
        Text(text = "Timestamp: ${record.time}")

    }
}

@Composable
fun PrioritySelection(
    selectedPriority: String,
    onPrioritySelected: (String) -> Unit
) {
    Column {
        Text(text = "Pilih Prioritas", style = MaterialTheme.typography.bodyMedium)

        Spacer(modifier = Modifier.height(8.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            // Button untuk prioritas "Darurat"
            Button(
                onClick = { onPrioritySelected("darurat") },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (selectedPriority == "darurat") Color.Red else Color.Transparent,
                    contentColor = if (selectedPriority == "darurat") Color.White else Color.Gray
                ),
                border = BorderStroke(1.dp, Color.Red),
                modifier = Modifier.weight(1f)
            ) {
                Text("Darurat")
            }

            // Button untuk prioritas "Penting"
            Button(
                onClick = { onPrioritySelected("penting") },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (selectedPriority == "penting") Color.Yellow else Color.Transparent,
                    contentColor = if (selectedPriority == "penting") Color.White else Color.Gray
                ),
                border = BorderStroke(1.dp, Color.Yellow),
                modifier = Modifier.weight(1f)
            ) {
                Text("Penting")
            }

            // Button untuk prioritas "Biasa"
            Button(
                onClick = { onPrioritySelected("biasa") },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (selectedPriority == "biasa") Color.Green else Color.Transparent,
                    contentColor = if (selectedPriority == "biasa") Color.White else Color.Gray
                ),
                border = BorderStroke(1.dp, Color.Green),
                modifier = Modifier.weight(1f)
            ) {
                Text("Biasa")
            }
        }
    }
}
