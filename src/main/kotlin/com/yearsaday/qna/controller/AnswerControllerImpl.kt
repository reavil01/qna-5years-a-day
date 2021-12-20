package com.yearsaday.qna.controller

import com.yearsaday.qna.message.AnswerRequest
import com.yearsaday.qna.message.AnswerResponse
import com.yearsaday.qna.repository.AnswerDataService

class AnswerControllerImpl(
    private val answerDataService: AnswerDataService
): AnswerController {

    // prevent saving the duplication answer of same year
    override fun save(request: AnswerRequest): AnswerResponse {
        val todayEntity = answerDataService.getTodayAnswer(request.question.id)

        return when(val savedId = todayEntity?.id ?: 0) {
            0 -> answerDataService.create(request)
            else -> update(savedId, request)
        }
    }

    override fun delete(id: Int) {
        return answerDataService.delete(id)
    }

    override fun update(id: Int, request: AnswerRequest): AnswerResponse {
        return answerDataService.update(id, request)
    }

    override fun select(id: Int): AnswerResponse? {
        return answerDataService.select(id)
    }

    override fun selectAll(): List<AnswerResponse> {
        return answerDataService.selectAll()
    }

    override fun getTodayAnswer(id: Int): AnswerResponse? {
        return answerDataService.getTodayAnswer(id)
    }

}