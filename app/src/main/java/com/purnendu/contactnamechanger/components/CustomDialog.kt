package com.purnendu.contactnamechanger.components


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun CustomPhDialog(
    title: String,
    modifiedName: String,
    alarmName: String,
    startingTime: String,
    endingTime: String,
    onAlarmNameChange: (String) -> Unit,
    onCrossIconClick: () -> Unit,
    onModifiedNameChange: (String) -> Unit,
    onSelectOfStartingTime: () -> Unit,
    onSelectOfEndingTime: () -> Unit,
    onSaveButtonClick: () -> Unit
) {

    Dialog(
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false
        ),
        onDismissRequest = { })
    {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.White)
                .padding(10.dp),
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = title,
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold
                )

                Icon(
                    modifier = Modifier.clickable { onCrossIconClick() },
                    imageVector = Icons.Default.Clear,
                    contentDescription = "clearIcon",
                    tint = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(text = "Alarm name") },
                    value = alarmName,
                    onValueChange = { onAlarmNameChange(it) }
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(text = "Enter name to be modified") },
                    value = modifiedName,
                    onValueChange = { onModifiedNameChange(it) }
                )

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Button(
                        modifier = Modifier.weight(0.45f),
                        onClick = { onSelectOfStartingTime() }) {
                        Text(text = startingTime.ifEmpty { "Starting time" })
                    }

                    Spacer(modifier = Modifier.fillMaxWidth(0.1f))

                    Button(
                        modifier = Modifier.weight(0.45f),
                        onClick = { onSelectOfEndingTime() }) {
                        Text(text = endingTime.ifEmpty { "Ending time" })
                    }

                }

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { onSaveButtonClick() }) {
                    Text(text = "Done")
                }

        }

    }

}