package com.duongnh.catastrophic.presentation.screen.cat

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.vectorResource
import coil3.compose.SubcomposeAsyncImage
import com.duongnh.catastrophic.R
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
            .wrapContentSize(Alignment.Center)
            .pointerInput(Unit) {
                detectDragGestures { _, _ -> onClosePhoto() }
            }
    ) {
        SubcomposeAsyncImage(
            modifier = modifier
                .fillMaxWidth()
                .aspectRatio(cat.width.toFloat() / cat.height.toFloat()),
            contentScale = ContentScale.Crop,
            model = cat.url,
            loading = { ShimmerAnimation() },
            error = {
                if (cat.bitmapImg != null) {
                    Image(
                        modifier = modifier
                            .fillMaxWidth()
                            .aspectRatio(cat.width.toFloat() / cat.height.toFloat()),
                        bitmap = cat.bitmapImg.asImageBitmap(),
                        contentDescription = null
                    )
                } else {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.picture_notfound),
                        contentDescription = null
                    )
                }
            },
            contentDescription = null
        )
    }
}