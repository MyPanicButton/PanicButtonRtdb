package com.example.panicbuttonrtdb.prensentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.panicbuttonrtdb.R
import com.example.panicbuttonrtdb.viewmodel.ViewModel
import kotlinx.coroutines.delay

@Composable
fun LatestMonitorItem(
    modifier: Modifier = Modifier,
    viewModel: ViewModel,
    navController: NavController
) {
    val recordData by viewModel.monitorData.observeAsState(emptyList())

    LaunchedEffect(Unit) {
        while (true) {
            viewModel.latestMonitorItem()
            delay(1000)
        }
    }

    Surface(
        modifier
            .padding(start = 24.dp, end = 24.dp),
        shadowElevation = 4.dp,
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier
                .fillMaxWidth()
                .background(Color.White, RoundedCornerShape(16.dp))
                .padding(top = 16.dp, bottom = 16.dp)
        ){
            recordData.forEachIndexed { index, record ->
                Column(
                    modifier
                        .padding(start = 26.dp, end = 25.dp)
                        .clickable { navController.navigate("detail_rekap/${record.houseNumber}") }
                ) {
                    Row(
                        modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = record.houseNumber,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = colorResource(id = R.color.font2)
                        )
                        Column(
                            modifier
                                .wrapContentSize()
                                .background(
                                    color = when (record.priority) {
                                        "Darurat" -> colorResource(id = R.color.darurat)
                                        "Penting" -> colorResource(id = R.color.penting)
                                        else -> colorResource(id = R.color.biasa)
                                    },
                                    RoundedCornerShape(6.dp)

                                ),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                text = record.priority,
                                fontSize = 12.sp,
                                color = Color.White
                            )
                        }
                        Text(
                            text = record.time,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal,
                            color = colorResource(id = R.color.font)
                        )
                    }
                    Spacer(modifier = Modifier.height(2.dp))

                    Box(
                        modifier
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = record.message,
                            fontSize = 12.sp,
                            color = colorResource(id = R.color.font3),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                                .padding(end = 24.dp)
                        )
                        Spacer(modifier = Modifier.matchParentSize())
                        if (record.status == "Selesai") Image(
                            painter = painterResource(id = R.drawable.ic_done),
                            contentDescription = "ic_done",
                            modifier
                                .size(24.dp)
                                .align(Alignment.CenterEnd)
                        ) else Image(
                            painter = painterResource(id = R.drawable.ic_process),
                            contentDescription = "ic_done",
                            modifier
                                .size(24.dp)
                                .align(Alignment.CenterEnd)
                        )
                    }
                    if (index < recordData.size - 1) { //kurangi 1 garis di akhir
                        HorizontalDivider(
                            modifier = Modifier.padding(top = 8.dp, bottom = 10.dp),
                            color = Color.Gray,
                            thickness = 0.5.dp
                        )
                    }
                }
            }
        }
    }
}