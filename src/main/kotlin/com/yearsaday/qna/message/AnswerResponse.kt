package com.yearsaday.qna.message

import java.time.LocalDateTime

data class AnswerResponse(
    val id: Int,
    val answer: String,
    val year: Int,
    val createTime: LocalDateTime,
    val updateTime: LocalDateTime,
)
