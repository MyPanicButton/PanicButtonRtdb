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
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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

        // Dialog konfirmasi
        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text(text = "Konfirmasi") },
                text = { Text("Apakah Anda yakin ingin mengaktifkan buzzer?") },
                confirmButton = {
                    Button(
                        onClick = {
                            // Jika pengguna mengonfirmasi, aktifkan buzzer
                            viewModel.setBuzzerState("On")
                            viewModel.saveMonitorData()
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
