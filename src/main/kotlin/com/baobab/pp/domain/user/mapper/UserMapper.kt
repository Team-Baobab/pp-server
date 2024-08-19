package com.baobab.pp.domain.user.mapper

import com.baobab.pp.domain.user.domain.entity.UserEntity
import com.baobab.pp.domain.user.dto.User
import com.baobab.pp.domain.user.error.UserError
import com.baobab.pp.domain.user.repository.UserRepository
import com.baobab.pp.global.common.mapper.Mapper
import com.baobab.pp.global.error.CustomException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class UserMapper(
    private val userRepository: UserRepository
) : Mapper<User, UserEntity> {
    override fun toDomain(entity: UserEntity) = User(
        id = entity.id!!,
        username = entity.username,
        phone = entity.phone,
        role = entity.role
    )

    override fun toEntity(domain: User) =
        userRepository.findByUsername(domain.username) ?: throw CustomException(UserError.USER_NOT_FOUND)
}