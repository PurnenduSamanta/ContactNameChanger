package com.purnendu.contactnamechanger

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.purnendu.contactnamechanger.screen.dashboard.DashBoard
import com.purnendu.contactnamechanger.ui.theme.ContactNameChangerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ContactNameChangerTheme {
                DashBoard()
            }
        }
    }
}

