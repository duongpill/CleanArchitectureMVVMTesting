package com.duongnh.catastrophic.domain.use_case

import android.graphics.Bitmap
import com.duongnh.catastrophic.domain.repository.CatRepository

class UpdateCatUseCase(private val catRepository: CatRepository) {

    suspend operator fun invoke(id: String, bitmapImg: Bitmap){
        catRepository.updateCatLocalDB(id, bitmapImg)
    }

}