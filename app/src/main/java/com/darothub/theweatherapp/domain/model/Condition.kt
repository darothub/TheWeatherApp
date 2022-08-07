package com.darothub.theweatherapp.com.darothub.theweatherapp.domain.model

data class Condition(
    val currentId: Long,
    val text: String,
    val icon: String,
    val code: Long
)
