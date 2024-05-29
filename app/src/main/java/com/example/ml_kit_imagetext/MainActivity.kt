package com.example.ml_kit_imagetext

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import coil.compose.rememberAsyncImagePainter
import com.example.ml_kit_imagetext.ui.theme.ML_Kit_ImageTextTheme
import com.google.mlkit.vision.common.InputImage
import java.io.IOException
import kotlin.coroutines.jvm.internal.CompletedContinuation.context

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ML_Kit_ImageTextTheme {
                MainScreen()

            }
        }
    }
}

@Composable
fun MainScreen() {
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        Spacer(modifier = Modifier.height(20.dp))


        var imageUri by remember {
            mutableStateOf<Uri?>(null)
        }

        var imageUris = remember {
            mutableStateListOf<Uri>()
        }

        // 한 장 업로드
        val pickMedia =
            rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
                imageUri = uri
            }

        // 여러 장 업로드 ( 단, 2장 제한 )
        var pickMultipleMedia =
            rememberLauncherForActivityResult(ActivityResultContracts.PickMultipleVisualMedia(2)) { uris ->
                imageUris.addAll(uris)

            }
        if (imageUri == null) {
            Text(
                text = "대표사진을 등록해주세요",
                fontSize = 18.sp,
                modifier = Modifier.padding(16.dp)
            )
        } else {
            Image(
                painter = rememberAsyncImagePainter(imageUri),
                contentDescription = "seol",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(300.dp, 300.dp)
                    .padding(0.dp, 0.dp, 0.dp, 10.dp)
                    .clip(RoundedCornerShape(50.dp))
                    .border(
                        width = 5.dp,
                        color = Color.Black
                    )
            )
        }
        Button(onClick = {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))


        }) {
            Text(text = "사진 변경")
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ML_Kit_ImageTextTheme {
        MainScreen()
    }
}