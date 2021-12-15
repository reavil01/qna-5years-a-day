package com.yearsaday.qna.message

data class QuestionUpdateRequest(
    val sentence: String,
    val month: Int,
    val day: Int,
)