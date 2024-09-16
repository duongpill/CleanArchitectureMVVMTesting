package com.duongnh.catastrophic.presentation.screen.cat

import com.duongnh.catastrophic.domain.model.Cat

data class CatUIState(
    val cats: List<Cat> = emptyList(),
    val cat: Cat = Cat(),
    val isPhotoOpen: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String = ""
)