package com.orryfrasetyo.rrqstoreapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.orryfrasetyo.rrqstoreapp.ui.theme.RRQStoreAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RRQStoreAppTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize(),
//                        .background(
//                            brush = Brush.verticalGradient(
//                                colors = listOf(Color(0xFF2D3E50), Color(0xFF2F576E)),
//                                startY = 100f,
//                                endY = 500f
//                            )
//                        ),
                    color = MaterialTheme.colorScheme.background
                ) {
                    RRQStoreApp()
                }
            }
        }
    }
}