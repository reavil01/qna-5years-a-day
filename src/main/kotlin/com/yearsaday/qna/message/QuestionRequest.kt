package com.yearsaday.qna.message

data class QuestionRequest(
    val sentence: String,
    val month: Int,
    val day: Int,
)
