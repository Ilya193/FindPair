package ru.kraz.findpair.domain

interface Mapper<T, R> {
    fun map(data: T): R
}