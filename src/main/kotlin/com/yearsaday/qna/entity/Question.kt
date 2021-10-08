package com.yearsaday.qna.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
data class Question(
    @Id
    @GeneratedValue
    @Column
    val id: Int,

    val sentence: String,
)