package com.example.panicbuttonrtdb.viewmodel

// Data class untuk User
data class User (
    val name: String,
    val houseNumber: String,
    val password: String
) {
    // Constructor tanpa argumen diperlukan oleh Firebase
    constructor() : this("", "", "")
}

data class MonitorRecord(
    val name: String = "",
    val houseNumber: String = "",
    val time: String = ""
)
