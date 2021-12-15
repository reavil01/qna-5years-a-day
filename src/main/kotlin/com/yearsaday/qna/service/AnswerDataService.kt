package com.yearsaday.qna.service

import com.yearsaday.qna.entity.Answer
import com.yearsaday.qna.message.AnswerCreateRequest
import com.yearsaday.qna.message.AnswerResponse
import com.yearsaday.qna.message.AnswerUpdateRequest
import com.yearsaday.qna.repository.AnswerRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*

@Service
class AnswerDataService(
    val repository: AnswerRepository
) {

    fun findByYearAndQuestionId(year: Int, questionId: Int): Optional<AnswerResponse> {
        val entity = repository.findByYearAndQuestionId(year, questionId)
        val result =
            if(entity.isPresent) toAnswerResponse(entity.get())
            else null
        return Optional.ofNullable(result)
    }

    fun findAll(): List<AnswerResponse> {
        val answerList = repository.findAll()
        val ans = answerList.map { toAnswerResponse(it) }

        return ans
    }

    fun save(request: AnswerCreateRequest): AnswerResponse {
        val year = LocalDateTime.now().year
        val todayEntity = repository.findByYearAndQuestionId(year, request.question.id)

        val entity = if(todayEntity.isPresent) {
            val saved = todayEntity.get()
            Answer(saved.id, request.answer, request.question)
        } else {
            Answer(0, request.answer, request.question)
        }

        val result = repository.save(entity)

        return toAnswerResponse(result)
    }

    fun findById(id: Int): AnswerResponse {
        val entity = repository.findById(id).orElseThrow()

        return toAnswerResponse(entity)
    }

    fun update(id: Int, request: AnswerUpdateRequest): AnswerResponse {
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
