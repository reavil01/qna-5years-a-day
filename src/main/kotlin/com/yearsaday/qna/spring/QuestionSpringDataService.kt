package com.yearsaday.qna.spring

import com.yearsaday.qna.entity.Question
import com.yearsaday.qna.message.QuestionRequest
import com.yearsaday.qna.message.QuestionResponse
import com.yearsaday.qna.repository.QuestionDataService
import com.yearsaday.qna.spring.repository.QuestionRepository
import org.springframework.stereotype.Service

@Service
class QuestionSpringDataService(
    val repository: QuestionRepository
) : QuestionDataService {

    override fun save(request: QuestionRequest): QuestionResponse {
        val question = Question(0, request.sentence, request.month, request.day)
        val entity = repository.save(question)

        return toQuestionResponse(entity)
    }

    override fun delete(id: Int) {
        repository.deleteById(id)
    }

    override fun update(id: Int, request: QuestionRequest): QuestionResponse {
        val update = Question(id, request.sentence, request.month, request.day)
        val result = repository.save(update)

        return toQuestionResponse(result)
    }

    override fun select(id: Int): QuestionResponse {
        val entity = repository.findById(id).orElseThrow()

        return toQuestionResponse(entity)
    }

    override fun selectByMonthAndDays(month: Int, day: Int): QuestionResponse {
        val entity = repository.findByMonthAndDay(month, day)

        return toQuestionResponse(entity)
    }

    private fun toQuestionResponse(entity: Question): QuestionResponse {
        return QuestionResponse(entity.id, entity.sentence)
    }
}
