package com.yearsaday.qna.entity

import java.time.LocalDateTime
import javax.persistence.MappedSuperclass
import javax.persistence.PrePersist
import javax.persistence.PreUpdate

@MappedSuperclass
abstract class BaseTime {
    var createdTime = LocalDateTime.now()
    var updatedTime = LocalDateTime.now()

    @PrePersist
    fun prePersist() {
        createdTime = LocalDateTime.now()
        updatedTime = LocalDateTime.now()
    }

    @PreUpdate
    fun preUpdate() {
        updatedTime = LocalDateTime.now()
    }
}