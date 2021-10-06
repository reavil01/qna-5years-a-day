package com.yearsaday.qna.entity

import javax.persistence.*

@Entity
data class Answer(
    @Id
    @GeneratedValue
    @Column
    val id: Int,

    val answer: String,

    @ManyToOne(fetch = FetchType.LAZY)
    val question: Question,
) : BaseTime()