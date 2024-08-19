package com.baobab.pp.domain.letter.domain.entity

import com.baobab.pp.domain.user.domain.entity.UserEntity
import com.baobab.pp.global.common.domain.entity.BaseTimeEntity
import jakarta.persistence.*

@Entity
@Table(name = "letters")
class LetterEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "title", nullable = false)
    var title: String,

    @Column(name = "content", nullable = false)
    var content: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: UserEntity
): BaseTimeEntity()