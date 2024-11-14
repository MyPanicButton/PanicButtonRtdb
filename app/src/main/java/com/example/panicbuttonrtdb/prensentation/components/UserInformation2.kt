package com.example.panicbuttonrtdb.prensentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.panicbuttonrtdb.R
import com.example.panicbuttonrtdb.viewmodel.ViewModel

@Composable
fun UserInformation2(
    modifier: Modifier = Modifier,
    viewModel: ViewModel
) {
    var phoneNumber by remember {mutableStateOf("")}
    var note by remember {mutableStateOf("")}

    Column(
        modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(Color.White)
    ) {
        Column(
            modifier
                .wrapContentHeight()
                .padding(8.dp),
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    modifier = Modifier
                        .height(50.dp)
                        .fillMaxWidth(),
                    value = phoneNumber,
                    onValueChange = { phoneNumber = it},
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_phonenumber),
                            contentDescription = "ic_phonenumber",
                            tint = colorResource(id = R.color.font2)
                        )
                    },
                    placeholder = {
                        Text(
                            text = "Masukan No Hp Anda",
                            fontSize = 12.sp
                        )
                    },
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        cursorColor = colorResource(id = R.color.font),
                        unfocusedIndicatorColor = Color.White,
                        focusedIndicatorColor = Color.White,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                    ),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done
                    ),
                )
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    modifier = Modifier
                        .wrapContentHeight()
                        .fillMaxWidth(),
                    value = note,
                    onValueChange = {note = it},
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_note),
                            contentDescription = "ic_note",
                            tint = colorResource(id = R.color.font2)
                        )
                    },
                    placeholder = {
                        Text(
                            text = "Tambahkan keterangan tentang rumah anda",
                            fontSize = 12.sp
                        )
                    },
                    colors = TextFieldDefaults.colors(
                        cursorColor = colorResource(id = R.color.font),
                        unfocusedIndicatorColor = Color.White,
                        focusedIndicatorColor = Color.White,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                    ),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done
                    ),
                )
            }
            Box(
                modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.BottomEnd
            ) {
                Button(
                    onClick = {
                        viewModel.savePhoneNumberAndNote(phoneNumber, note)
                    },
                    contentPadding = PaddingValues(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.font)
                    )
                ) {
                    Text(
                        text = "Selesai",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.White,
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                    )
                }
            }
        }
    }
}

