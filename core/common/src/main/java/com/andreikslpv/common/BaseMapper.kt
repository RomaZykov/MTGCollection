package com.andreikslpv.common

interface BaseMapper<in A, out B> {

    fun map(type: A?): B
}