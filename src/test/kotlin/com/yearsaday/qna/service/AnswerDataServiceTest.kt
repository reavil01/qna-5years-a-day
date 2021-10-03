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
import javax.transaction.Transactional

@SpringBootTest
class AnswerDataServiceTest {
    @Autowired
    private lateinit var service: AnswerDataService

    @Autowired
    private lateinit var repository: AnswerRepository

    @Autowired
    private lateinit var questionRepository: QuestionRepository

    val ANSWER = "답변1"
    lateinit var QUESTION: Question

    @BeforeEach
    fun cleanUp() {
        repository.deleteAll()
        questionRepository.deleteAll()

        val question = Question(0, "질문1")
        QUESTION = questionRepository.save(question)
    }

    @Test
    @Transactional
    fun saveAnswerTest() {
        // given
        val request = makeAnswerRequest()

        // when
        val result = service.save(request)

        // then
        assertThat(repository.findAll().size).isEqualTo(1)
        assertThat(result.id).isGreaterThan(0)
        assertThat(result.answer).isEqualTo(request.answer)
        assertThat(result.question.answers.size).isEqualTo(1)
    }

    @Test
    @Transactional
    fun findAnswerTest() {
        // given
        val request = makeAnswerRequest()
        val saved = service.save(request)
        assertThat(repository.findAll().size).isEqualTo(1)

        // when
        val result = service.findById(saved.id)

        // then
        assertThat(result.id).isEqualTo(saved.id)
        assertThat(result.answer).isEqualTo(request.answer)
        assertThat(result.question.answers.size).isEqualTo(1)
    }

    @Test
    @Transactional
    fun updateAnswerTest() {
        // given
        val request = makeAnswerRequest()
        val saved = service.save(request)
        assertThat(repository.findAll().size).isEqualTo(1)

        val updateAnswer = "답변2"

        val question = Question(0, "질문2")
        val savedQuestion = questionRepository.save(question)
        val updateRequest = AnswerUpdateRequest(updateAnswer, savedQuestion)

        val beforeQuestionId = saved.question.id
        val beforeQuestion = questionRepository.findById(beforeQuestionId).orElseThrow()

        // when
        val result = service.update(saved.id, updateRequest)

        // then
        assertThat(repository.findAll().size).isEqualTo(1)
        assertThat(result.answer).isEqualTo(updateAnswer)
        assertThat(result.question.sentence).isEqualTo(savedQuestion.sentence)
        assertThat(beforeQuestion.answers.size).isEqualTo(0)
        assertThat(savedQuestion.answers.size).isEqualTo(1)
    }

    @Test
    @Transactional
    fun deleteAnswerTest() {
        // given
        val request = makeAnswerRequest()
        val saved = service.save(request)
        assertThat(repository.findAll().size).isEqualTo(1)

        // when
        service.delete(saved.id)

        // then
        assertThat(repository.findAll().size).isEqualTo(0)
        assertThat(QUESTION.answers.size).isEqualTo(0)
    }

    private fun makeAnswerRequest(): AnswerCreateRequest {
        return AnswerCreateRequest(ANSWER, QUESTION)
    }

}