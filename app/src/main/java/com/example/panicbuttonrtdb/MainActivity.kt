package com.example.panicbuttonrtdb

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.panicbuttonrtdb.navigation.MainApp
import com.example.panicbuttonrtdb.ui.theme.PanicButtonRtdbTheme
import com.google.firebase.database.FirebaseDatabase

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PanicButtonRtdbTheme {
                MainApp()
            }
        }
    }
}


