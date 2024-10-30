package com.example.panicbuttonrtdb.prensentation.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.panicbuttonrtdb.R
import com.example.panicbuttonrtdb.data.MonitorRecord
import com.example.panicbuttonrtdb.viewmodel.ViewModel

@Composable
fun DetailRekapItem(
    record: MonitorRecord,
    modifier: Modifier = Modifier,
    viewModel: ViewModel,
    recorId: String //utk menerima key unik

) {


    LaunchedEffect(record) {
        viewModel.detailRekap(record.houseNumber)
    }

    Card(
        modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(bottom = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = record.houseNumber,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(id = R.color.font2)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column(
                    modifier
                        .wrapContentWidth()
                        .height(22.dp)
                        .background(
                            color = when (record.priority) {
                                "Darurat" -> colorResource(id = R.color.darurat)
                                "Penting" -> colorResource(id = R.color.penting)
                                else -> colorResource(id = R.color.biasa)
                            }, RoundedCornerShape(6.dp)
                        )
                        .padding(horizontal = 4.dp)
                ) {
                    Text(
                        text = record.priority,
                        fontSize = 12.sp,
                        color = Color.White
                    )
                }
                Box(
                    modifier
                        .fillMaxWidth(),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Text(
                        text = record.time,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = colorResource(id = R.color.primary)
                    )
                }
            }
            Text(
                text = record.message,
                fontSize = 12.sp,
                color = colorResource(id = R.color.font3),
                style = TextStyle(lineHeight = 20.sp),
                overflow = TextOverflow.Ellipsis
            )
            if (record.id.isNotEmpty()) {
                ConfirmationButton(
                    viewModel = viewModel,
                    record = record,
                    onConfirm = { viewModel.updateStatus(record.id) }
                )
            } else {
                Log.e("DetailRekapItem", "Record ID is empty, cannot update status.")
            }
        }
    }
}
