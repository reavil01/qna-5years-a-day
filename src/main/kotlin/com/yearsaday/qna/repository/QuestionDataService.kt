package com.yearsaday.qna.repository

import com.yearsaday.qna.message.QuestionRequest
import com.yearsaday.qna.message.QuestionResponse

interface QuestionDataService {

    fun save(request: QuestionRequest): QuestionResponse

    fun delete(id: Int)

    fun update(id: Int, request: QuestionRequest): QuestionResponse

    fun select(id: Int): QuestionResponse

    fun selectByMonthAndDays(month: Int, day: Int): QuestionResponse

}