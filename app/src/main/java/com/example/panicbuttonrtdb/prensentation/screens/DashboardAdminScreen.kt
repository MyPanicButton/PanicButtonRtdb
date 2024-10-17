package com.example.panicbuttonrtdb.prensentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.panicbuttonrtdb.R
import com.example.panicbuttonrtdb.data.MonitorRecord
import com.example.panicbuttonrtdb.prensentation.components.MonitorScreen
import com.example.panicbuttonrtdb.viewmodel.ViewModel

@Composable
fun DashboardAdminScreen(
    navController: NavController,
    viewModel: ViewModel,
    //onLogout: () -> Unit,
    record: MonitorRecord,
    modifier: Modifier = Modifier
) {

    val monitorData by viewModel.monitorData.observeAsState(initial = emptyList())

    // Panggil fungsi untuk fetch data ketika screen dibuka
    LaunchedEffect(Unit) {
        viewModel.fetchMonitorData()
    }

    Column(
        modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.primary)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 40.dp),
            textAlign = TextAlign.Center,
            text = "INFORMASI\nDARURAT",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            style = TextStyle(lineHeight = 40.sp)
        )

        Spacer(modifier = Modifier.height(28.dp))

        MonitorScreen(viewModel, onClick = { navController.navigate("history/${record.houseNumber}") })


    }
}

@Composable
fun MonitorItem(record: MonitorRecord, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() }
            .border(1.dp, Color.Gray)
            .padding(8.dp)
    ) {
        Text(text = "Name: ${record.name}")
        Text(text = "House Number: ${record.houseNumber}")
        Text(text = "Message: ${record.message}")
        Text(text = "Priority: ${record.priority}")
        Text(text = "Timestamp: ${record.time}")

    }
}

