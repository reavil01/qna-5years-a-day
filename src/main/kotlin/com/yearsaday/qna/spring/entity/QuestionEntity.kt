package com.yearsaday.qna.spring.entity

import javax.persistence.*

@Entity
data class QuestionEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    val id: Int,

    val sentence: String,

    val month: Int,

    val day: Int
)