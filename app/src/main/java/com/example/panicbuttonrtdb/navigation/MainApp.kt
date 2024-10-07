package com.example.panicbuttonrtdb.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.panicbuttonrtdb.screens.DashboardAdminScreen
import com.example.panicbuttonrtdb.screens.DashboardUserScreen
import com.example.panicbuttonrtdb.screens.LoginScreen
import com.example.panicbuttonrtdb.screens.SignUpScreen
import com.example.panicbuttonrtdb.viewmodel.ViewModel
import com.example.panicbuttonrtdb.viewmodel.ViewModelFactory

@Composable
fun MainApp() {
    val context = LocalContext.current // Dapatkan konteks
    val viewModel: ViewModel = viewModel(factory = ViewModelFactory(context)) // Inisialisasi ViewModel

    val navController = rememberNavController()


    // Cek status login untuk menentukan layar awal
    val startDestination = if (viewModel.isUserLoggedIn()) {
        "dashboard" // Jika pengguna sudah login
    } else {
        "login" // Jika pengguna belum login
    }

    NavHost(navController = navController, startDestination = startDestination) {
        // Halaman Sign Up
        composable("signup") {
            SignUpScreen(navController = navController)
        }

        // Halaman Login
        composable("login") {
            LoginScreen(
                navController = navController,
                viewModel = viewModel // Mengoper ViewModel ke LoginScreen
            )
        }

        // Halaman Dashboard
        composable("dashboard") {
            DashboardUserScreen(
                viewModel = viewModel,
                onLogout = {
                    viewModel.logout() // Panggil fungsi logout dari ViewModel
                    navController.navigate("login") // Navigasi kembali ke layar login
                }
            )
        }

        composable("dashboard_admin") {
            DashboardAdminScreen(viewModel = viewModel)
        }
    }
}


