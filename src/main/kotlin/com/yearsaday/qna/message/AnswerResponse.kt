package com.yearsaday.qna.message

import com.yearsaday.qna.entity.Question

data class AnswerResponse(
    val id: Int,
    val answer: String,
    val question: Question,
)
