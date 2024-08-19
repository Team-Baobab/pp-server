package com.baobab.pp.global.common.mapper

interface Mapper<D, E> {
    fun toDomain(entity: E): D
    fun toEntity(domain: D): E
}