package com.yearsaday.qna.service

import com.yearsaday.qna.entity.Answer
import com.yearsaday.qna.entity.Question
import com.yearsaday.qna.message.QuestionCreateRequest
import com.yearsaday.qna.message.QuestionUpdateRequest
import com.yearsaday.qna.repository.AnswerRepository
import com.yearsaday.qna.repository.QuestionRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.lang.IllegalArgumentException
import javax.transaction.Transactional

@SpringBootTest
class QuestionDataServiceTest {
    @Autowired
    private lateinit var service: QuestionDataService

    @Autowired
    private lateinit var repository: QuestionRepository

    @Autowired
    private lateinit var answerRepository: AnswerRepository

    val SENTENCE = "질문1"

    @BeforeEach
    fun cleanUp() {
        answerRepository.deleteAll()
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
        val result = service.findById(saved.id)

        // then
        assertThat(result.id).isEqualTo(saved.id)
        assertThat(result.sentence).isEqualTo(request.sentence)
    }

    @Test
    @Transactional
    fun updateQuestionTest() {
        // given
        val request = makeQuestionRequest()
        val saved = service.save(request)
        assertThat(repository.findAll().size).isEqualTo(1)
        val savedQuestionEntity = repository.findById(saved.id).orElseThrow()
        val updateSentence = "질문2"
        val updateRequest = QuestionUpdateRequest(updateSentence)

        val answer = "답변1"
        val answerEntity = Answer(0, answer, savedQuestionEntity)
        val savedAnswer = answerRepository.save(answerEntity)
        savedQuestionEntity.answers.add(savedAnswer)

        // when
        val result = service.update(saved.id, updateRequest)

        // then
        assertThat(repository.findAll().size).isEqualTo(1)
        assertThat(result.sentence).isEqualTo(updateSentence)

        val updated = repository.findById(saved.id).orElseThrow()
        assertThat(updated.answers).isEqualTo(saved.answers)
        assertThat(updated.answers[0].answer).isEqualTo(answer)
        assertThat(updated.answers[0].question.sentence).isEqualTo(updateSentence)
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

    @Test
    @Transactional
    fun deleteQuestionWhenHasAnswer() {
        // given
        val question = makeQuestionRequest()
        val saved = service.save(question)
        assertThat(repository.findAll().size).isEqualTo(1)

        val questionEntity = repository.findById(saved.id).orElseThrow()
        val answerEntity = makeSavedAnswer(questionEntity)
        assertThat(questionEntity.answers.size).isEqualTo(1)

        // when, then
        assertThrows<IllegalArgumentException> { service.delete(saved.id) }
    }

    private fun makeQuestionRequest(): QuestionCreateRequest {
        return QuestionCreateRequest(SENTENCE)
    }

    private fun makeSavedAnswer(question: Question): Answer {
        val entity = Answer(0, "답변1", question)
        question.answers.add(entity)

        return answerRepository.save(entity)
    }
}