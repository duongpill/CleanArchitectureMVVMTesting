package com.duongnh.catastrophic.domain.model

data class Cat(val id: String, val url: String, val width: Int, val height: Int) {
    constructor(): this("", "", 0, 0)
}