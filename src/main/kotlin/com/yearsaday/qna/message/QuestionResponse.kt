package com.yearsaday.qna.message

import com.yearsaday.qna.entity.Answer

data class QuestionResponse(
    val id: Int,
    val sentence: String,
    val answers: List<Answer>
)