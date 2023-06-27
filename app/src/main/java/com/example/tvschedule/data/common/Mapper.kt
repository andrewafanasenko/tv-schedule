package com.example.tvschedule.data.common

interface Mapper<in I, out O> {

    fun map(input: I): O

    fun mapList(input: List<I>) = input.map(::map)
}
