package dev.anaelle.test

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.tooling.preview.Preview
import coil3.compose.AsyncImage
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
    val haptic = LocalHapticFeedback.current

    // Load cat images from the GitHub repo
    val catUrls = remember {
        (1..33).map { id ->
            "https://raw.githubusercontent.com/anaelle-dev/dev.anaelle.test/main/assets/cats/cat$id.jpg"
        }
    }

    var currentIndex by rememberSaveable { mutableStateOf(Random.nextInt(catUrls.size)) }

    fun nextRandomIndex(): Int {
        if (catUrls.size <= 1) return 0
        var newIndex: Int
        do {
            newIndex = Random.nextInt(catUrls.size)
        } while (newIndex == currentIndex)
        return newIndex
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
            .clickable {
                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                currentIndex = nextRandomIndex()
            }
    ) {
        Crossfade(targetState = currentIndex, label = "Cat crossfade") { index ->
            AsyncImage(
                model = catUrls[index],
                contentDescription = "Random cat image",
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun CatImageViewerPreview() {
    TestappTheme {
        CatImageViewer()
    }
}
