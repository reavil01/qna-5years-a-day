package com.yearsaday.qna.repository

import com.yearsaday.qna.spring.entity.AnswerEntity
import com.yearsaday.qna.spring.entity.QuestionEntity
import com.yearsaday.qna.spring.repository.AnswerRepository
import com.yearsaday.qna.spring.repository.QuestionRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.web.WebAppConfiguration
import java.time.LocalDateTime

@WebAppConfiguration
@DataJpaTest
class QuestionAnswerMappingTest {
    @Autowired
    private lateinit var answerRepository: AnswerRepository

    @Autowired
    private lateinit var questionRepository: QuestionRepository

    @BeforeEach
    fun cleanUp() {
        answerRepository.deleteAll()
        questionRepository.deleteAll()
    }

    @Test
    fun mappingTest() {
        // given
        val question = QuestionEntity(
            0,
            "질문1",
            LocalDateTime.now().monthValue,
            LocalDateTime.now().dayOfMonth
        )
        val answer = AnswerEntity(0, "답변1", question)

        // when
        val savedQuestion = questionRepository.save(question)
        val savedAnswer = answerRepository.save(answer)

        // then
        assertThat(savedQuestion.id).isEqualTo(savedAnswer.question.id)
        assertThat(savedQuestion.sentence).isEqualTo(savedAnswer.question.sentence)
    }

    @Test
    fun mappingDeleteTest() {
        // given
        val question = QuestionEntity(
            0,
            "질문1",
            LocalDateTime.now().monthValue,
            LocalDateTime.now().dayOfMonth
        )
        val answer = AnswerEntity(0, "답변1", question)
        questionRepository.save(question)
        val savedAnswer = answerRepository.save(answer)

        // when
        answerRepository.delete(savedAnswer)

        // then
        assertThat(answerRepository.findAll().size).isEqualTo(0)
    }
}