package com.example.panicbuttonrtdb.prensentation.components

import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.panicbuttonrtdb.R
import com.example.panicbuttonrtdb.viewmodel.ViewModel

@Composable
fun UserInformation(
    modifier: Modifier = Modifier,
    viewModel: ViewModel,
    context: Context
) {

    val sharedPref = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
    val houseNumber = sharedPref.getString("house_number", "")
    val defaultText = stringResource(id = R.string.userInformation)
    val textScroll = rememberScrollState()
    val userInformation by viewModel.userInformation.observeAsState(defaultText)
    var userText by remember { mutableStateOf(defaultText) }
    var isEditing by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }
    var editedText by remember { mutableStateOf(userText) }
    var isTextChanges by remember { mutableStateOf(false) }

    Box(
        modifier
            .height(130.dp)
            .fillMaxWidth()
            .background(Color.White)
    ) {
        Text(
            modifier = Modifier
                .fillMaxHeight()
                .padding(24.dp),
            text = userInformation,
            fontSize = 12.sp,
            style = TextStyle(lineHeight = 16.sp)
        )
        Column(
            modifier
                .height(80.dp)
                .fillMaxWidth()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.White
                        )
                    )
                )
                .align(Alignment.BottomCenter)
                .padding(start = 24.dp, end = 24.dp, top = 24.dp, bottom = 12.dp),
            verticalArrangement = Arrangement.Bottom
        ) {
            Row(
                modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(
                    modifier = Modifier
                        .wrapContentSize(),
                    onClick = {
                        showDialog = true
                        isEditing = false
                        editedText = userText
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.background_button)
                    )
                ) {
                    Text(
                        text = "Tambahkan",
                        color = colorResource(id = R.color.font2)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        painter = painterResource(id = R.drawable.ic_arrow_right),
                        contentDescription = "ic_arrow_right",
                        tint = colorResource(id = R.color.font2)
                    )
                }
            }
        }
        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text(text = "Edit Keterangan")},
                text = {
                    OutlinedTextField(
                        value = editedText,
                        onValueChange = { newText ->
                            editedText = newText
                            isTextChanges = newText != userText
                        },
                        label = { Text("Keterangan")},
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                confirmButton = {
                    if (isTextChanges) {
                        Button(
                            onClick = {
                                userText = editedText
                                isTextChanges = false
                                isEditing = false
                                showDialog = false
                                if (houseNumber != null) {
                                    Log.d("UpdateKeterangan", "Nomor Rumah: $houseNumber, Keterangan: $userText")
//                                    viewModel.addUserInformation(houseNumber, userText)
                                }
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colorResource(id = R.color.primary)
                            )
                        ) {
                            Text("Selesai",
                                color = Color.White
                            )
                        }
                    } else {
                        Button(
                            onClick = {
                                isEditing = true
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colorResource(id = R.color.primary)
                            )
                        ) {
                            Text("Edit")
                        }
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            showDialog = false
                            isEditing = false
                            isTextChanges = false
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(id = R.color.backgroundButtonBack)
                        )
                    ) {
                        Text("Batal",
                            color = colorResource(id = R.color.backgroundTextBack))
                    }
                }
            )
        }
    }
}