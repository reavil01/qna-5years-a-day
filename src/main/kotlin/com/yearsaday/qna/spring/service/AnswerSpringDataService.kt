package com.yearsaday.qna.spring.service

import com.yearsaday.qna.repository.AnswerDataService
import com.yearsaday.qna.spring.entity.AnswerEntity
import com.yearsaday.qna.message.AnswerRequest
import com.yearsaday.qna.message.AnswerResponse
import com.yearsaday.qna.spring.repository.AnswerRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class AnswerSpringDataService(
    val repository: AnswerRepository
) : AnswerDataService {

    override fun create(request: AnswerRequest): AnswerResponse {
        val entity = AnswerEntity(0, request.answer, request.question)
        val result = repository.save(entity)

        return toAnswerResponse(result)
    }

    override fun delete(id: Int) {
        repository.deleteById(id)
    }

    override fun update(id: Int, request: AnswerRequest): AnswerResponse {
        val entity = repository.findById(id).orElseThrow()
        val update = AnswerEntity(entity.id, request.answer, request.question)
        val result = repository.save(update)

        return toAnswerResponse(result)
    }

    override fun select(id: Int): AnswerResponse {
        val entity = repository.findById(id).orElseThrow()

        return toAnswerResponse(entity)
    }

    override fun selectAllByQuestionId(questionId: Int): List<AnswerResponse> {
        val answerList = repository.findAllByQuestionId(questionId)

        return answerList.map { toAnswerResponse(it) }
    }

    override fun getTodayAnswer(questionId: Int): AnswerResponse? {
        val year = LocalDateTime.now().year
        val entity = repository.findByYearAndQuestionId(year, questionId)

        return entity?.let { toAnswerResponse(it) }
    }

    private fun toAnswerResponse(entity: AnswerEntity): AnswerResponse {
        return AnswerResponse(
            entity.id,
            entity.answer,
            entity.year,
            entity.createdTime,
            entity.updatedTime
        )
    }
}
