package com.example.panicbuttonrtdb.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.panicbuttonrtdb.viewmodel.ViewModel
import com.example.panicbuttonrtdb.viewmodel.ViewModelFactory

@Composable
fun SignUpScreen(
    navController: NavHostController,
    viewModel: ViewModel = viewModel(factory = ViewModelFactory(LocalContext.current))) {
    var name by remember { mutableStateOf("") }
    var houseNumber by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") } // Untuk pesan error

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Sign Up", style = MaterialTheme.typography.titleLarge)

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nama") },
            modifier = Modifier.fillMaxWidth(),
            isError = errorMessage.isNotEmpty() && name.isEmpty()
        )

        OutlinedTextField(
            value = houseNumber,
            onValueChange = { houseNumber = it },
            label = { Text("Nomor Rumah") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            isError = errorMessage.isNotEmpty() && houseNumber.isEmpty()
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Sandi") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            isError = errorMessage.isNotEmpty() && password.isEmpty()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Tampilkan pesan error jika ada
        if (errorMessage.isNotEmpty()) {
            Text(text = errorMessage, color = MaterialTheme.colorScheme.error)
        }

        // Indikator loading
        if (isLoading) {
            CircularProgressIndicator()
        }

        Button(
            onClick = {
                if (name.isNotEmpty() && houseNumber.isNotEmpty() && password.isNotEmpty()) {
                    isLoading = true
                    errorMessage = ""  // Reset pesan error
                    viewModel.saveUserToFirebase(
                        name = name,
                        houseNumber = houseNumber,
                        password = password,
                        onSuccess = {
                            isLoading = false
                            navController.navigate("login")  // Navigasi ke login jika berhasil
                        },
                        onFailure = { error ->
                            isLoading = false
                            errorMessage = error  // Tampilkan pesan error
                        }
                    )
                } else {
                    errorMessage = "Semua kolom harus diisi."
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isLoading // Matikan tombol saat loading
        ) {
            Text(text = "Sign Up")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Sudah memiliki akun? ")
            TextButton(onClick = { navController.navigate("login") }) {
                Text(text = "Login")
            }
        }
    }
}

