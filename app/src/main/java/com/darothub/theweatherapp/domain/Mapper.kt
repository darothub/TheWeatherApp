package com.darothub.theweatherapp.com.darothub.theweatherapp.domain

interface Mapper<T, R> {
    fun toEntity(value: T): R
}