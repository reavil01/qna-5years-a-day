package com.yearsaday.qna.message

import com.yearsaday.qna.entity.Question
import java.time.LocalDateTime

data class AnswerResponseWithQuestion (
    val id: Int,
    val answer: String,
    val year: Int,
    val question: Question,
    val createTime: LocalDateTime,
    val updateTime: LocalDateTime,
)