package com.duongnh.catastrophic.presentation.cat

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.duongnh.catastrophic.domain.model.Cat

@Composable
fun CatGalleryRoute(viewModel: CatGalleryViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    CatGalleryRoute(
        uiState = uiState,
        onPhotoTapped = { viewModel.onPhotoTapped(it) },
        onClosePhoto = { viewModel.onClosePhoto() },
        loadMore = { viewModel.loadMoreItems() }
    )
}

@Composable
fun CatGalleryRoute(
    uiState: CatUIState,
    onPhotoTapped: (Cat) -> Unit,
    onClosePhoto: () -> Unit,
    loadMore: () -> Unit
) {
    CatGalleryScreen(
        uiState = uiState,
        onPhotoTapped = onPhotoTapped,
        onClosePhoto = onClosePhoto,
        loadMore = loadMore
    )
}