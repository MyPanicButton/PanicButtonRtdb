package com.example.panicbuttonrtdb

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.panicbuttonrtdb.navigation.MainApp
import com.example.panicbuttonrtdb.notification.createNotificationChannel
import com.example.panicbuttonrtdb.ui.theme.PanicButtonRtdbTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        Log.d("MainActivity", "Creating notification channel")
        createNotificationChannel(this)
        setContent {
            PanicButtonRtdbTheme {
                MainApp()
            }
        }
    }
}


