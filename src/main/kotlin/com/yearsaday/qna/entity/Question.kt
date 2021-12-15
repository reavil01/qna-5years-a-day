package com.yearsaday.qna.entity

import javax.persistence.*

@Entity
data class Question(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    val id: Int,

    val sentence: String,

    val month: Int,

    val day: Int
)