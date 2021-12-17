package com.yearsaday.qna.repository

import com.yearsaday.qna.message.AnswerRequest
import com.yearsaday.qna.message.AnswerResponse
import java.time.Year

interface AnswerDataService {

    fun save(request: AnswerRequest): AnswerResponse

    fun delete(id: Int)

    fun update(id: Int, request: AnswerRequest): AnswerResponse

    fun select(id: Int): AnswerResponse?

    fun selectAll(): List<AnswerResponse>

    fun selectByYearAndQuestionId(year: Int, questionId: Int): AnswerResponse?

    fun preventDuplicationSave(request: AnswerRequest): AnswerResponse

}