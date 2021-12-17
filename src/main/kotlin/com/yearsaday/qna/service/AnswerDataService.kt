package com.yearsaday.qna.service

import com.yearsaday.qna.entity.Answer
import com.yearsaday.qna.message.AnswerRequest
import com.yearsaday.qna.message.AnswerResponse
import com.yearsaday.qna.repository.AnswerRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class AnswerDataService(
    val repository: AnswerRepository
) {

    fun findByYearAndQuestionId(year: Int, questionId: Int): AnswerResponse? {
        val entity = repository.findByYearAndQuestionId(year, questionId)

        return entity?.let { toAnswerResponse(it) }
    }

    fun findAll(): List<AnswerResponse> {
        val answerList = repository.findAll()
        val ans = answerList.map { toAnswerResponse(it) }

        return ans
    }

    fun preventDuplicationSave(request: AnswerRequest): AnswerResponse {
        val year = LocalDateTime.now().year
        val todayEntity = repository.findByYearAndQuestionId(year, request.question.id)
        val savedId = todayEntity?.id ?: 0

        return when(savedId) {
            0 -> save(request)
            else -> update(savedId, request)
        }
    }

    fun save(request: AnswerRequest): AnswerResponse {
        val entity = Answer(0, request.answer, request.question)
        val result = repository.save(entity)

        return toAnswerResponse(result)
    }

    fun findById(id: Int): AnswerResponse {
        val entity = repository.findById(id).orElseThrow()

        return toAnswerResponse(entity)
    }

    fun update(id: Int, request: AnswerRequest): AnswerResponse {
        val entity = repository.findById(id).orElseThrow()
        val update = Answer(entity.id, request.answer, request.question)
        val result = repository.save(update)

        return toAnswerResponse(result)
    }

    fun delete(id: Int) {
        repository.deleteById(id)
    }

    private fun toAnswerResponse(entity: Answer): AnswerResponse {
        return AnswerResponse(
            entity.id,
            entity.answer,
            entity.year,
            entity.createdTime,
            entity.updatedTime
        )
    }
}
