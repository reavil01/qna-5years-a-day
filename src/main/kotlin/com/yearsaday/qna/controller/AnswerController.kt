package com.yearsaday.qna.controller

import com.yearsaday.qna.message.AnswerRequest
import com.yearsaday.qna.message.AnswerResponse
import com.yearsaday.qna.repository.AnswerDataService

class AnswerController(
    private val answerDataService: AnswerDataService
) {

    // prevent saving the duplication answer of same year
    fun save(request: AnswerRequest): AnswerResponse {
        val todayEntity = answerDataService.getTodayAnswer(request.question.id)

        return when(val savedId = todayEntity?.id ?: 0) {
            0 -> answerDataService.create(request)
            else -> update(savedId, request)
        }
    }

    fun delete(id: Int) {
        return answerDataService.delete(id)
    }

    fun update(id: Int, request: AnswerRequest): AnswerResponse {
        return answerDataService.update(id, request)
    }

    fun select(id: Int): AnswerResponse? {
        return answerDataService.select(id)
    }

    fun selectAll(): List<AnswerResponse> {
        return answerDataService.selectAll()
    }

    fun getTodayAnswer(id: Int): AnswerResponse? {
        return answerDataService.getTodayAnswer(id)
    }

}