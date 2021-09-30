package com.yearsaday.qna.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class Question(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Int,

    val sentence: String,
)