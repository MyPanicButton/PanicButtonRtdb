package com.example.panicbuttonrtdb.prensentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.panicbuttonrtdb.R
import com.example.panicbuttonrtdb.data.MonitorRecord
import com.example.panicbuttonrtdb.viewmodel.ViewModel

@Composable
fun ConfirmationButton(
    modifier: Modifier = Modifier,
    viewModel: ViewModel,
    record: MonitorRecord,
    onConfirm: () -> Unit
) {
    val ifDone by viewModel.monitorData.observeAsState(emptyList())
    var isClicked by remember { mutableStateOf(false) }

    Box(
        modifier
            .height(30.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.CenterEnd
    ) {
        if (isClicked) {
            Row(
                modifier
                    .height(30.dp)
                    .width(108.dp)
                    .background(Color.White, RoundedCornerShape(16.dp))
                    .padding(start = 20.dp, end = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_done),
                    contentDescription = "ic_done",
                    modifier = Modifier.size(18.dp)
                )
                Text(
                    text = "Selesai",
                    fontSize = 12.sp,
                    color = Color.Black
                )
            }
        } else {
            ifDone.forEach { _ ->
                if (record.status == "Selesai") Row(
                    modifier
                        .height(30.dp)
                        .width(108.dp)
                        .background(Color.White, RoundedCornerShape(16.dp))
                        .padding(start = 20.dp, end = 20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_done),
                        contentDescription = "ic_done",
                        modifier = Modifier.size(18.dp)
                    )
                    Text(
                        text = "Selesai",
                        fontSize = 12.sp,
                        color = Color.Black
                    )
                }
                else Column(
                    modifier
                        .width(80.dp)
                        .height(30.dp)
                        .background(colorResource(id = R.color.primary), RoundedCornerShape(16.dp))
                        .clickable {
                            isClicked = true
                            onConfirm()
                        },
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Proses",
                        color = Color.White,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}
