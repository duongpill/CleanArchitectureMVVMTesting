package com.duongnh.catastrophic.presentation.screen.cat

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil3.Bitmap
import com.duongnh.catastrophic.data.data_source.remote.dto.CatRequest
import com.duongnh.catastrophic.domain.MyResult
import com.duongnh.catastrophic.domain.model.Cat
import com.duongnh.catastrophic.domain.use_case.GetCatsUseCase
import com.duongnh.catastrophic.domain.use_case.UpdateCatUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatGalleryViewModel @Inject constructor(
    private val getCatsUseCase: GetCatsUseCase,
    private val updateCatUseCase: UpdateCatUseCase
) : ViewModel() {

    private val TAG = "CatGalleryViewModel"

    private val _uiState = MutableStateFlow(CatUIState(isLoading = true))

    val uiState: StateFlow<CatUIState> = _uiState

    private var currentPage = 1
    private var totalItemPage = 20

    init {
        fetchCats(totalItemPage, currentPage)
    }

    private fun setLoading(isLoading: Boolean) {
        _uiState.update { it.copy(isLoading = isLoading) }
    }

    private fun fetchCats(limit: Int, page: Int) {
        viewModelScope.launch {
            getCatsUseCase.invoke(CatRequest(limit, page, "jpg"), false)
                .onStart { setLoading(true) }
                .catch { ex ->
                    setLoading(false)
                    Log.e(TAG, ex.message.toString())
                }.collect { result ->
                    _uiState.update { state ->
                        when (result) {
                            is MyResult.Success -> {
                                state.copy(isLoading = false, cats = state.cats + result.data)
                            }
                            is MyResult.Error -> {
                                state.copy(isLoading = false, errorMessage = result.rawResponse)
                            }
                        }
                    }
                }
        }
    }

    fun updateCat(id: String, bitmapImg: Bitmap){
        viewModelScope.launch {
            updateCatUseCase.invoke(id, bitmapImg)
        }
    }

    fun loadMoreItems() {
        currentPage += currentPage
        totalItemPage += totalItemPage
        fetchCats(totalItemPage, currentPage)
    }

    fun onPhotoTapped(cat: Cat) {
        _uiState.update { it.copy(isPhotoOpen = true, cat = cat) }
    }

    fun onClosePhoto() {
        _uiState.update { it.copy(isPhotoOpen = false) }
    }

}