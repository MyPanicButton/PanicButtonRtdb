package com.example.panicbuttonrtdb.prensentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.panicbuttonrtdb.R

@Composable
fun HelpScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    var isClickedPanduan by remember { mutableStateOf(false) }
    var imageFullScreen by remember{ mutableStateOf(false) }
    var isClickedMasalah by remember{ mutableStateOf(false) }
    var isClickedTombol by remember { mutableStateOf(false) }
    var isClickedHubungi by remember{ mutableStateOf(false) }

    val stringMasalah = buildAnnotatedString {
        append("Apakah Anda mengalami kesulitan yang tidak dapat diselesaikan? Kami di sini untuk membantu!\n")
        append("Mungkin beberapa masalah tidak bisa langsung diatasi oleh aplikasi kami, tetapi Anda masih memiliki beberapa pilihan:\n\n")
        append("Langkah-langkah yang bisa Anda coba:\n")
        append("1. Periksa Koneksi Internet: Pastikan perangkat Anda memiliki koneksi internet yang stabil, karena fitur-fitur tertentu mungkin memerlukan akses internet.\n\n")
        append("2. Restart Aplikasi: Cobalah menutup aplikasi dan membukanya kembali. Ini bisa membantu jika ada bug sementara yang mempengaruhi kinerja aplikasi.\n\n")
        append("3. Update Aplikasi: Pastikan Anda menggunakan versi terbaru dari aplikasi Panic Button. Cek di Play Store atau App Store untuk pembaruan.\n\n")
    }
    val stringTombol = buildAnnotatedString {
        append("Mengalami masalah saat mencoba mengaktifkan tombol Panic Button? Berikut adalah beberapa solusi cepat untuk mengatasi masalah tersebut\n\n")
        append("Langkah-langkah Untuk Mengatasi Masalah:\n")
        append("1. Pastikan Izin Aplikasi Telah Diberikan: Aplikasi membutuhkan izin tertentu seperti akses lokasi atau izin lainnya untuk berfungsi dengan baik. Pastikan Anda telah memberikan izin yang diminta melalui pengaturan aplikasi.\n\n")
        append("2. Cek Baterai atau Mode Hemat Daya: Jika perangkat Anda dalam mode hemat daya, aplikasi mungkin dibatasi dalam menjalankan fungsinya. Coba nonaktifkan mode hemat daya dan pastikan baterai Anda mencukupi.\n\n")
        append("3. Restart Perangkat: Kadang kala, masalah teknis bisa diatasi dengan me-restart perangkat Anda.\n\n")
        append("4. Perbarui Aplikasi: Pastikan Anda menggunakan versi aplikasi yang paling terbaru. Pembaruan terkadang membawa perbaikan bug yang mungkin mempengaruhi fungsi tombol.\n\n")
    }

    Column(
        modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier
                .fillMaxWidth()
                .padding(start = 24.dp, top = 40.dp, end = 24.dp)
        ) {
            IconButton(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape),
                onClick = {
                    navController.popBackStack("user_profile", false)
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
            Column(
                modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Bantuan",
                    fontWeight = FontWeight.Bold,
                    color = colorResource(id = R.color.primary),
                    fontSize = 36.sp
                )
            }
        }
        Column(
            modifier
                .fillMaxWidth()
                .padding(start = 24.dp, end = 24.dp, top = 80.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier
                    .fillMaxWidth()
                    .clickable { isClickedPanduan = true },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Panduan pengggunaan",
                    fontSize = 16.sp,
                    color = colorResource(id = R.color.font2)
                )
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_right),
                    contentDescription = "ic_arrow_right",
                    tint = colorResource(id = R.color.font2)
                )
            }
            Row(
                modifier
                    .fillMaxWidth()
                    .clickable { isClickedMasalah = true },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Masalah belum diatasi",
                    fontSize = 16.sp,
                    color = colorResource(id = R.color.font2)
                )
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_right),
                    contentDescription = "ic_arrow_right",
                    tint = colorResource(id = R.color.font2)
                )
            }
            Row(
                modifier
                    .fillMaxWidth()
                    .clickable { isClickedTombol = true },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Saya tidak dapat mengaktifkan tombolnya",
                    fontSize = 16.sp,
                    color = colorResource(id = R.color.font2)
                )
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_right),
                    contentDescription = "ic_arrow_right",
                    tint = colorResource(id = R.color.font2)
                )
            }
            Row(
                modifier
                    .fillMaxWidth()
                    .clickable { isClickedHubungi = true },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Hubungi kami",
                    fontSize = 16.sp,
                    color = colorResource(id = R.color.font2)
                )
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_right),
                    contentDescription = "ic_arrow_right",
                    tint = colorResource(id = R.color.font2)
                )
            }
        }
    }
    if (isClickedPanduan) {
        AlertDialog(
            onDismissRequest = { isClickedPanduan = false },
            confirmButton = {
                TextButton(
                    onClick = { isClickedPanduan = false },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.primary)
                    )
                ) {
                    Text(
                        text = "Selesai",
                        color = Color.White
                    )
                }
            },
            title = {
                Text(
                    text = "Panduan penggunaan",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Image(
                    painter = painterResource(id = R.drawable.panduan),
                    contentDescription = "ic_panduan",
                    modifier = Modifier
                        .clickable { imageFullScreen = true }
                )
            }
        )
    }
    if (imageFullScreen){
        Dialog(
            onDismissRequest = { imageFullScreen = false },
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            Box(
                modifier
                    .fillMaxSize()
                    .clickable { imageFullScreen = false }
            ) {
                Image(
                    painter = painterResource(id = R.drawable.panduan),
                    contentDescription = "panduan",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxSize()
                )
            }
        }
    }
    if (isClickedMasalah){
        AlertDialog(
            onDismissRequest = { isClickedMasalah = false},
            confirmButton = {
                TextButton(
                    onClick = { isClickedMasalah = false },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.primary)
                    )
                ) {
                    Text(
                        text = "Selesai",
                        color = Color.White)
                }
            },
            title = {
                Text(
                    text = "Masalah belum di atasi",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Text(
                    text = stringMasalah,
                    fontSize = 12.sp
                )
            }
        )
    }
    if (isClickedTombol){
        AlertDialog(
            onDismissRequest = { isClickedTombol = false },
            confirmButton = {
                TextButton(
                    onClick = { isClickedTombol = false },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.primary)
                    )
                ) {
                    Text(
                        text = "Selesai",
                        color = Color.White
                    )
                }
            },
            title = {
                Text(
                    text = "Saya tidak dapat mengaktifkan tombolnya",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Text(
                    text = stringTombol,
                    fontSize = 12.sp
                )
            }
        )
    }
    if (isClickedHubungi){
        AlertDialog(
            onDismissRequest = { isClickedHubungi = false},
            confirmButton = {
                TextButton(
                    onClick = { isClickedHubungi = false },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.primary)
                    )
                ) {
                    Text(
                        text =" Selesai",
                        color = Color.White
                    )
                }
            },
            title = {
                Text(
                    text = "Hubungi kami",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Text(
                    text = "lifemedia@gmail.com"
                )
            }
        )
    }
}
