package com.yearsaday.qna.service

import com.yearsaday.qna.entity.Question
import com.yearsaday.qna.message.AnswerCreateRequest
import com.yearsaday.qna.message.AnswerUpdateRequest
import com.yearsaday.qna.repository.AnswerRepository
import com.yearsaday.qna.repository.QuestionRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.event.annotation.BeforeTestClass

@SpringBootTest
class AnswerDataServiceTest {
    @Autowired
    private lateinit var service: AnswerDataService

    @Autowired
    private lateinit var repository: AnswerRepository

    @Autowired
    private lateinit var questionRepository: QuestionRepository

    val ANSWER = "답변1"
    val QUESTION by lazy {
        val question = Question(0, "질문1")
        questionRepository.save(question)
    }

    @BeforeEach
    fun cleanUp() {
        repository.deleteAll()
    }

    @Test
    fun saveQuestionTest() {
        // given
        val request = makeAnswerRequest()

        // when
        val result = service.save(request)

        // then
        assertThat(repository.findAll().size).isEqualTo(1)
        assertThat(result.id).isGreaterThan(0)
        assertThat(result.answer).isEqualTo(request.answer)
    }

    @Test
    fun findQuestionTest() {
        // given
        val request = makeAnswerRequest()
        val saved = service.save(request)
        assertThat(repository.findAll().size).isEqualTo(1)

        // when
        val result = service.findById(saved.id)

        // then
        assertThat(result.id).isEqualTo(saved.id)
        assertThat(result.answer).isEqualTo(request.answer)
    }

    @Test
    fun updateQuestionTest() {
        // given
        val request = makeAnswerRequest()
        val saved = service.save(request)
        assertThat(repository.findAll().size).isEqualTo(1)
        val updateAnswer = "답변2"
        val updateRequest = AnswerUpdateRequest(updateAnswer, QUESTION)

        // when
        val result = service.update(saved.id, updateRequest)

        // then
        assertThat(repository.findAll().size).isEqualTo(1)
        assertThat(result.answer).isEqualTo(updateAnswer)
        val updated = repository.findById(saved.id).orElseThrow()
        assertThat(updated.answer).isEqualTo(updateAnswer)
    }

    @Test
    fun deleteQuestionTest() {
        // given
        val request = makeAnswerRequest()
        val saved = service.save(request)
        assertThat(repository.findAll().size).isEqualTo(1)

        // when
        service.delete(saved.id)

        // then
        assertThat(repository.findAll().size).isEqualTo(0)
    }

    private fun makeAnswerRequest(): AnswerCreateRequest {
        return AnswerCreateRequest(ANSWER, QUESTION)
    }

}