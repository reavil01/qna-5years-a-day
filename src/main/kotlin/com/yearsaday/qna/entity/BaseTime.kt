package com.yearsaday.qna.entity

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime
import javax.persistence.MappedSuperclass
import javax.persistence.PrePersist
import javax.persistence.PreUpdate

@MappedSuperclass
abstract class BaseTime {
    @JsonProperty("created_time") var createdTime = LocalDateTime.now()
    @JsonProperty("updated_time") var updatedTime = LocalDateTime.now()

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