package com.yearsaday.qna.service

import com.yearsaday.qna.message.AnswerCreateRequest
import com.yearsaday.qna.message.AnswerUpdateRequest
import com.yearsaday.qna.repository.AnswerRepository
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class AnswerDataServiceTest {
    @Autowired
    private lateinit var service: AnswerDataService

    @Autowired
    private lateinit var repository: AnswerRepository

    val ANSWER = "답변1"

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
        Assertions.assertThat(repository.findAll().size).isEqualTo(1)
        Assertions.assertThat(result.id).isEqualTo(1)
        Assertions.assertThat(result.answer).isEqualTo(request.answer)
    }

    @Test
    fun findQuestionTest() {
        // given
        val request = makeAnswerRequest()
        val saved = service.save(request)
        Assertions.assertThat(repository.findAll().size).isEqualTo(1)

        // when
        val result = service.findById(saved.id)

        // then
        Assertions.assertThat(result.id).isEqualTo(saved.id)
        Assertions.assertThat(result.answer).isEqualTo(request.answer)
    }

    @Test
    fun updateQuestionTest() {
        // given
        val question = makeAnswerRequest()
        val saved = service.save(question)
        Assertions.assertThat(repository.findAll().size).isEqualTo(1)
        val answer = "답변2"
        val request = AnswerUpdateRequest(answer)

        // when
        service.update(saved.id, request)

        // then
        Assertions.assertThat(repository.findAll().size).isEqualTo(1)
        val updated = repository.findById(saved.id).orElseThrow()
        Assertions.assertThat(updated.answer).isEqualTo(answer)
    }

    @Test
    fun deleteQuestionTest() {
        // given
        val request = makeAnswerRequest()
        val saved = service.save(request)
        Assertions.assertThat(repository.findAll().size).isEqualTo(1)

        // when
        service.delete(saved.id)

        // then
        Assertions.assertThat(repository.findAll().size).isEqualTo(0)
    }

    private fun makeAnswerRequest(): AnswerCreateRequest {
        return AnswerCreateRequest(ANSWER)
    }

}