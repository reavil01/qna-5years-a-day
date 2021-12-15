package com.yearsaday.qna.service

import com.yearsaday.qna.entity.Question
import com.yearsaday.qna.message.QuestionCreateRequest
import com.yearsaday.qna.message.QuestionResponse
import com.yearsaday.qna.message.QuestionUpdateRequest
import com.yearsaday.qna.repository.QuestionRepository
import org.springframework.stereotype.Service

@Service
class QuestionDataService(
    val repository: QuestionRepository
) {

    fun findByMonthAndDays(month: Int, day: Int): QuestionResponse {
        val entity = repository.findByMonthAndDay(month, day)

        return toQuestionResponse(entity)
    }

    fun save(request: QuestionCreateRequest): QuestionResponse {
        val question = Question(0, request.sentence, request.month, request.day)
        val entity = repository.save(question)

        return toQuestionResponse(entity)
    }

    fun findById(id: Int): QuestionResponse {
        val entity = repository.findById(id).orElseThrow()

        return toQuestionResponse(entity)
    }

    fun delete(id: Int) {
        repository.deleteById(id)
    }

    fun update(id: Int, request: QuestionUpdateRequest): QuestionResponse {
        val saved = repository.findById(id).orElseThrow()
        val update = Question(id, request.sentence, request.month, request.day)
        val result = repository.save(update)

        return toQuestionResponse(result)
    }

    private fun toQuestionResponse(entity: Question): QuestionResponse {
        return QuestionResponse(entity.id, entity.sentence)
    }
}
