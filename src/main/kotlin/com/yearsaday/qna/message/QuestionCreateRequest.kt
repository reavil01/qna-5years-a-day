package com.yearsaday.qna.message

data class QuestionCreateRequest(
    val sentence: String,
    val month: Int,
    val day: Int,
)
