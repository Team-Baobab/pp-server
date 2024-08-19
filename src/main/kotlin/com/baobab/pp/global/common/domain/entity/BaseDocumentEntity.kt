package com.baobab.pp.global.common.domain.entity

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.mongodb.core.mapping.Field
import org.springframework.data.mongodb.core.mapping.FieldType
import java.time.LocalDateTime

abstract class BaseDocumentEntity(
    @Id
    @Field("id", targetType = FieldType.OBJECT_ID)
    val id: String? = null,

    @Field("created_at", targetType = FieldType.DATE_TIME)
    @CreatedDate
    var createdAt: LocalDateTime? = null,

    @Field("updated_at", targetType = FieldType.DATE_TIME)
    @LastModifiedDate
    var updatedAt: LocalDateTime? = null
)