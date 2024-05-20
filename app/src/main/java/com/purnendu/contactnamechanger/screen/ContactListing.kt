package com.purnendu.contactnamechanger.screen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp

@Composable
fun Contacts(modifier: Modifier = Modifier,viewModel: ContactListingViewModel = androidx.lifecycle.viewmodel.compose.viewModel())
{

    val contact =   remember { mutableStateOf(viewModel.getContactDetailsFromPhoneNumber("1234"))}

    viewModel.updateContactNoteIfNumberExists("1234","joy")

    contact.value?.let { Text(text = it.name, color = Color.Green, fontSize = 30.sp) }

}