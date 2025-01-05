package com.example.panicbuttonrtdb.prensentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import coil.request.CachePolicy
import com.example.panicbuttonrtdb.R
import com.example.panicbuttonrtdb.data.User
import com.example.panicbuttonrtdb.prensentation.components.DetailRekapItem
import com.example.panicbuttonrtdb.prensentation.components.UserInformationForAdmin
import com.example.panicbuttonrtdb.viewmodel.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.delay

@Composable
fun DetailRekapScreen(
    modifier: Modifier = Modifier,
    viewModel: ViewModel,
    navController : NavController,
    houseNumber: String
) {
    val databaseRef = FirebaseDatabase.getInstance().getReference("users")
    var user by remember { mutableStateOf<User?>(null) }
    val emptyProfile = R.drawable.ic_empty_profile
    val emptyCover = R.drawable.empty_image
    val record by viewModel.monitorData.observeAsState(emptyList())
    val unit = record.filter { it.houseNumber == houseNumber }
    val profileImageUrl = if (user?.imageProfile.isNullOrEmpty()) emptyProfile else user?.imageProfile
    val coverImageUrl = if (user?.coverImage.isNullOrEmpty()) emptyCover else user?.coverImage

    LaunchedEffect(houseNumber) { //menampilkan image berdasarkan houseNumber
        databaseRef.orderByChild("houseNumber").equalTo(houseNumber)
            .addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val matchedUser = snapshot.children
                        .mapNotNull { it.getValue(User::class.java) }
                        .firstOrNull { it.houseNumber == houseNumber }

                    if (matchedUser != null) { user = matchedUser }
                }
            }

            override fun onCancelled(error: DatabaseError) { }
        })
    }

    LaunchedEffect(houseNumber) {
        while (true) {
            viewModel.detailRekap(houseNumber)
            delay(2000)
        }
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
                painter = rememberImagePainter(
                    data = coverImageUrl,
                    builder = {
                        crossfade(true)
                        diskCachePolicy(CachePolicy.DISABLED)
                        memoryCachePolicy(CachePolicy.DISABLED)
                        build()
                    }
                ),
                contentDescription = "cover_image",
                contentScale = ContentScale.Crop
            )

            IconButton(
                modifier = Modifier
                    .padding(top = 40.dp, start = 24.dp)
                    .size(36.dp)
                    .clip(CircleShape),
                onClick = {
                    navController.popBackStack()
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
                    record.forEach { rekap ->
                        Text(
                            text = rekap.name,
                            fontSize = 18.sp,
                            color = colorResource(id = R.color.font),
                            fontWeight = FontWeight.Bold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    Row(
                        modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Nomor Rumah Anda:",
                            fontSize = 12.sp
                        )
                        Text(
                            text = rekap.houseNumber,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = colorResource(id = R.color.font2),
                            maxLines = 1
                        )

                        Spacer(modifier.padding(bottom = 24.dp))

                        Spacer(modifier = Modifier.padding(bottom = 48.dp))
                    }
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
            }
            Column(
                modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .padding(top = 100.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    modifier = Modifier.padding(start = 24.dp),
                    text = "Keterangan",
                    fontSize = 14.sp,
                    color = Color.White
                )
                UserInformationForAdmin(
                    viewModel = viewModel,
                    houseNumber = houseNumber
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    modifier = Modifier.padding(start = 24.dp),
                    text = "Detail Rekap",
                    fontSize = 14.sp,
                    color = Color.White
                )
                Column(
                    modifier
                        .fillMaxSize()
                        .background(
                            Color.White,
                            RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                        )
                        .padding(top = 24.dp)
                ) {
                    LazyColumn(
                        modifier
                            .padding(bottom = 40.dp)
                            .fillMaxWidth()
                            .padding(horizontal = 26.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        items(unit) { log ->
                            DetailRekapItem(
                                record = log,
                                viewModel = viewModel
                            )
                        }
                    }
                }
            }
        }
    }
}