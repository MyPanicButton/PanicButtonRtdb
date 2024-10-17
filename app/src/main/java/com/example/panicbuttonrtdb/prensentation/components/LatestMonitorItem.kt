package com.example.panicbuttonrtdb.prensentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.panicbuttonrtdb.viewmodel.ViewModel

@Composable
fun MonitorScreen(monitorViewModel: ViewModel, onClick: () -> Unit) {
    val latestRecord by monitorViewModel.latestRecord.collectAsState()

    val backgroundColor = when (latestRecord.priority) {
        "darurat" -> Color.Red
        "penting" -> Color.Yellow
        "biasa" -> Color.Green
        else -> Color.Gray
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFF0F0)) // Background warna merah muda
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick }
                .padding(16.dp)
                .background(Color.White, shape = RoundedCornerShape(12.dp))
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "NOMOR RUMAH",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color.Black
            )

            Text(
                text = latestRecord.houseNumber,
                fontWeight = FontWeight.Bold,
                fontSize = 48.sp,
                color = Color.Black
            )

            Text(
                text = latestRecord.time,
                fontSize = 14.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Mengganti Button menjadi Card atau Box dengan warna dinamis
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .background(backgroundColor, shape = RoundedCornerShape(8.dp)),
                shape = RoundedCornerShape(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(backgroundColor),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = latestRecord.priority,
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Column(horizontalAlignment = Alignment.Start) {
                Text(
                    text = "Pesan:",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = Color.Red
                )

                Text(
                    text = latestRecord.message,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
        }
    }
}


