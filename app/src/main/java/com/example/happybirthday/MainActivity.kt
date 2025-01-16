package com.example.happybirthday

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.happybirthday.ui.theme.HappyBirthdayTheme
import androidx.compose.foundation.Image


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HappyBirthdayTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    GreetingCardApp()
                }
            }
        }
    }
}

@Composable
fun GreetingCardApp() {
    // States for recipient, sender, background color, and card visibility
    var recipientName by remember { mutableStateOf("") }
    var senderName by remember { mutableStateOf("") }
    var selectedColor by remember { mutableStateOf(Color.LightGray) }
    var showCard by remember { mutableStateOf(false) }

    if (showCard) {
        // Show the full-screen greeting card if inputs are valid
        GreetingImage(
            message = "Happy Birthday, $recipientName!",
            from = senderName,
            backgroundColor = selectedColor
        )
    } else {
        // Show the input form
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Input for recipient's name
            TextField(
                value = recipientName,
                onValueChange = { newName -> recipientName = newName },
                label = { Text("Enter recipient's name") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Input for sender's name
            TextField(
                value = senderName,
                onValueChange = { newName -> senderName = newName },
                label = { Text("Enter your name") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Buttons for background color selection
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                listOf(
                    Color.LightGray to "Gray",
                    Color.Yellow to "Yellow",
                    Color.Cyan to "Cyan",
                    Color.Magenta to "Magenta"
                ).forEach { (color, name) ->
                    Button(
                        onClick = { selectedColor = color },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (selectedColor == color) color else Color.White
                        ),
                        modifier = Modifier
                            .padding(4.dp)
                            .clip(CircleShape)
                            .background(if (selectedColor == color) Color.Black.copy(alpha = 0.1f) else Color.Transparent)
                    ) {
                        Text(
                            text = name,
                            color = if (selectedColor == color) Color.White else Color.Black
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Submit button to display the card
            Button(
                onClick = {
                    // Validate inputs
                    if (recipientName.isNotEmpty() && senderName.isNotEmpty()) {
                        showCard = true // Show the card
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Create Card")
            }
        }
    }
}

@Composable
fun GreetingImage(
    message: String,
    from: String,
    backgroundColor: Color = Color.LightGray,
    imageAlpha: Float = 0.5F,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(backgroundColor) // Apply the dynamic background color
    ) {
        Image(
            painter = painterResource(id = R.drawable.androidparty),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            alpha = imageAlpha
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(16.dp)
        ) {
            Text(
                text = message,
                fontSize = 36.sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Text(
                text = "From: $from",
                fontSize = 20.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingCardAppPreview() {
    HappyBirthdayTheme {
        GreetingCardApp()
    }
}
