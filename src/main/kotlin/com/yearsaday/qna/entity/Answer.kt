package com.yearsaday.qna.entity

import java.time.LocalDateTime

data class Answer (

    val id: Int,

    val answer: String,

    val questionId: Int,

    val createTime: LocalDateTime,

    val updateTime: LocalDateTime,

)