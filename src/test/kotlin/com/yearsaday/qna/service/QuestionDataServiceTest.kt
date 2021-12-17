package com.yearsaday.qna.service

import com.yearsaday.qna.message.QuestionRequest
import com.yearsaday.qna.repository.QuestionDataService
import com.yearsaday.qna.spring.repository.QuestionRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDateTime

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
        val request = makeQuestionRequest()

        // when
        val result = service.save(request)

        // then
        assertThat(repository.findAll().size).isEqualTo(1)
        assertThat(result.id).isGreaterThan(0)
        assertThat(result.sentence).isEqualTo(SENTENCE)
    }

    @Test
    fun findQuestionTest() {
        // given
        val request = makeQuestionRequest()
        val saved = service.save(request)
        assertThat(repository.findAll().size).isEqualTo(1)

        // when
        val result = service.select(saved.id)

        // then
        assertThat(result.id).isEqualTo(saved.id)
        assertThat(result.sentence).isEqualTo(request.sentence)
    }

    @Test
    fun updateQuestionTest() {
        // given
        val request = makeQuestionRequest()
        val saved = service.save(request)
        assertThat(repository.findAll().size).isEqualTo(1)
        val updateSentence = "질문2"
        val updateRequest = QuestionRequest(
            updateSentence,
            LocalDateTime.now().monthValue,
            LocalDateTime.now().dayOfMonth
        )

        // when
        val result = service.update(saved.id, updateRequest)

        // then
        assertThat(repository.findAll().size).isEqualTo(1)
        assertThat(result.sentence).isEqualTo(updateSentence)
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

    private fun makeQuestionRequest(): QuestionRequest {
        return QuestionRequest(
            SENTENCE,
            LocalDateTime.now().monthValue,
            LocalDateTime.now().dayOfMonth
        )
    }
}