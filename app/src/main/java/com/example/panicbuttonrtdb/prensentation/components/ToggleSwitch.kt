package com.example.panicbuttonrtdb.prensentation.components

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.panicbuttonrtdb.R
import com.example.panicbuttonrtdb.notification.sendNotification
import com.example.panicbuttonrtdb.viewmodel.ViewModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToggleSwitch(
    viewModel: ViewModel,
    context: Context
) {


    var showDialog by remember { mutableStateOf(false) } // State untuk menampilkan dialog
    var pendingToggleState by remember { mutableStateOf(false) } // State untuk menyimpan toggle sementara
    var selectedPriority by remember { mutableStateOf("Darurat") }
    val buzzerState by viewModel.buzzerState.observeAsState(initial = "Off")
    var message by remember { mutableStateOf("") }
    var showError by remember {mutableStateOf(false)}


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Switch(
            checked = buzzerState == "on",
            onCheckedChange = { isChecked ->
                if (isChecked) {
                    pendingToggleState = true
                    showDialog = true
                } else {
                    viewModel.updateBuzzerState(false)
                    viewModel.setBuzzerState("off")
                }
            },
            thumbContent = {
                if (buzzerState == "on") {
                    Icon(
                        painter = painterResource(id = R.drawable.onmode),
                        contentDescription = "on mode",
                        modifier = Modifier
                            .padding(5.dp),
                        tint = Color.White
                    )
                } else {
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
                .padding(20.dp)
        )
    }
    if (buzzerState == "on") {
        LaunchedEffect(key1 = buzzerState) {
            delay(20000)
            viewModel.setBuzzerState("off")
            viewModel.updateBuzzerState(isOn = false)
        }
    }
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_warning),
                        contentDescription = "ic warning",
                        tint = colorResource(id = R.color.darurat)
                    )
                    Text(
                        text = "Konfirmasi",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = colorResource(id = R.color.primary)
                    )
                }
            },
            text = {
                Column {
                    Text(
                        "Tambahkan Pesan dan Prioritas",
                        color = colorResource(id = R.color.font2)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    OutlinedTextField(
                        value = message,
                        onValueChange = { message = it },
                        label = { Text(text = "Tambahkan Pesan", color = colorResource(id = R.color.font2)) },
                        placeholder = { Text( "Opsional", color = colorResource(id = R.color.font3)) },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = colorResource(id = R.color.font),
                            focusedLabelColor = colorResource(id = R.color.font),
                            cursorColor = colorResource(id = R.color.font)
                        )
                    )
                    PriorityButton(
                        onPrioritySelected = { priority ->
                            selectedPriority = priority
                        }
                    )
                    if (showError) {
                        Text(
                            text = "Silahkan pilih tombol skala prioritas",
                            color = colorResource(id = R.color.darurat),
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                }
            },
            containerColor = Color.White,
            confirmButton = {
                Button(
                    modifier = Modifier
                        .width(130.dp),
                    onClick = {
                        if (selectedPriority.isEmpty()) {
                            showError = true
                        } else {
                            showError = false
                            viewModel.setBuzzerState("on")
                            viewModel.updateBuzzerState(
                                isOn = true,
                                priority = selectedPriority
                            )
                            viewModel.saveMonitorData(
                                message = message,
                                priority = selectedPriority,
                                status = "Proses"
                            )
                            sendNotification(
                                context,
                                "Panic Button",
                                "Buzzer telah diaktifkan dengan skala prioritas $selectedPriority"
                            )
                            showDialog = false
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.primary)
                    )
                ) {
                    Text(
                        "Kirim",
                        color = Color.White
                    )
                }
            },
            dismissButton = {
                Button(
                    modifier = Modifier
                        .width(130.dp),
                    onClick = { showDialog = false },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.background_button)
                    )
                ) {
                    Text(
                        "Batal",
                        color = colorResource(id = R.color.font3)
                    )
                }
            }
        )
    }
}