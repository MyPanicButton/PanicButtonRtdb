package com.example.panicbuttonrtdb.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.panicbuttonrtdb.prensentation.screens.UserProfileScreen
import com.example.panicbuttonrtdb.prensentation.screens.DetailRekapScreen
import com.example.panicbuttonrtdb.prensentation.screens.DataRekapScreen
import com.example.panicbuttonrtdb.prensentation.screens.DashboardAdminScreen
import com.example.panicbuttonrtdb.prensentation.screens.DashboardUserScreen
import com.example.panicbuttonrtdb.prensentation.screens.HelpScreen
import com.example.panicbuttonrtdb.prensentation.screens.HistoryScreen
import com.example.panicbuttonrtdb.prensentation.screens.LoginScreen
import com.example.panicbuttonrtdb.prensentation.screens.SignUpScreen
import com.example.panicbuttonrtdb.viewmodel.ViewModel
import com.example.panicbuttonrtdb.viewmodel.ViewModelFactory

@Composable
fun MainApp() {
    val context = LocalContext.current // Dapatkan konteks
    val viewModel: ViewModel = viewModel(factory = ViewModelFactory(context)) // Inisialisasi ViewModel

    val navController = rememberNavController()


    // Cek status login untuk menentukan layar awal
    val startDestination =
        if (viewModel.isAdminLoggedIn()){
            "dashboard_admin"
        } else if (viewModel.isUserLoggedIn()) {
            "dashboard"
        } else
            "login"
//        if (viewModel.isUserLoggedIn()) {
//        "dashboard" // Jika pengguna sudah login
//    } else {
//        "login" // Jika pengguna belum login
//    }

    NavHost(navController = navController, startDestination = startDestination) {
        // Halaman Sign Up
        composable("signup") {
            SignUpScreen(navController = navController)
        }

        // Halaman Login
        composable("login") {
            LoginScreen(
                navController = navController,
                viewModel = viewModel, // Mengoper ViewModel ke LoginScreen
                context = context
            )
        }

        // Halaman Dashboard
        composable("dashboard") {
            DashboardUserScreen(
                context = context,
                viewModel = viewModel,
                navController = navController,
                onLogout = {
                    viewModel.logout() // Panggil fungsi logout dari ViewModel
                    navController.navigate("login") // Navigasi kembali ke layar login
                }
            )
        }

        composable("dashboard_admin") {
            DashboardAdminScreen(
                context = context,
                navController = navController,
                viewModel = viewModel,
                onLogout = {
                    viewModel.adminLogout()
                    navController.navigate("login")
                }
            )
        }

        // Halaman History untuk nomor rumah tertentu
        composable(
            route = "history/{houseNumber}",
            arguments = listOf(navArgument("houseNumber") { type = NavType.StringType })
        ) { backStackEntry ->
            val houseNumber = backStackEntry.arguments?.getString("houseNumber")
            if (houseNumber != null) {
                HistoryScreen(navController = navController, viewModel = viewModel, houseNumber = houseNumber)
            }
        }

        composable("data_rekap") {
            DataRekapScreen(
                viewModel = viewModel,
                navController = navController
            )
        }
        composable("detail_rekap/{houseNumber}") {backStackEntry ->
            val nomorRumah = backStackEntry.arguments?.getString("houseNumber")
            DetailRekapScreen(
                houseNumber = nomorRumah ?:"",
                viewModel = viewModel,
                navController = navController
            )
        }
        composable("user_profile") {
            UserProfileScreen(
                context = context,
                navController = navController
            )
        }
        composable("help") {
            HelpScreen(
                navController =navController
            )
        }
    }
}


