package com.yearsaday.qna.message

import com.yearsaday.qna.spring.entity.QuestionEntity

data class AnswerRequest(
    val answer: String,
    val question: QuestionEntity,
)
