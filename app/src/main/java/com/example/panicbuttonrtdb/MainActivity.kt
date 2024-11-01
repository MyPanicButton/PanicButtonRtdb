package com.example.panicbuttonrtdb

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.example.panicbuttonrtdb.navigation.MainApp
import com.example.panicbuttonrtdb.notification.createNotificationChannel
import com.example.panicbuttonrtdb.notification.sendNotification
import com.example.panicbuttonrtdb.ui.theme.PanicButtonRtdbTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val requestPermissionLauncher = registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted: Boolean ->
                if (isGranted) {
                    Log.d("Permission", "POST_NOTIFICATIONS granted")
                } else {
                    Log.d("Permission", "POST_NOTIFICATIONS denied")
                }
            }

            if (ContextCompat.checkSelfPermission(
                    this, "android.permission.POST_NOTIFICATIONS"
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissionLauncher.launch("android.permission.POST_NOTIFICATIONS")
            }
        }
//        createNotificationChannel(context = this)

        createNotificationChannel(this)
        setContent {
            PanicButtonRtdbTheme {
                MainApp()
            }
        }
    }
}


