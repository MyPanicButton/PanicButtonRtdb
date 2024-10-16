package com.example.panicbuttonrtdb.prensentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.panicbuttonrtdb.R
import com.example.panicbuttonrtdb.viewmodel.ViewModel

@Composable
fun DashboardUserScreen(
    modifier: Modifier = Modifier,
    viewModel: ViewModel,
    onLogout: () -> Unit
) {
    val buzzerState by viewModel.buzzerState.observeAsState(initial = "Off")
    var showDialog by remember { mutableStateOf(false) } // State untuk menampilkan dialog
    var pendingToggleState by remember { mutableStateOf(false) } // State untuk menyimpan toggle sementara
    var selectedPriority by remember { mutableStateOf("biasa") }

    var message by remember { mutableStateOf("") }

    Box(
        modifier
            .background(color = colorResource(id = R.color.background))
            .fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier
                .background(color = colorResource(id = R.color.primary))
                .height(180.dp)
                .fillMaxWidth()
                .padding(top = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Panic Button",
                fontSize = 36.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )
        }
        Column(
            modifier
                .padding(bottom = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Card(
                modifier
                    .padding(top = 140.dp)
                    .height(114.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                elevation = CardDefaults.cardElevation(4.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    contentColor = Color.White,
                    containerColor = Color.White
                )
            ) {
                Row(
                    modifier
                        .padding(start = 22.dp, end = 22.dp)
                        .fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Absolute.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = "Selamat Datang",
                            fontSize = 14.sp,
                            color = colorResource(id = R.color.font2)
                        )
                        Text(
                            text = viewModel.currentUserName,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = colorResource(id = R.color.font)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = "Nomor rumah anda:",
                                fontSize = 14.sp,
                                color = colorResource(id = R.color.font2)
                            )
                            Text(
                                text = viewModel.currentUserHouseNumber,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = colorResource(id = R.color.font2)
                            )
                        }
                    }
                    IconButton(onClick = onLogout) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_logout),
                            contentDescription = "logout icon",
                            tint = colorResource(id = R.color.font)
                        )
                    }
                }
            }
            Column(
                modifier
                    .padding(horizontal = 24.dp)
                    .height(100.dp)
                    .fillMaxWidth()
                    .background(
                        color = colorResource(id = R.color.primary),
                        RoundedCornerShape(16.dp)
                    )
                    .fillMaxWidth()
                    .padding(start = 24.dp, end = 24.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Row(
                    modifier
                        .padding(top = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_warning),
                        contentDescription = "ic_warning",
                        tint = colorResource(id = R.color.background)
                    )
                    Text(
                        text = "Peringatan",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                }
                Text(
                    text = "Gunakan tombol hanya untuk keadaan darurat atau gangguan lainnya",
                    color = Color.White)
            }
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
                },
                thumbContent = {
                    if (buzzerState == "On") {
                        Icon(
                            painter = painterResource(id = R.drawable.onmode),
                            contentDescription = "on mode",
                            modifier = Modifier
                                .padding(5.dp),
                            tint = Color.White
                        )
                    }else {
                        Icon(
                            painter = painterResource(id = R.drawable.offmode),
                            contentDescription = "off mode",
                            modifier = Modifier
                                .padding(5.dp)
                                .size(24.dp),
                            tint = Color.White
                        )
                    }
                },
                colors = SwitchDefaults.colors(
                    checkedTrackColor = colorResource(id = R.color.pudar),
                    uncheckedTrackColor = colorResource(id = R.color.merah_pudar),
                    uncheckedBorderColor = colorResource(id = R.color.primary),
                    checkedThumbColor = colorResource(id = R.color.biru),
                    uncheckedThumbColor = colorResource(id = R.color.primary),
                    checkedBorderColor = colorResource(id = R.color.biru)
                ),
                modifier = Modifier
                    .scale(1.8f)
                    .padding(20.dp),
            )
            // Logic untuk otomatis mematikan toggle setelah 10 detik
            if (buzzerState == "On") {
                LaunchedEffect(key1 = buzzerState) {
                    kotlinx.coroutines.delay(20000) // Tunggu selama 10 detik
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
}
