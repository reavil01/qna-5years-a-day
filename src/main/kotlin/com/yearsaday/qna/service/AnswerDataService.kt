package com.yearsaday.qna.service

import com.yearsaday.qna.entity.Answer
import com.yearsaday.qna.message.AnswerCreateRequest
import com.yearsaday.qna.message.AnswerResponse
import com.yearsaday.qna.message.AnswerUpdateRequest
import com.yearsaday.qna.repository.AnswerRepository
import org.springframework.stereotype.Service

@Service
class AnswerDataService(
    val repository: AnswerRepository
) {
    fun save(request: AnswerCreateRequest): AnswerResponse {
        val entity = Answer(0, request.answer)
        val saved = repository.save(entity)

        return toAnswerResponse(saved)
    }

    fun findById(id: Int): AnswerResponse {
        val entity = repository.findById(id).orElseThrow()

        return toAnswerResponse(entity)
    }

    fun update(id: Int, request: AnswerUpdateRequest): AnswerResponse {
        val entity = repository.findById(id).orElseThrow()
        val update = Answer(entity.id, request.answer)
        val result = repository.save(update)

        return toAnswerResponse(result)
    }

    fun delete(id: Int) {
        repository.deleteById(id)
    }


    private fun toAnswerResponse(entity: Answer): AnswerResponse {
        return AnswerResponse(entity.id, entity.answer)
    }
}
