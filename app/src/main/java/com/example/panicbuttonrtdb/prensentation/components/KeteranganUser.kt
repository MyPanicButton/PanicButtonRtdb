package com.example.panicbuttonrtdb.prensentation.components

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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.panicbuttonrtdb.R

@Composable
fun KeteranganUser(
    modifier: Modifier = Modifier
) {

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
            text = "keteranganUser",
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
                    onClick = { },
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
    }
}