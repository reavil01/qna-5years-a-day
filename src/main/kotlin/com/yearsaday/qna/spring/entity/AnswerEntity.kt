package com.yearsaday.qna.spring.entity

import java.time.LocalDateTime
import javax.persistence.*

@Entity
data class AnswerEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    val id: Int,

    val answer: String,

    @ManyToOne(fetch = FetchType.LAZY)
    val question: QuestionEntity,

    val year: Int = LocalDateTime.now().year,
) : BaseTime()