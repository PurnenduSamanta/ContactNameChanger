package com.purnendu.contactnamechanger.screen.dashboard.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun SingleDashBoardItem(
    modifier: Modifier = Modifier,
    alarmName: String,
    phNo:String,
    startingTime: String,
    endingTime: String,
    onDeleteClick: () -> Unit
) {

    Card(modifier = modifier.fillMaxWidth())
    {

        Row(modifier=Modifier.fillMaxWidth().padding(5.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween)
        {
            Column(modifier = Modifier)
            {

                Text(text = alarmName, color = Color.Black)

                Spacer(modifier = Modifier.height(5.dp))

                Text(text = phNo, color = Color.Black)

                Spacer(modifier = Modifier.height(5.dp))

                Text(text = "Starting Time: $startingTime")

                Spacer(modifier = Modifier.height(5.dp))

                Text(text = "Ending Time: $endingTime")

            }


            Icon(modifier = Modifier.clickable { onDeleteClick() }, imageVector = Icons.Default.Delete, tint = Color.Red, contentDescription = "deleteIcon")

        }

    }

}