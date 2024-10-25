package com.example.panicbuttonrtdb.prensentation.screens

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.panicbuttonrtdb.R
import com.example.panicbuttonrtdb.prensentation.components.KeteranganUser
import com.example.panicbuttonrtdb.notification.openNotificationSettings

@Composable
fun UserProfileScreen(
    modifier: Modifier = Modifier,
    context: Context,
    navController: NavController
) {

    val sharedPref = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
    val userName = sharedPref.getString("user_name", "nama user tidak ditemukan")
    val nomorRumah = sharedPref.getString("house_number", "norum tidak ada")

    Box(
        modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.primary))
    ) {
        Box(
            modifier
                .height(180.dp)
                .fillMaxWidth()
                .background(Color.White)
        ) {
            Image(
                modifier = Modifier
                    .fillMaxWidth(),
                painter = painterResource(id = R.drawable.empty_image),
                contentDescription = "cover_image",
                contentScale = ContentScale.Crop
            )
            Row(
                modifier
                    .padding(start = 24.dp, end = 24.dp, top = 40.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape),
                    onClick = {
                        navController.navigate("dashboard")
                    },
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = colorResource(id = R.color.background_button),
                        contentColor = colorResource(id = R.color.primary)
                    )
                ) {
                    Icon(
                        modifier = Modifier
                            .size(24.dp),
                        painter = painterResource(id = R.drawable.ic_back),
                        contentDescription = null
                    )
                }
                IconButton(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape),
                    onClick = { },
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = colorResource(id = R.color.background_button),
                        contentColor = colorResource(id = R.color.primary)
                    )
                ) {
                    Icon(
                        modifier = Modifier
                            .size(24.dp),
                        painter = painterResource(id = R.drawable.ic_edit),
                        contentDescription = null
                    )
                }
            }
        }
        Box(
            modifier
                .wrapContentSize()
                .padding(top = 160.dp)
        ){
            Card(
                modifier
                    .padding(top = 26.dp)
                    .height(54.dp)
                    .fillMaxWidth()
                    .padding(start = 54.dp, end = 24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                elevation = CardDefaults.elevatedCardElevation(4.dp)
            ) {
                Column(
                    modifier
                        .padding(start = 60.dp, top = 4.dp, end = 6.dp, bottom = 4.dp)
                ) {
                    Text(
                        text = "$userName",
                        fontSize = 18.sp,
                        color = colorResource(id = R.color.font),
                        fontWeight = FontWeight.Bold
                    )
                    Row(
                        modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Nomor Rumah anda",
                            fontSize = 12.sp
                        )
                        Text(
                            text = "$nomorRumah",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = colorResource(id = R.color.font2)
                        )
                    }
                }
            }
            Box(
                modifier
                    .wrapContentSize(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    modifier = Modifier
                        .padding(horizontal = 24.dp)
                        .clip(CircleShape)
                        .size(80.dp)
                        .background(color = Color.White)
                        .border(
                            width = 4.dp,
                            color = colorResource(id = R.color.primary),
                            shape = RoundedCornerShape(100.dp)
                        ),
                    painter = painterResource(id = R.drawable.ic_empty_profile),
                    contentDescription = "ic_empty_profile",
                    contentScale = ContentScale.Crop
                )
                Icon(
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { },
                    painter = painterResource(id = R.drawable.ic_edit),
                    contentDescription = "ic_edit",
                    tint = colorResource(id = R.color.primary)
                )
            }
        }
        Column(
            modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(top = 260.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    modifier = Modifier.padding(start = 24.dp),
                    text = "Keterangan",
                    fontSize = 14.sp,
                    color = Color.White
                )
                KeteranganUser()
                Spacer(modifier = Modifier.height(16.dp))
                Column(
                    modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(start = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "Pengaturan",
                        fontSize = 14.sp,
                        color = Color.White
                    )
                    TextButton(
                        modifier = Modifier,
                        onClick = {  },
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(id = R.color.background_button)
                        )
                    ) {
                        Icon(
                            modifier = Modifier.size(20.dp),
                            painter = painterResource(id = R.drawable.ic_language),
                            contentDescription = "ic_notification",
                            tint = Color.White
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Bahasa",
                            color = Color.White,
                            fontSize = 12.sp
                        )
                    }
                    TextButton(
                        modifier = Modifier,
                        onClick = { openNotificationSettings(context) },
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(id = R.color.background_button)
                        )
                    ) {
                        Icon(
                            modifier = Modifier.size(20.dp),
                            painter = painterResource(id = R.drawable.ic_notification),
                            contentDescription = "ic_notification",
                            tint = Color.White
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Notifikasi",
                            color = Color.White,
                            fontSize = 12.sp
                        )
                    }
                    TextButton(
                        modifier = Modifier,
                        onClick = { navController.navigate("help") },
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(id = R.color.background_button)
                        )
                    ) {
                        Icon(
                            modifier = Modifier.size(20.dp),
                            painter = painterResource(id = R.drawable.ic_help),
                            contentDescription = "ic_help",
                            tint = Color.White
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Bantuan",
                            color = Color.White,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Liat() {
    UserProfileScreen(
        context = LocalContext.current,
        navController = rememberNavController()
    )
}