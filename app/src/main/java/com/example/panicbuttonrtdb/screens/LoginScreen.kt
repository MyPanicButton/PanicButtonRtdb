package com.example.panicbuttonrtdb.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.panicbuttonrtdb.viewmodel.ViewModel
import com.example.panicbuttonrtdb.viewmodel.ViewModelFactory

@Composable
fun LoginScreen(
    navController: NavHostController,
    viewModel: ViewModel = viewModel(factory = ViewModelFactory(LocalContext.current))) {
    var houseNumber by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }  // Indikator loading
    var errorMessage by remember { mutableStateOf("") }  // Pesan error

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Login", style = MaterialTheme.typography.titleLarge)

        // Input untuk nomor rumah
        OutlinedTextField(
            value = houseNumber,
            onValueChange = { houseNumber = it },
            label = { Text("Nomor Rumah") },
            modifier = Modifier.fillMaxWidth(),
            isError = houseNumber.isEmpty() && errorMessage.isNotEmpty()
        )

        // Input untuk sandi
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Sandi") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            isError = password.isEmpty() && errorMessage.isNotEmpty()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Pesan kesalahan jika login gagal
        if (errorMessage.isNotEmpty()) {
            Text(text = errorMessage, color = MaterialTheme.colorScheme.error)
        }

        // Indikator loading jika sedang memproses login
        if (isLoading) {
            CircularProgressIndicator()
        }

        Button(
            onClick = {
                if (houseNumber.isNotEmpty() && password.isNotEmpty()) {
                    isLoading = true
                    errorMessage = ""

                    viewModel.validateLogin(houseNumber, password) { success, isAdmin ->
                        isLoading = false
                        if (success) {
                            if (isAdmin) {
                                // Navigasi ke Dashboard Admin jika login sebagai admin
                                navController.navigate("dashboard_admin")
                            } else {
                                // Navigasi ke Dashboard User jika login sebagai user biasa
                                navController.navigate("dashboard")
                            }
                        } else {
                            errorMessage = "Login gagal. Periksa nomor rumah dan sandi."
                        }
                    }
                } else {
                    errorMessage = "Nomor rumah dan sandi harus diisi."
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isLoading // Matikan tombol saat loading
        ) {
            Text(text = "Login")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Belum memiliki akun? ")
            TextButton(
                onClick = {navController.navigate("signup")},
            ) {
                Text(text = "Signup")
            }
        }
    }
}
