package com.yearsaday.qna.message

import com.yearsaday.qna.entity.Question
import java.time.LocalDateTime

data class AnswerResponse(
    val id: Int,
    val answer: String,
    val question: Question,
    val createTime: LocalDateTime,
    val updateTime: LocalDateTime,
)
