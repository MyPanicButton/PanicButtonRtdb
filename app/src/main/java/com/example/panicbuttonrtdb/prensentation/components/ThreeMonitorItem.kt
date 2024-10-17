package com.example.panicbuttonrtdb.prensentation.components

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
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.panicbuttonrtdb.R
import com.example.panicbuttonrtdb.viewmodel.ViewModel

@Composable
fun ThreeMonitorItem(
    modifier: Modifier = Modifier,
    viewModel: ViewModel
) {
    val recordData by viewModel.monitorData.observeAsState(emptyList())

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
                        .clickable { }
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
                                .height(22.dp)
                                .wrapContentWidth()
                                .background(
                                    color = when (record.priority) {
                                        "darurat" -> Color.Red
                                        "penting" -> Color.Yellow
                                        else -> Color.Green
                                    },
                                    RoundedCornerShape(6.dp)

                                )
                                .padding(horizontal = 4.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
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