package dev.anaelle.test

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import dev.anaelle.test.ui.theme.TestappTheme
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestappTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CatImageViewer(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun CatImageViewer(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val catImages = remember {
        (1..33).map { id ->
            val resourceName = "cat$id"
            context.resources.getIdentifier(resourceName, "drawable", context.packageName)
        }
    }
    var currentImageIndex by remember { mutableStateOf(Random.nextInt(catImages.size)) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
            .clickable {
                currentImageIndex = Random.nextInt(catImages.size)
            }
    ) {
        Image(
            painter = painterResource(id = catImages[currentImageIndex]),
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CatImageViewerPreview() {
    TestappTheme {
        CatImageViewer()
    }
}