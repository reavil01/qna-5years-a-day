package com.yearsaday.qna.repository

import com.yearsaday.qna.entity.Answer
import com.yearsaday.qna.entity.Question
import com.yearsaday.qna.spring.repository.AnswerRepository
import com.yearsaday.qna.spring.repository.QuestionRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import java.time.LocalDateTime

@DataJpaTest
class AnswerRepositoryTest {
    @Autowired
    private lateinit var repository: AnswerRepository

    @Autowired
    private lateinit var questionRepository: QuestionRepository

    val ANSWER = "답변1"
    val QUESTION by lazy {
        val question = Question(
            0,
            "질문1",
            LocalDateTime.now().monthValue,
            LocalDateTime.now().dayOfMonth
        )
        questionRepository.save(question)
    }

    @BeforeEach
    fun cleanUp() {
        repository.deleteAll()
        questionRepository.deleteAll()
    }

    @Test
    fun createdTimeTest() {
        // given
        val answer = makeAnswer()

        // when
        val result = repository.save(answer)

        // then
        val time = LocalDateTime.now()
        assertThat(result.createdTime?.minute).isEqualTo(time.minute)
        assertThat(result.createdTime?.second).isEqualTo(time.second)
        assertThat(result.updatedTime?.minute).isEqualTo(time.minute)
        assertThat(result.updatedTime?.second).isEqualTo(time.second)
    }

    @Test
    fun updateTimeTest() {
        // given
        val answer = makeAnswer()

        // when
        val result = repository.save(answer)
        val update = Answer(result.id, "바뀐 답", result.question)
        val updated = repository.save(update)
        repository.flush()

        // then
        val time = LocalDateTime.now()
        assertThat(result.createdTime?.minute).isEqualTo(updated.createdTime?.minute)
        assertThat(result.createdTime?.second).isEqualTo(updated.createdTime?.second)
        assertThat(updated.updatedTime?.minute).isEqualTo(time.minute)
        when {
            updated.updatedTime?.second!! == time.second ->
                assertThat(updated.updatedTime?.nano).isLessThan(time.nano)
            updated.updatedTime?.second!! < time.second -> assert(true)
            else -> assert(false)
        }
    }

    private fun makeAnswer(): Answer {
        return Answer(0, ANSWER, QUESTION)
    }
}