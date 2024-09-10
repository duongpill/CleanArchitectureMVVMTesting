package com.duongnh.catastrophic.presentation.cat

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.testTag
import coil3.compose.AsyncImage
import com.duongnh.catastrophic.domain.model.Cat
import com.duongnh.catastrophic.utils.TestTags

@Composable
fun CatPhotoScreen(
    modifier: Modifier = Modifier,
    onClosePhoto: () -> Unit,
    cat: Cat
) {
    Surface(
        modifier = modifier
            .testTag(TestTags.PHOTO_SECTION)
            .fillMaxSize()
            .background(Color.Black)
            .wrapContentSize(Alignment.Center)
            .pointerInput(Unit) {
                detectDragGestures { _, _ -> onClosePhoto() }
            }
    ) {
        AsyncImage(
            modifier = modifier.fillMaxWidth().aspectRatio(cat.width.toFloat() / cat.height.toFloat()),
            model = cat.url,
            contentDescription = null
        )
    }
}