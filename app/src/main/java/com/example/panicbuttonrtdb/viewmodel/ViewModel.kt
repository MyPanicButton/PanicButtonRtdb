package com.example.panicbuttonrtdb.viewmodel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.panicbuttonrtdb.data.MonitorRecord
import com.example.panicbuttonrtdb.data.User
import com.example.panicbuttonrtdb.data.UserPreferences
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ViewModel(private val context: Context) : ViewModel() {

    private val database = FirebaseDatabase.getInstance()
    private val databaseRef = FirebaseDatabase.getInstance().getReference("/buzzer")
    private val userPreferences = UserPreferences(context)
    private val monitorRef = database.getReference("monitor")

    var currentUserName by mutableStateOf("")
    var currentUserHouseNumber by mutableStateOf("")

    private val _monitorData = MutableLiveData<List<MonitorRecord>>()
    val monitorData: LiveData<List<MonitorRecord>> get() = _monitorData

    private val _latestRecord = MutableStateFlow(MonitorRecord())
    val latestRecord: StateFlow<MonitorRecord> = _latestRecord

    fun isUserLoggedIn(): Boolean {
        return userPreferences.isUserLoggedIn()
    }

    fun isAdminLoggedIn(): Boolean {
        return userPreferences.isAdminLoggedIn()
    }

    // LiveData untuk memantau status buzzer
    private val _buzzerState = MutableLiveData<String>()
    val buzzerState: LiveData<String> = _buzzerState

    init {
        // Ambil data pengguna yang tersimpan saat aplikasi dibuka kembali
        currentUserName = userPreferences.getUserName() ?: ""
        currentUserHouseNumber = userPreferences.getHouseNumber() ?: ""
    }

//    init {
//        fetchLatestRecord()
//    }

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
            userPreferences.saveAdminLoggedIn(true)
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

    fun adminLogout() {
        userPreferences.saveAdminLoggedIn(false)
        userPreferences.clearUserInfo()
    }

    // Fungsi untuk menyimpan data ke path /monitor di Firebase
    fun saveMonitorData(message: String, priority: String) {
        val monitorRef = database.getReference("monitor")

        val data = mapOf(
            "name" to currentUserName,
            "houseNumber" to currentUserHouseNumber,
            "message" to message,
            "priority" to priority,
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
                for (recordSnapshot in snapshot.children.reversed()) {
                    val record = recordSnapshot.getValue(MonitorRecord::class.java)
                    record?.let { records.add(it) }
                }
                _monitorData.value = records
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Firebase", "Failed to fetch monitor data", error.toException())
            }
        })
    }

    fun fetchLatestRecord() {
        val latestData = database.getReference("monitor")
        latestData.orderByKey().limitToLast(1)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val data = snapshot.children.first().getValue(MonitorRecord::class.java)
                        data?.let {
                            viewModelScope.launch {
                                _latestRecord.emit(it)
                            }
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle error
                }
            })
    }

    // fun mengambil 4 data dan menampilkan 3
    fun latestMonitorItem() {

        monitorRef.orderByKey().limitToLast(4) // take 4 data terbaru
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val records = mutableListOf<MonitorRecord>()

                    for ((count, recordSnapshot) in snapshot.children.reversed().withIndex()) {
                        if (count != 0) { // lewati data pertama
                            val record = recordSnapshot.getValue(MonitorRecord::class.java)
                            record?.let { records.add(it) }
                        }
                    }

                    _monitorData.value = records.take(3) // take 3 data
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("Firebase", "Failed to fetch monitor data", error.toException())
                }
            })
    }

    // fun utk menampilkan detail rekap berdasarkan nomor rumah
    fun detailRekap(houseNumber: String) {

        monitorRef.orderByChild("houseNumber").equalTo(houseNumber)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val records = mutableListOf<MonitorRecord>()
                        for (recordSnapshot in snapshot.children) {
                            val record = recordSnapshot.getValue(MonitorRecord::class.java)
                            record?.let { records.add(it) }
                        }

                    _monitorData.value  =records
                } else {
                        Log.d("Firebase", "Tidak ada houseNumber: $houseNumber")
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("Firebase", "Failed to fetch detail rekap data", error.toException())
                }
            })
    }

    //fun utk menampilkan riwayat user berdasarkan houseNumber
    fun userHistory() {

        monitorRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val records = mutableListOf<MonitorRecord>()
                val userHistoryNumber = currentUserHouseNumber //ambil houseNumber dari user yang login

                for (recordSnapshot in snapshot.children){
                    val record = recordSnapshot.getValue(MonitorRecord::class.java)
                    if (record?.houseNumber == userHistoryNumber) {
                        records.add(record)
                    }
                }
                _monitorData.value = records //hanya utk data yang sesuai dengan houseNumber
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Firebase","Gagal mengambil data", error.toException())
            }
        })
    }

    fun getHistoryByHouseNumber(houseNumber: String): LiveData<List<MonitorRecord>> {
        val historyLiveData = MutableLiveData<List<MonitorRecord>>()
        val monitorRef = database.getReference("monitor")

        monitorRef.orderByChild("houseNumber").equalTo(houseNumber)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val historyList = mutableListOf<MonitorRecord>()
                    for (data in snapshot.children) {
                        val record = data.getValue(MonitorRecord::class.java)
                        if (record != null) {
                            historyList.add(record)
                        }
                    }
                    historyLiveData.value = historyList
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("Firebase", "Error fetching data: ${error.message}")
                }
            })

        return historyLiveData
    }


    private fun getCurrentTimestampFormatted(): String {
        val sdf = java.text.SimpleDateFormat("yyyy-MM-dd 'waktu' HH:mm", java.util.Locale.getDefault())
        return sdf.format(java.util.Date())
    }

}