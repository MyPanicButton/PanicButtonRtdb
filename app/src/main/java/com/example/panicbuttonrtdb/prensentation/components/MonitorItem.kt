package com.example.panicbuttonrtdb.prensentation.components

import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.panicbuttonrtdb.R
import com.example.panicbuttonrtdb.data.MonitorRecord
import com.example.panicbuttonrtdb.notification.sendNotification
import com.example.panicbuttonrtdb.viewmodel.ViewModel

@Composable
fun MonitorItem(
    modifier: Modifier = Modifier,
    viewModel: ViewModel,
    navController: NavController,
    context: Context
) {
    val latestRecord by viewModel.latestRecord.collectAsState()
    val recordData by viewModel.monitorData.observeAsState(emptyList())
    var lastData : List<MonitorRecord> by remember { mutableStateOf(emptyList()) }

    val backgroundColor = when (latestRecord.priority) {
        "Darurat" -> colorResource(id = R.color.darurat)
        "Penting" -> colorResource(id = R.color.penting)
        "Biasa" -> colorResource(id = R.color.biasa)
        else -> Color.Gray
    }
//    LaunchedEffect(Unit) {
//        viewModel.fetchLatestRecord()
//    }

    LaunchedEffect(Unit) {
        viewModel.fetchLatestRecord()
        if (recordData.isNotEmpty() && recordData != lastData) {
            recordData.forEachIndexed { index, newItem ->
                val oldItem = lastData.getOrNull(index)
                if (oldItem != null && newItem != oldItem) {
                    sendNotification(
                        context,
                        title = "Panic Button",
                        message = "Buzzer telah dibunyikan oleh nomor rumah: ${newItem.houseNumber}, prioritas: ${newItem.priority}"
                    )
                }
            }
            lastData = recordData
        }
    }

    Card(
        modifier
            .padding(horizontal = 24.dp)
            .clickable {
                navController.navigate("detail_rekap/${latestRecord.houseNumber}")
            }
            .wrapContentHeight()
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.elevatedCardElevation(4.dp),
        colors = CardDefaults.cardColors(
            contentColor = Color.White,
            containerColor = Color.White)
    ) {
        Box(
            modifier
                .fillMaxWidth()
                .padding(start = 24.dp, end = 24.dp, top = 28.dp, bottom = 20.dp),
            contentAlignment = Alignment.Center
        ) {
            if (latestRecord.status == "Selesai") Image( //jika status pada latestRecord Selesai maka Image ic_done akan muncul
                painter = painterResource(id = R.drawable.ic_done),
                contentDescription = "ic_done",
                modifier
                    .align(Alignment.TopEnd)
                    .size(32.dp)
            )
            else Image( //selain Selesai tampilkan ic_proses
                painter = painterResource(id = R.drawable.ic_process),
                contentDescription = "ic_process",
                modifier
                    .align(Alignment.TopEnd)
                    .size(32.dp)
            )
            Column(
                modifier.wrapContentSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "NOMOR RUMAH",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(id = R.color.font2)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = latestRecord.houseNumber,
                    fontSize = 64.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(id = R.color.font2)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = latestRecord.time,
                    fontSize = 16.sp,
                    color = colorResource(id = R.color.font2)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Column(
                    modifier
                        .fillMaxWidth()
                        .height(36.dp)
                        .background(
                            color = backgroundColor,
                            RoundedCornerShape(10.dp)
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = latestRecord.priority,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.White
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Column(
                    modifier
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(0.dp)
                ) {
                    Text(
                        text = "Pesan:",
                        fontSize = 14.sp,
                        color = colorResource(id = R.color.font)
                    )
                    Text(
                        text = latestRecord.message,
                        fontSize = 14.sp,
                        color = colorResource(id = R.color.font3),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        style = TextStyle(lineHeight = 20.sp),
                        textAlign = TextAlign.Justify
                    )
                }
            }
        }
    }
}