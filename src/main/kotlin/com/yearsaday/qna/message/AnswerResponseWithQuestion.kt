package com.yearsaday.qna.message

import com.yearsaday.qna.spring.entity.QuestionEntity
import java.time.LocalDateTime

data class AnswerResponseWithQuestion (
    val id: Int,
    val answer: String,
    val year: Int,
    val question: QuestionEntity,
    val createTime: LocalDateTime,
    val updateTime: LocalDateTime,
)