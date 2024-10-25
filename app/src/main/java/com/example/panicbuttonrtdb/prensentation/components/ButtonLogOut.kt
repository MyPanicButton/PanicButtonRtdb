package com.example.panicbuttonrtdb.prensentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.panicbuttonrtdb.R

@Composable
fun LogOutUser(
    modifier: Modifier = Modifier,
    onClick: () ->Unit
) {
    var showKeluarDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Box(
        modifier
            .clickable { showKeluarDialog = true }
            .size(36.dp)
            .clip(CircleShape)
            .background(color = colorResource(id = R.color.primary)),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_logout),
            contentDescription = "icon logout",
            tint = Color.White
        )
    }
    if (showKeluarDialog) {
        AlertDialog(
            onDismissRequest = { showKeluarDialog},
            title = { Text( "Konfirmasi") },
            text = { Text("Apakah Anda yakin ingin keluar?") },
            confirmButton = {
                Button(
                    onClick = {
                        showKeluarDialog = false
                        onClick()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.font)
                    )
                ) { Text("Ya")

                }
            },
            dismissButton = {
                Button(
                    onClick = { showKeluarDialog = false },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.font)
                    )
                ) {
                    Text("Tidak")
                }
            }
        )
    }
}

@Composable
fun LogOutAdmin(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    var showKeluarDialog by remember { mutableStateOf(false) }

    Button(
        onClick = { showKeluarDialog = true},
        modifier
            .size(36.dp)
            .clip(CircleShape),
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(id = R.color.background_button),
            contentColor = Color.White

        ),
        contentPadding = PaddingValues(2.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_logout),
            contentDescription = "icon logout",
            tint = Color.White,
            modifier = Modifier.size(24.dp)
        )
    }
    if (showKeluarDialog) {
        AlertDialog(
            onDismissRequest = { showKeluarDialog},
            title = { Text( "Konfirmasi") },
            text = { Text("Apakah Anda yakin ingin keluar?") },
            confirmButton = {
                Button(
                    onClick = {
                        onClick()
                        showKeluarDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.font)
                    )
                ) { Text("Ya")

                }
            },
            dismissButton = {
                Button(
                    onClick = { showKeluarDialog = false },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.font)
                    )
                ) {
                    Text("Tidak")
                }
            }
        )
    }
}