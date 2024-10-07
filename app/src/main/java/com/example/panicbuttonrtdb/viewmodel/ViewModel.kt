package com.example.panicbuttonrtdb.viewmodel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ViewModel(private val context: Context) : ViewModel() {

    private val database = FirebaseDatabase.getInstance()
    private val databaseRef = FirebaseDatabase.getInstance().getReference("/buzzer")
    private val userPreferences = UserPreferences(context)

    var currentUserName by mutableStateOf("")
    var currentUserHouseNumber by mutableStateOf("")

    private val _monitorData = MutableLiveData<List<MonitorRecord>>()
    val monitorData: LiveData<List<MonitorRecord>> get() = _monitorData

    fun isUserLoggedIn(): Boolean {
        return userPreferences.isUserLoggedIn()
    }

    // LiveData untuk memantau status buzzer
    private val _buzzerState = MutableLiveData<String>()
    val buzzerState: LiveData<String> = _buzzerState

    init {
        // Ambil data pengguna yang tersimpan saat aplikasi dibuka kembali
        currentUserName = userPreferences.getUserName() ?: ""
        currentUserHouseNumber = userPreferences.getHouseNumber() ?: ""
    }

    init {
        // Inisialisasi untuk mendapatkan status awal buzzer dari Firebase
        getBuzzerState()
    }

    // Fungsi untuk menyimpan user baru ke Firebase
    fun saveUserToFirebase(
        name: String,
        houseNumber: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit  // Tambahkan callback untuk error

    ) {
        val usersRef = database.getReference("users")


        // Periksa apakah sudah ada pengguna dengan nomor rumah atau nama yang sama
        usersRef.orderByChild("houseNumber").equalTo(houseNumber)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        // Jika nomor rumah sudah ada
                        onFailure("Nomor rumah sudah digunakan.")
                    } else {
                        // Lakukan pengecekan untuk nama
                        usersRef.orderByChild("name").equalTo(name)
                            .addListenerForSingleValueEvent(object : ValueEventListener {
                                override fun onDataChange(snapshot: DataSnapshot) {
                                    if (snapshot.exists()) {
                                        // Jika nama sudah ada
                                        onFailure("Nama sudah digunakan.")
                                    } else {
                                        // Jika tidak ada duplikasi, simpan data pengguna
                                        val userId = usersRef.push().key // Buat key unik untuk user baru
                                        val user = User(name, houseNumber, password)

                                        userId?.let {
                                            usersRef.child(it).setValue(user)
                                                .addOnCompleteListener { task ->
                                                    if (task.isSuccessful) {
                                                        onSuccess()  // Data berhasil disimpan
                                                    } else {
                                                        onFailure("Gagal menyimpan data.")
                                                    }
                                                }
                                        }
                                    }
                                }

                                override fun onCancelled(error: DatabaseError) {
                                    onFailure("Terjadi kesalahan: ${error.message}")
                                }
                            })
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    onFailure("Terjadi kesalahan: ${error.message}")
                }
            })

    }

    // Fungsi untuk validasi login
    fun validateLogin(houseNumber: String, password: String, onResult: (Boolean, Boolean) -> Unit) {
        // Kredensial Admin
        val adminHouseNumber = "admin"
        val adminPassword = "admin"

        // Periksa apakah login sebagai admin
        if (houseNumber == adminHouseNumber && password == adminPassword) {
            // Jika sesuai kredensial admin, anggap login sebagai admin
            onResult(true, true)
            return
        }

        // Jika bukan admin, cek user biasa di Firebase
        val usersRef = database.getReference("users")
        usersRef.orderByChild("houseNumber").equalTo(houseNumber)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        for (userSnapshot in snapshot.children) {
                            val user = userSnapshot.getValue(User::class.java)
                            if (user != null && user.password == password) {
                                // Jika login berhasil sebagai user biasa
                                userPreferences.saveUserLoggedIn(true)
                                userPreferences.saveUserInfo(user.name, user.houseNumber)

                                currentUserName = user.name
                                currentUserHouseNumber = user.houseNumber
                                onResult(true, false)
                            } else {
                                onResult(false, false)
                            }
                        }
                    } else {
                        onResult(false, false)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    onResult(false, false)
                }
            })
    }

    // Fungsi untuk logout
    fun logout() {
        // Menghapus status login dari SharedPreferences
        userPreferences.saveUserLoggedIn(false)
        currentUserName = "" // Reset nama pengguna saat logout
        currentUserHouseNumber = "" // Reset nomor rumah saat logout
    }

    // Fungsi untuk menyimpan data ke path /monitor di Firebase
    fun saveMonitorData() {
        val monitorRef = database.getReference("monitor")

        val data = mapOf(
            "name" to currentUserName,
            "houseNumber" to currentUserHouseNumber,
            "time" to getCurrentTimestampFormatted() // Waktu saat toggle diaktifkan
        )

        // Simpan data ke Firebase
        monitorRef.push().setValue(data)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("Firebase", "Data berhasil disimpan ke /monitor")
                } else {
                    Log.e("Firebase", "Gagal menyimpan data", task.exception)
                }
            }
    }

    // Fungsi untuk mendapatkan status buzzer dari Firebase
    private fun getBuzzerState() {
        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                _buzzerState.value = snapshot.getValue(String::class.java) ?: "Off"
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error, bisa diisi log jika dibutuhkan
            }
        })
    }

    // Fungsi untuk mengatur status buzzer di Firebase
    fun setBuzzerState(state: String) {
        databaseRef.setValue(state)
    }

    fun fetchMonitorData() {
        val monitorRef = database.getReference("monitor")

        monitorRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val records = mutableListOf<MonitorRecord>()
                for (recordSnapshot in snapshot.children) {
                    val record = recordSnapshot.getValue(MonitorRecord::class.java)
                    record?.let { records.add(it) }
                }
                _monitorData.value = records
                Log.d("Firebase", "Monitor Record: $records")
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Firebase", "Failed to fetch monitor data", error.toException())
            }
        })
    }

    private fun getCurrentTimestampFormatted(): String {
        val sdf = java.text.SimpleDateFormat("yyyy-MM-dd 'waktu' HH:mm", java.util.Locale.getDefault())
        return sdf.format(java.util.Date())
    }

}