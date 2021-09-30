package com.yearsaday.qna.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class Answer(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Int,

    val answer: String,
//    val creatTime: LocalDateTime,
)
