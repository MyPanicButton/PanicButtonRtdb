package com.example.panicbuttonrtdb.viewmodel

import android.content.Context

class UserPreferences(context: Context) {
    private val prefs = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)


    // Menyimpan status login pengguna
    fun saveUserLoggedIn(isLoggedIn: Boolean) {
        prefs.edit().putBoolean("is_logged_in", isLoggedIn).apply()
    }

    // Mengambil status login pengguna
    fun isUserLoggedIn(): Boolean {
        return prefs.getBoolean("is_logged_in", false)
    }

    // Fungsi untuk menyimpan nama pengguna dan nomor rumah
    fun saveUserInfo(name: String, houseNumber: String) {
        prefs.edit().putString("user_name", name).putString("house_number", houseNumber).apply()
    }

    fun getUserName(): String? {
        return prefs.getString("user_name", "")
    }

    fun getHouseNumber(): String? {
        return prefs.getString("house_number", "")
    }

    // Fungsi untuk menghapus data pengguna saat logout
    fun clearUserInfo() {
        prefs.edit().clear().apply()
    }
}