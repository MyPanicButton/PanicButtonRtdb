package com.example.panicbuttonrtdb.prensentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.panicbuttonrtdb.R
import com.example.panicbuttonrtdb.viewmodel.ViewModel
import com.example.panicbuttonrtdb.viewmodel.ViewModelFactory

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: ViewModel = viewModel(factory = ViewModelFactory(LocalContext.current))) {
    var houseNumber by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }  // Indikator loading
    var errorMessage by remember { mutableStateOf("") }  // Pesan error

    Box(
        modifier
            .background(color = colorResource(id = R.color.primary))
            .fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Box(
            modifier
                .height(600.dp)
                .background(
                    color = Color.White, RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                )
                .fillMaxWidth()
                .padding(start = 24.dp, end = 24.dp)
        ){
            Column(
                modifier
                    .padding(top = 40.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Login",
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(id = R.color.font)
                )
                Spacer(modifier = Modifier.height(44.dp))
                OutlinedTextField(
                    value = houseNumber,
                    onValueChange = {houseNumber = it},
                    label = { Text(text = "Nomor Rumah") },
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    ),
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_home),
                            contentDescription = "ic home"
                        )
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = colorResource(id = R.color.font),
                        focusedLabelColor = colorResource(id = R.color.font),
                        focusedLeadingIconColor = colorResource(id = R.color.font),
                        unfocusedLeadingIconColor = colorResource(id = R.color.defauld),
                        cursorColor = colorResource(id = R.color.font)
                    )
                )
                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = {password = it},
                    label = { Text(text = "Password") },
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    ),
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_password),
                            contentDescription = "ic password"
                        )
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = colorResource(id = R.color.font),
                        focusedLabelColor = colorResource(id = R.color.font),
                        focusedLeadingIconColor = colorResource(id = R.color.font),
                        unfocusedLeadingIconColor = colorResource(id = R.color.defauld),
                        cursorColor = colorResource(id = R.color.font)
                    )
                )

                Spacer(modifier = Modifier.height(12.dp))

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
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                    ,
                    enabled = !isLoading, // Matikan tombol saat loading
                    shape = RoundedCornerShape(26.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.font),
                        contentColor = Color.White
                    )
                ) {
                    Text(text = "Login")
                }

                Spacer(modifier = Modifier.height(36.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "Belum memiliki akun?"
                    )
                    Text(
                        modifier = Modifier
                            .clickable { navController.navigate("signup") },
                        text = "Daftar",
                        fontWeight = FontWeight.Bold,
                        color = colorResource(id = R.color.font)
                    )
                }
            }
        }

    }
}
