package com.example.mowheel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mowheel.ui.theme.MowheelTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MowheelTheme {
                Scaffold(
                    content = { paddingValues ->
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(paddingValues),
                            contentAlignment = Alignment.Center
                        ) {
                            RoundButton(
                                onClick = { /* TODO: Add action here */ },
                                color = Color.Blue
                            )
                        }
                    }
                )
            }
        }
    }
}
@Composable
fun RoundButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    color: Color = Color.Blue
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .size(100.dp) // Set a fixed size for a perfect circle
            .clip(CircleShape), // Clip to a circle shape
        colors = ButtonDefaults.buttonColors(containerColor = color),
        contentPadding = PaddingValues(16.dp) // Adjust padding as needed
    ) {
        Text("Zoom")
    }
}

@Preview(showBackground = true)
@Composable
fun RoundButtonPreview() {
    MowheelTheme {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            RoundButton(
                onClick = { /* TODO: Add action here */ },
                color = Color.Blue
            )
        }
    }
}

