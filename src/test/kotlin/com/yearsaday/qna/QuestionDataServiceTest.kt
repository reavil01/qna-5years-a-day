package com.yearsaday.qna

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

class QuestionDataServiceTest {
    @Autowired
    private lateinit var service: QuestionDataService
    @Autowired
    private lateinit var repository: QuestionRepository

    val ID = 1
    val SENTENCE = "질문1"

    @BeforeEach
    fun cleanUp() {
        service.deleteAll()
    }

    @Test
    fun saveQuestionTest() {
        // given
        val question = makeQuestion()

        // when
        val result = service.save(question)

        // then
        assertThat(repository.findAll().size).isEqualTo(1)
        assertThat(result.id).isEqualTo(1)
        assertThat(result.sentence).isEqualTo(sentence)
    }

    @Test
    fun findQuestionTest() {
        // given
        val question = makeQuestion()
        service.save(question)
        assertThat(repository.findAll().size).isEqualTo(1)

        // when
        val result = service.findById(question.id)

        // then
        assertThat(result.id).isEqualTo(question.id)
        assertThat(result.sentence).isEqualTo(question.sentence)
    }

    @Test
    fun deleteQuestionTest() {
        // given
        val question = makeQuestion()
        service.save(question)
        assertThat(repository.findAll().size).isEqualTo(1)

        // when
        service.delete(question.id)

        // then
        assertThat(service.findAll().size).isEqualTo(0)
    }

    @Test
    fun updateQuestionTest() {
        // given
        val question = makeQuestion()
        service.save(question)
        assertThat(repository.findAll().size).isEqualTo(1)
        val sentence = "질문2"
        val request = QuestionUpdateRequest(ID, sentence)

        // when
        service.update(ID, request)

        // then
        val updated = repository.findBy(ID)
        assertThat(updated.sentence).isEqualTo(sentence)
    }


    private fun makeQuestion(): Question {
        return Question(ID, SENTENCE)
    }
}