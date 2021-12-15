package com.yearsaday.qna.entity

import java.time.LocalDateTime
import javax.persistence.*

@Entity
data class Answer(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    val id: Int,

    val answer: String,

    @ManyToOne(fetch = FetchType.LAZY)
    val question: Question,

    val year: Int = LocalDateTime.now().year,
) : BaseTime()