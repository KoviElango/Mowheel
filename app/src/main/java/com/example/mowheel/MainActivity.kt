package com.example.mowheel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.mowheel.ui.theme.MowheelTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MowheelTheme {
                SearchMovie()
            }
        }
    }
}



/*@Preview(showBackground = true)
@Composable
fun RoundButtonPreview() {
    MowheelTheme {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            SearchMovie()
        }
    }
}*/
