package com.yearsaday.qna.service

import com.yearsaday.qna.spring.entity.QuestionEntity
import com.yearsaday.qna.message.AnswerRequest
import com.yearsaday.qna.repository.AnswerDataService
import com.yearsaday.qna.spring.repository.AnswerRepository
import com.yearsaday.qna.spring.repository.QuestionRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDateTime

@SpringBootTest
class AnswerDataServiceTest {
    @Autowired
    private lateinit var service: AnswerDataService

    @Autowired
    private lateinit var repository: AnswerRepository

    @Autowired
    private lateinit var questionRepository: QuestionRepository

    val ANSWER = "답변1"
    lateinit var QUESTION: QuestionEntity

    @BeforeEach
    fun cleanUp() {
        repository.deleteAll()
        questionRepository.deleteAll()

        val question = QuestionEntity(
            0,
            "질문1",
            LocalDateTime.now().monthValue,
            LocalDateTime.now().dayOfMonth
        )
        QUESTION = questionRepository.save(question)
    }

    @Test
    fun saveAnswerTest() {
        // given
        val request = makeAnswerRequest()

        // when
        val result = service.save(request)

        // then
        assertThat(repository.findAll().size).isEqualTo(1)
        assertThat(result.id).isGreaterThan(0)
        assertThat(result.answer).isEqualTo(request.answer)
//        assertThat(result.question.id).isEqualTo(QUESTION.id)
    }

    @Test
    fun findAnswerTest() {
        // given
        val request = makeAnswerRequest()
        val saved = service.save(request)
        assertThat(repository.findAll().size).isEqualTo(1)

        // when
        val result = service.select(saved.id)

        // then
        assertThat(result?.id).isEqualTo(saved.id)
        assertThat(result?.answer).isEqualTo(request.answer)
//        assertThat(result.question.id).isEqualTo(QUESTION.id)
    }

    @Test
    fun updateAnswerTest() {
        // given
        val request = makeAnswerRequest()
        val saved = service.save(request)
        assertThat(repository.findAll().size).isEqualTo(1)

        val updateAnswer = "답변2"
        val updateSentence = "질문2"
        val question = QuestionEntity(
            0,
            updateSentence,
            LocalDateTime.now().monthValue,
            LocalDateTime.now().dayOfMonth
        )
        val updateQuestion = questionRepository.save(question)
        val updateRequest = AnswerRequest(updateAnswer, updateQuestion)

        // when
        val result = service.update(saved.id, updateRequest)

        // then
        assertThat(repository.findAll().size).isEqualTo(1)
        assertThat(result.answer).isEqualTo(updateAnswer)
//        assertThat(result.question.id).isEqualTo(updateQuestion.id)
//        assertThat(result.question.sentence).isEqualTo(updateSentence)
    }

    @Test
    fun deleteAnswerTest() {
        // given
        val request = makeAnswerRequest()
        val saved = service.save(request)
        assertThat(repository.findAll().size).isEqualTo(1)

        // when
        service.delete(saved.id)

        // then
        assertThat(repository.findAll().size).isEqualTo(0)
    }

    private fun makeAnswerRequest(): AnswerRequest {
        return AnswerRequest(ANSWER, QUESTION)
    }

}