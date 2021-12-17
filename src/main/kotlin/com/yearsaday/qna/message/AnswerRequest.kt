package com.yearsaday.qna.message

import com.yearsaday.qna.entity.Question

data class AnswerRequest(
    val answer: String,
    val question: Question,
)
