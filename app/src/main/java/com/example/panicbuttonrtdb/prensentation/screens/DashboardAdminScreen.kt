package com.example.panicbuttonrtdb.prensentation.screens

import android.app.Activity
import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.panicbuttonrtdb.R
import com.example.panicbuttonrtdb.data.MonitorRecord
import com.example.panicbuttonrtdb.prensentation.components.MonitorScreen
import com.example.panicbuttonrtdb.prensentation.components.ThreeMonitorItem
import com.example.panicbuttonrtdb.viewmodel.ViewModel

@Composable
fun DashboardAdminScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: ViewModel,
    context: Context,
    record: MonitorRecord,
    onLogout: () -> Unit
) {
    BackHandler { //jika tombol back ditekan maka akan menutup activity
        (context as? Activity)?. finish()
    }

    LaunchedEffect(Unit) {
        viewModel.fetchDataRecordTakeThree()
    }

    Column(
        modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.primary)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.CenterEnd
        ) {
            Text(
                modifier = Modifier
                    .padding(top = 40.dp)
                    .align(Alignment.Center),
                textAlign = TextAlign.Center,
                text = "INFORMASI\nDARURAT",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                style = TextStyle(lineHeight = 40.sp)
            )
            IconButton(
                modifier = Modifier
                    .padding(end = 24.dp),
                onClick = onLogout,
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = colorResource(id = R.color.background_button)
                )
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_logout),
                    contentDescription = "logout icon",
                    tint = Color.White
                )
            }
        }
        Spacer(modifier = Modifier.height(28.dp))

        MonitorScreen(
            monitorViewModel = viewModel,
            onClick = {
                navController.navigate("history/${record.houseNumber}")
            }
        )
        Spacer(modifier = Modifier.height(24.dp))
        Column(
            modifier
                .fillMaxSize()
                .background(color = colorResource(id = R.color.background))
        ) {
            Row(
                modifier
                    .padding(top = 16.dp, start = 24.dp, bottom = 8.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_warning),
                    contentDescription = "ic_warning",
                    tint = colorResource(id = R.color.primary)
                )
                Text(
                    text = "Data Terbaru",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = colorResource(id = R.color.primary)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            ThreeMonitorItem(viewModel = viewModel)
            Button(
                onClick = {
                    navController.navigate("data_rekap")
                },
                modifier
                    .padding(horizontal = 24.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.primary)
                )
            ) {
                Text(
                    text = "List Data Rekap",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White
                )
            }
        }
    }
}
