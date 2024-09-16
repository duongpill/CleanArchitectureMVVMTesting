package com.duongnh.catastrophic.presentation.screen.cat

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import coil3.Bitmap
import com.duongnh.catastrophic.domain.model.Cat
import com.duongnh.catastrophic.utils.TestTags
import com.duongnh.catastrophic.utils.Utils
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map

@Composable
fun CatGalleryScreen(
    modifier: Modifier = Modifier,
    uiState: CatUIState,
    onPhotoTapped: (Cat) -> Unit,
    onClosePhoto: () -> Unit,
    loadMore: () -> Unit,
    updateCat: (String, Bitmap) -> Unit
) {
    CatGalleryScreenWithList(
        modifier = modifier,
        uiState = uiState
    ) { contentPadding, contentModifier ->
        // Put it here to hold the grid state
        val gridState = rememberLazyGridState()
        AnimatedContent(targetState = uiState.isPhotoOpen, transitionSpec = {
            scaleIn(animationSpec = tween(150)) togetherWith
                    scaleOut(animationSpec = tween(150)) using
                    SizeTransform { initialSize, targetSize ->
                        if (targetState) {
                            keyframes {
                                // Expand horizontally first.
                                IntSize(targetSize.width, initialSize.height)
                                durationMillis = 300
                            }
                        } else {
                            keyframes {
                                // Shrink vertically first.
                                IntSize(initialSize.width, targetSize.height)
                                durationMillis = 300
                            }
                        }
                    }
        }, label = "") { isPhotoOpen ->
            if (isPhotoOpen) {
                BackHandler {
                    onClosePhoto()
                }
                CatPhotoScreen(
                    modifier = modifier,
                    onClosePhoto = onClosePhoto,
                    cat = uiState.cat
                )
            } else {
                CatList(
                    gridState = gridState,
                    cats = uiState.cats,
                    contentPadding = contentPadding,
                    isLoading = uiState.isLoading,
                    openPhoto = onPhotoTapped,
                    loadMore = loadMore,
                    updateCat = updateCat
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatGalleryScreenWithList(
    modifier: Modifier = Modifier,
    uiState: CatUIState,
    hasUsersContent: @Composable (
        contentPadding: PaddingValues,
        modifier: Modifier
    ) -> Unit
) {
    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(topAppBarState)

    Scaffold(modifier = modifier.testTag(TestTags.CAT_GALLERY_SCREEN)) { innerPadding ->
        val contentModifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
        LoadingContent(
            isLoading = uiState.cats.isEmpty() && uiState.isLoading, // Only show it when the list is empty
            loading = {
                ScreenLoading()
            },
            content = {
                if (uiState.cats.isEmpty()) {
                    Box(
                        contentModifier
                            .padding(innerPadding)
                            .fillMaxSize()
                            .wrapContentSize(Alignment.Center)
                    ) {
                        Text(
                            text = "No Cats Found!!",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                } else {
                    hasUsersContent(innerPadding, contentModifier)
                }
            }
        )
    }
}

@Composable
private fun LoadingContent(
    isLoading: Boolean,
    loading: @Composable () -> Unit,
    content: @Composable () -> Unit
) {
    if (isLoading) {
        loading()
    } else {
        content()
    }
}

@Composable
private fun CatList(
    gridState: LazyGridState,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    cats: List<Cat>,
    isLoading: Boolean,
    openPhoto: (cat: Cat) -> Unit,
    loadMore: () -> Unit,
    updateCat: (String, Bitmap) -> Unit
) {
    LazyVerticalGrid(
        modifier = Modifier
            .testTag(TestTags.CAT_LIST_SECTION)
            .padding(bottom = 15.dp),
        columns = GridCells.Fixed(3),
        contentPadding = contentPadding,
        state = gridState.also {
            it.ScrollEndCallback {
                loadMore()
            }
        }
    ) {
        items(items = cats, key = { it.id }) { cat ->
            CatCard(cat = cat, openPhoto = openPhoto, updateCat = updateCat)
        }
        if (isLoading) {
            item(span = { GridItemSpan(maxLineSpan) }) {
                LoadingMore()
            }
        }
    }
}

@Composable
inline fun LazyGridState.ScrollEndCallback(crossinline callback: () -> Unit) {
    val context = LocalContext.current
    LaunchedEffect(key1 = this) {
        snapshotFlow { layoutInfo }
            .filter { it.totalItemsCount > 0 }
            .map { it.totalItemsCount == (it.visibleItemsInfo.lastOrNull()?.index ?: -1).inc() }
            .distinctUntilChanged()
            .filter { it && Utils.isNetworkAvailable(context) }
            .collect {
                callback()
            }
    }
}

@Composable
private fun ScreenLoading() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    ) {
        CircularProgressIndicator(
            modifier = Modifier.width(40.dp),
            color = MaterialTheme.colorScheme.secondary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant,
        )
    }
}

@Composable
private fun LoadingMore() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        CircularProgressIndicator(
            modifier = Modifier.width(50.dp),
            color = MaterialTheme.colorScheme.secondary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant,
        )
    }
}