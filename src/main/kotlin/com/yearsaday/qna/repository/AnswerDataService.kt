package com.yearsaday.qna.repository

import com.yearsaday.qna.message.AnswerRequest
import com.yearsaday.qna.message.AnswerResponse

interface AnswerDataService {

    fun create(request: AnswerRequest): AnswerResponse

    fun delete(id: Int)

    fun update(id: Int, request: AnswerRequest): AnswerResponse

    fun select(id: Int): AnswerResponse?

    fun selectAll(): List<AnswerResponse>

    fun getTodayAnswer(questionId: Int): AnswerResponse?

}