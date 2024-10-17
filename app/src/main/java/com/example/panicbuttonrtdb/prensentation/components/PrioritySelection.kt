package com.example.panicbuttonrtdb.prensentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun PrioritySelection(
    selectedPriority: String,
    onPrioritySelected: (String) -> Unit
) {
    Column {
        Text(text = "Pilih Prioritas", style = MaterialTheme.typography.bodyMedium)

        Spacer(modifier = Modifier.height(8.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            // Button untuk prioritas "Darurat"
            Button(
                onClick = { onPrioritySelected("darurat") },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (selectedPriority == "darurat") Color.Red else Color.Transparent,
                    contentColor = if (selectedPriority == "darurat") Color.White else Color.Gray
                ),
                border = BorderStroke(1.dp, Color.Red),
                modifier = Modifier.weight(1f)
            ) {
                Text("Darurat")
            }

            // Button untuk prioritas "Penting"
            Button(
                onClick = { onPrioritySelected("penting") },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (selectedPriority == "penting") Color.Yellow else Color.Transparent,
                    contentColor = if (selectedPriority == "penting") Color.White else Color.Gray
                ),
                border = BorderStroke(1.dp, Color.Yellow),
                modifier = Modifier.weight(1f)
            ) {
                Text("Penting")
            }

            // Button untuk prioritas "Biasa"
            Button(
                onClick = { onPrioritySelected("biasa") },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (selectedPriority == "biasa") Color.Green else Color.Transparent,
                    contentColor = if (selectedPriority == "biasa") Color.White else Color.Gray
                ),
                border = BorderStroke(1.dp, Color.Green),
                modifier = Modifier.weight(1f)
            ) {
                Text("Biasa")
            }
        }
    }
}