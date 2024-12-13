package com.example.panicbuttonrtdb.prensentation.screens

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.panicbuttonrtdb.R
import com.example.panicbuttonrtdb.data.User
import com.example.panicbuttonrtdb.prensentation.components.UserInformation
import com.example.panicbuttonrtdb.notification.openNotificationSettings
import com.example.panicbuttonrtdb.viewmodel.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

@Composable
fun UserProfileScreen(
    modifier: Modifier = Modifier,
    context: Context,
    navController: NavController,
    viewModel: ViewModel
) {

    val sharedPref = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
    val houseNumber = sharedPref.getString("house_number", "") ?: ""
    val databaseRef = FirebaseDatabase.getInstance().getReference("desaSalam/users")
    val userName = sharedPref.getString("user_name", "nama user tidak ditemukan")
    val nomorRumah = sharedPref.getString("house_number", "norum tidak ada")
    var user by remember {mutableStateOf<User?>(null)}
    val emptyProfile = R.drawable.ic_empty_profile
    val emptyCover = R.drawable.empty_image
    val profileImageUrl = if (user?.imageProfile.isNullOrEmpty()) emptyProfile else user?.imageProfile // jika null maka panggil emptyProfile jika ada maka panggil imageProfile di User
    val coverImageUrl = if (user?.coverImage.isNullOrEmpty()) emptyCover else user?.coverImage // jika null maka panggil emptyCover jika ada maka panggil coverImage di User
    val profileLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            viewModel.uploadImage(it, houseNumber, "profileImage", context)
        }
    }
    val coverLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            viewModel.uploadImage(it, houseNumber, "coverImage", context)
        }
    }

    LaunchedEffect(houseNumber) {
        databaseRef.orderByChild("houseNumber").equalTo(houseNumber).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val matchedUser = snapshot.children // matchedUser utk memfilter user
                        .mapNotNull { it.getValue(User::class.java) }
                        .firstOrNull { it.houseNumber == houseNumber }

                    if (matchedUser != null) { user = matchedUser }
                }
            }

            override fun onCancelled(error: DatabaseError) { }
        })
    }

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
                painter = rememberImagePainter(data = coverImageUrl),
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
                    onClick = { coverLauncher.launch("image/*")},
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
                    painter = rememberImagePainter(data = profileImageUrl),
                    contentDescription = "ic_empty_profile",
                    contentScale = ContentScale.Crop
                )
                Icon(
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { profileLauncher.launch("image/*") },
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
                UserInformation(viewModel = viewModel)

                Spacer(modifier = Modifier.height(16.dp))
                Column(
                    modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(horizontal = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Pengaturan",
                        fontSize = 14.sp,
                        color = Color.White
                    )
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(44.dp),
                        onClick = { openNotificationSettings(context) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(id = R.color.background_button_userInformationScreen)
                        ),
                        shape = RoundedCornerShape(16.dp),
                        elevation = ButtonDefaults.elevatedButtonElevation(
                            defaultElevation = 4.dp
                        )
                    ) {
                        Row(
                            modifier
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                modifier = Modifier.size(20.dp),
                                painter = painterResource(id = R.drawable.ic_notification),
                                contentDescription = "ic_notification",
                                tint = Color.White
                            )
                            Text(
                                text = "Notifikasi",
                                color = Color.White,
                                fontSize = 14.sp
                            )
                        }
                    }
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(44.dp),
                        onClick = { navController.navigate("help") },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(id = R.color.background_button_userInformationScreen)
                        ),
                        shape = RoundedCornerShape(16.dp),
                        elevation = ButtonDefaults.elevatedButtonElevation(
                            defaultElevation = 4.dp
                        )
                    ) {
                        Row(
                            modifier
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                modifier = Modifier.size(20.dp),
                                painter = painterResource(id = R.drawable.ic_help),
                                contentDescription = "ic_help",
                                tint = Color.White
                            )
                            Text(
                                text = "Bantuan",
                                color = Color.White,
                                fontSize = 14.sp
                            )
                        }
                    }
                }
            }
        }
    }
}
