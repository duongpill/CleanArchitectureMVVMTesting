package com.duongnh.catastrophic.presentation.screen.cat

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.Bitmap
import com.duongnh.catastrophic.domain.model.Cat

@Composable
fun CatGalleryRoute(viewModel: CatGalleryViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    CatGalleryRoute(
        uiState = uiState,
        onPhotoTapped = { viewModel.onPhotoTapped(it) },
        onClosePhoto = { viewModel.onClosePhoto() },
        loadMore = { viewModel.loadMoreItems() },
        updateCat = { id, bitmapImg ->
            viewModel.updateCat(id, bitmapImg)
        }
    )
}

@Composable
fun CatGalleryRoute(
    uiState: CatUIState,
    onPhotoTapped: (Cat) -> Unit,
    onClosePhoto: () -> Unit,
    loadMore: () -> Unit,
    updateCat: (String, Bitmap) -> Unit
) {
    CatGalleryScreen(
        uiState = uiState,
        onPhotoTapped = onPhotoTapped,
        onClosePhoto = onClosePhoto,
        loadMore = loadMore,
        updateCat = updateCat
    )
}