package com.duongnh.catastrophic.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.duongnh.catastrophic.navigation.MyNavGraph
import com.duongnh.catastrophic.ui.theme.CATastrophicTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CATastrophicTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    MyNavGraph()
                }
            }
        }
    }
}