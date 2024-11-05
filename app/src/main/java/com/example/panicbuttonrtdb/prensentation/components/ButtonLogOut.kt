package com.example.panicbuttonrtdb.prensentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.panicbuttonrtdb.R

@Composable
fun LogOutUser(
    onClick: () ->Unit
) {
    var showKeluarDialog by remember { mutableStateOf(false) }

    Button(
        onClick = { showKeluarDialog = true },
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(id = R.color.primary)
        ),
        shape = CircleShape,
        contentPadding = PaddingValues(4.dp),
        modifier = Modifier.size(36.dp)
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
            containerColor = Color.White,
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
    onClick: () -> Unit
) {
    var showKeluarDialog by remember { mutableStateOf(false) }

    Button(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                shape = RoundedCornerShape(100.dp),
                color = colorResource(id = R.color.font2),
                width = 1.dp
            ),
        onClick = { showKeluarDialog = true },
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_logout),
                contentDescription = "ic_logout",
                tint = colorResource(id = R.color.primary),
                modifier = Modifier.size(24.dp)
            )
            Text(
                text = "Logout",
                fontSize = 12.sp,
                color = colorResource(id = R.color.font2)
            )
        }
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