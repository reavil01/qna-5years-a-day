package com.yearsaday.qna.controller

import com.yearsaday.qna.message.AnswerRequest
import com.yearsaday.qna.message.AnswerResponse

interface AnswerController {

    fun save(request: AnswerRequest): AnswerResponse

    fun delete(id: Int)

    fun update(id: Int, request: AnswerRequest): AnswerResponse

    fun select(id: Int): AnswerResponse?

    fun selectAll(): List<AnswerResponse>

    fun getTodayAnswer(id: Int): AnswerResponse?

}