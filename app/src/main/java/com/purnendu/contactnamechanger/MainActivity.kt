package com.purnendu.contactnamechanger

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.purnendu.contactnamechanger.screen.Contacts
import com.purnendu.contactnamechanger.ui.theme.ContactNameChangerTheme
import com.purnendu.contactnamechanger.utils.AlarmManager
import com.purnendu.contactnamechanger.utils.getCustomTimeInMillis

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpAlarm(application)
        enableEdgeToEdge()
        setContent {
            ContactNameChangerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Contacts( modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }

    private fun setUpAlarm(context: Context) {
        val manager = AlarmManager(context)
        manager.schedule(200, getCustomTimeInMillis(13,10,0))
    }
}

