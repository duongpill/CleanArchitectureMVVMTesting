package com.duongnh.catastrophic.presentation.screen.cat

import android.graphics.Bitmap
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import coil3.request.ImageRequest
import coil3.request.allowHardware
import coil3.toBitmap
import com.duongnh.catastrophic.R
import com.duongnh.catastrophic.domain.model.Cat
import com.duongnh.catastrophic.utils.TestTags.PHOTO_ITEM_SECTION

@Composable
fun CatCard(
    modifier: Modifier = Modifier,
    cat: Cat,
    openPhoto: (Cat) -> Unit = {},
    updateCat: (String, Bitmap) -> Unit = { _, _ -> }
) {
    val configuration = LocalConfiguration.current
    val imageWidth = configuration.screenWidthDp.dp / 3
    val context = LocalContext.current

    Surface(
        modifier = modifier
            .testTag(PHOTO_ITEM_SECTION)
            .clickable(onClick = { openPhoto(cat) })
    ) {
        Column {
            SubcomposeAsyncImage(
                modifier = modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .size(imageWidth),
                contentScale = ContentScale.Crop,
                model = ImageRequest.Builder(context).data(cat.url).allowHardware(false)
                    .build(),
                loading = { ShimmerAnimation() },
                error = {
                    if (cat.bitmapImg != null) {
                        Image(
                            modifier = modifier
                                .fillMaxWidth()
                                .aspectRatio(1f)
                                .size(imageWidth),
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
                contentDescription = null,
                onSuccess = {
                    val bitmap = it.result.image.toBitmap()
                    updateCat(cat.id, bitmap)
                }
            )
        }
    }
}

@Preview
@Composable
fun CatCardPreview() {
    CatCard(
        cat = Cat(
            "1",
            "https://26.media.tumblr.com/tumblr_krvvyt91aU1qa9hjso1_1280.png",
            null,
            800,
            1000
        )
    )
}

@Composable
fun ShimmerAnimation() {
    val shimmerColorShades = listOf(
        Color.LightGray.copy(0.7f),
        Color.LightGray.copy(0.2f),
        Color.LightGray.copy(0.7f)
    )

    /*
    Create InfiniteTransition
    which holds child animation like [Transition]
    animations start running as soon as they enter
    the composition and do not stop unless they are removed
    */
    val transition = rememberInfiniteTransition(label = "")
    val translateAnim by transition.animateFloat(
        /*
        Specify animation positions,
        initial Values 0F means it starts from 0 position
        */
        initialValue = 0f,
        targetValue = 200f,
        animationSpec = infiniteRepeatable(
            /*
             Tween Animates between values over specified [durationMillis]
            */
            tween(durationMillis = 1500, easing = FastOutSlowInEasing),
            RepeatMode.Restart
        ), label = ""
    )

    /*
      Create a gradient using the list of colors
      Use Linear Gradient for animating in any direction according to requirement
      start=specifies the position to start with in cartesian like system Offset(10f,10f) means x(10,0) , y(0,10)
      end= Animate the end position to give the shimmer effect using the transition created above
    */
    val brush = Brush.linearGradient(
        colors = shimmerColorShades,
        start = Offset(10f, 10f),
        end = Offset(translateAnim, translateAnim)
    )

    ShimmerItem(brush = brush)
}


@Composable
fun ShimmerItem(
    brush: Brush
) {
    /*
      Column composable shaped like a rectangle,
      set the [background]'s [brush] with the
      brush receiving from [ShimmerAnimation]
      which will get animated.
      Add few more Composable to test
    */
    Column(modifier = Modifier.padding(5.dp)) {
        Spacer(
            modifier = Modifier
                .fillMaxSize()
                .background(brush = brush)
        )
    }
}