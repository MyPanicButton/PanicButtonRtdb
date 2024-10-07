package com.example.panicbuttonrtdb.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.panicbuttonrtdb.viewmodel.ViewModel

@Composable
fun DashboardUserScreen(viewModel: ViewModel, onLogout: () -> Unit) {

    val buzzerState by viewModel.buzzerState.observeAsState(initial = "Off")
    var showDialog by remember { mutableStateOf(false) } // State untuk menampilkan dialog
    var pendingToggleState by remember { mutableStateOf(false) } // State untuk menyimpan toggle sementara
    var selectedPriority by remember { mutableStateOf("biasa") }

    var message by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Welcome to Dashboard", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Welcome, ${viewModel.currentUserName}!", style = MaterialTheme.typography.titleLarge)
        Text(text = "House Number: ${viewModel.currentUserHouseNumber}", style = MaterialTheme.typography.bodyLarge)

        Spacer(modifier = Modifier.height(16.dp))

        // Tampilkan toggle untuk mengontrol buzzer
        Switch(
            checked = buzzerState == "On",
            onCheckedChange = { isChecked ->
                if (isChecked) {
                    // Jika toggle diaktifkan, tampilkan dialog konfirmasi
                    pendingToggleState = true
                    showDialog = true
                } else {
                    // Jika toggle dimatikan, langsung matikan tanpa konfirmasi
                    viewModel.setBuzzerState("Off")
                }
            }
        )
        
        Button(onClick = onLogout) {
            Text(text = "Logout")
        }

        // Logic untuk otomatis mematikan toggle setelah 10 detik
        if (buzzerState == "On") {
            LaunchedEffect(key1 = buzzerState) {
                kotlinx.coroutines.delay(10000) // Tunggu selama 10 detik
                viewModel.setBuzzerState("Off")
            }
        }

        // Dialog konfirmasi
        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text(text = "Konfirmasi") },
                text = {
                    Column {
                        Text(text = "Apakah Anda yakin ingin mengaktifkan buzzer?")
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = message,
                            onValueChange = { message = it },
                            label = { Text("Tambahkan Pesan") },
                            placeholder = { Text("Pesan opsional...") }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        PrioritySelection(
                            selectedPriority = selectedPriority,
                            onPrioritySelected = { selectedPriority = it }
                        )
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            // Jika pengguna mengonfirmasi, aktifkan buzzer
                            viewModel.setBuzzerState("On")
                            viewModel.saveMonitorData(
                                message = message,
                                priority = selectedPriority
                            )
                            showDialog = false
                        }
                    ) {
                        Text("Ya")
                    }
                },
                dismissButton = {
                    Button(
                        onClick = {
                            // Jika pengguna membatalkan, sembunyikan dialog dan reset toggle
                            showDialog = false
                        }
                    ) {
                        Text("Tidak")
                    }
                }
            )
        }

    }
}
