package com.purnendu.contactnamechanger.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun Contacts(
    modifier: Modifier = Modifier,
    viewModel: ContactListingViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton ={ FloatingActionButton(onClick = {  }) {

            Box(modifier = modifier.clip(CircleShape.copy(all = CornerSize(10.dp))).padding(5.dp))
            Icon( modifier = Modifier.size(30.dp), imageVector = Icons.Default.Add, tint = Color.White, contentDescription = "add")
        }},
        floatingActionButtonPosition = FabPosition.End
    )
    {


    }


}