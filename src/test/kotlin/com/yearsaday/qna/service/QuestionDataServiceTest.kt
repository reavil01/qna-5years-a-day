package com.yearsaday.qna.service

import com.yearsaday.qna.message.QuestionCreateRequest
import com.yearsaday.qna.message.QuestionUpdateRequest
import com.yearsaday.qna.repository.QuestionRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class QuestionDataServiceTest {
    @Autowired
    private lateinit var service: QuestionDataService

    @Autowired
    private lateinit var repository: QuestionRepository

    val SENTENCE = "질문1"

    @BeforeEach
    fun cleanUp() {
        repository.deleteAll()
    }

    @Test
    fun saveQuestionTest() {
        // given
        val question = makeQuestionRequest()

        // when
        val result = service.save(question)

        // then
        assertThat(repository.findAll().size).isEqualTo(1)
        assertThat(result.id).isEqualTo(1)
        assertThat(result.sentence).isEqualTo(SENTENCE)
    }

    @Test
    fun findQuestionTest() {
        // given
        val question = makeQuestionRequest()
        val saved = service.save(question)
        assertThat(repository.findAll().size).isEqualTo(1)

        // when
        val result = service.findById(saved.id)

        // then
        assertThat(result.id).isEqualTo(saved.id)
        assertThat(result.sentence).isEqualTo(question.sentence)
    }

    @Test
    fun updateQuestionTest() {
        // given
        val question = makeQuestionRequest()
        val saved = service.save(question)
        assertThat(repository.findAll().size).isEqualTo(1)
        val sentence = "질문2"
        val request = QuestionUpdateRequest(sentence)

        // when
        service.update(saved.id, request)

        // then
        assertThat(repository.findAll().size).isEqualTo(1)
        val updated = repository.findById(saved.id).orElseThrow()
        assertThat(updated.sentence).isEqualTo(sentence)
    }

    @Test
    fun deleteQuestionTest() {
        // given
        val question = makeQuestionRequest()
        val saved = service.save(question)
        assertThat(repository.findAll().size).isEqualTo(1)

        // when
        service.delete(saved.id)

        // then
        assertThat(repository.findAll().size).isEqualTo(0)
    }

    private fun makeQuestionRequest(): QuestionCreateRequest {
        return QuestionCreateRequest(SENTENCE)
    }
}