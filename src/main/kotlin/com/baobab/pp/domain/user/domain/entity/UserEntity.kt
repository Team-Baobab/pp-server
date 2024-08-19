package com.baobab.pp.domain.user.domain.entity

import com.baobab.pp.domain.user.domain.enums.UserRole
import com.baobab.pp.domain.user.domain.enums.UserStatus
import jakarta.persistence.*

@Entity
@Table(name = "users")
class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "username", nullable = false, unique = true)
    val username: String,

    @Column(name = "phone", nullable = false, unique = true)
    val phone: String,

    @Column(name = "password", nullable = false)
    val password: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    val role: UserRole = UserRole.USER,

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    val status: UserStatus = UserStatus.ACTIVE
)