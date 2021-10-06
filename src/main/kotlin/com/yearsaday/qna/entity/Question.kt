package com.yearsaday.qna.entity

import javax.persistence.*

@Entity
data class Question(
    @Id
    @GeneratedValue
    @Column
    val id: Int,

    val sentence: String,

    @OneToMany(mappedBy = "question", fetch = FetchType.LAZY)
    val answers: MutableList<Answer> = ArrayList()
)