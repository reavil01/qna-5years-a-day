package com.yearsaday.qna.controller

import com.yearsaday.qna.message.QuestionResponse
import com.yearsaday.qna.repository.QuestionDataService

class QuestionController(
    private val questionDataService: QuestionDataService
) {

    fun getTodayQuestion(): QuestionResponse {
        return questionDataService.getTodayQuestion()
    }

}