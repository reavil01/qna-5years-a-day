package com.yearsaday.qna.service

import com.yearsaday.qna.spring.entity.QuestionEntity
import com.yearsaday.qna.message.AnswerRequest
import com.yearsaday.qna.message.AnswerResponse
import com.yearsaday.qna.repository.AnswerDataService
import com.yearsaday.qna.spring.entity.AnswerEntity
import com.yearsaday.qna.spring.repository.AnswerRepository
import com.yearsaday.qna.spring.service.AnswerSpringDataService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.BDDMockito.*
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import java.time.LocalDateTime
import java.util.*

class AnswerDataServiceTest {
    private lateinit var service: AnswerDataService

    @Mock
    private lateinit var repository: AnswerRepository

    private val request = makeAnswerRequest()
    private val entity = request.toEntity()
    private val response = entity.toResponse()

    @BeforeEach
    fun init() {
        MockitoAnnotations.openMocks(this)
        service = AnswerSpringDataService(repository)
    }

    @Test
    fun saveAnswerTest() {
        // given
        given(repository.save(entity))
            .willReturn(entity)

        // when
        val result = service.create(request)

        // then
        verify(repository).save(entity)
        assertThat(result).isEqualTo(response)
    }

    @Test
    fun findAnswerTest() {
        // given
        val id = 1
        given(repository.findById(id))
            .willReturn(Optional.of(entity))

        // when
        val answer = service.select(id)

        // then
        verify(repository).findById(id)
        assertThat(answer).isEqualTo(response)
    }

    @Test
    fun findAnswerWhenAnswerIsNotExist() {
        assertThrows<NoSuchElementException> {
            // given
            val id = 1
            given(repository.findById(id))
                .willReturn(Optional.empty())

            // when
            service.select(id)
        }
    }

    @Test
    fun updateAnswerTest() {
        // given
        val updateRequest = AnswerRequest(
            "수정",
            request.question
        )
        val updatedEntity = updateRequest.toEntity()
        val updatedResponse = updatedEntity.toResponse()

        given(repository.findById(entity.id))
            .willReturn(Optional.of(entity))
        given(repository.save(updatedEntity))
            .willReturn(updatedEntity)

        // when
        val result = service.update(entity.id, updateRequest)

        // then
        verify(repository).findById(entity.id)
        verify(repository).save(updatedEntity)
        assertThat(result).isEqualTo(updatedResponse)
    }

    @Test
    fun updateAnswerWhenPreviousAnswerIsNotExist() {
        assertThrows<NoSuchElementException> {
            // given
            val updateRequest = AnswerRequest(
                "수정",
                request.question
            )

            given(repository.findById(entity.id))
                .willReturn(Optional.empty())

            // when
            service.update(entity.id, updateRequest)
        }
    }

    @Test
    fun deleteAnswerTest() {
        // given

        // when
        service.delete(entity.id)

        // then
        verify(repository).deleteById(entity.id)
    }

    @Test
    fun selectAllByQuestionIdTest() {
        // given
        val questionId = 1
        given(repository.findAllByQuestionId(questionId))
            .willReturn(listOf(entity, entity, entity))

        // when
        val result = service.selectAllByQuestionId(questionId)

        // then
        verify(repository).findAllByQuestionId(questionId)
        assertThat(result).isEqualTo(listOf(response, response, response))
    }

    @Test
    fun selectAllByQuestionIdWhenAnswerIsNotExist() {
        // given
        val questionId = 1
        given(repository.findAllByQuestionId(questionId))
            .willReturn(listOf())

        // when
        val result = service.selectAllByQuestionId(questionId)

        // then
        verify(repository).findAllByQuestionId(questionId)
        assertThat(result.size).isEqualTo(0)
    }

    @Test
    fun getTodayAnswerTest() {
        // given
        given(repository.findByYearAndQuestionId(response.year, request.question.id))
            .willReturn(entity)

        // when
        val result = service.getTodayAnswer(request.question.id)

        // then
        verify(repository).findByYearAndQuestionId(response.year, request.question.id)
        assertThat(result).isEqualTo(response)
    }

    @Test
    fun getTodayAnswerWhenQuestionIsNotExist() {
        // given
        given(repository.findByYearAndQuestionId(response.year, request.question.id))
            .willReturn(null)

        // when
        val result = service.getTodayAnswer(request.question.id)

        // then
        verify(repository).findByYearAndQuestionId(LocalDateTime.now().year, request.question.id)
        assertThat(result).isNull()
    }

    private fun makeAnswerRequest(): AnswerRequest {
        val answer = "답변"
        val question = QuestionEntity(
            1,
            "질문1",
            LocalDateTime.now().monthValue,
            LocalDateTime.now().dayOfMonth,
        )
        return AnswerRequest(answer, question)
    }

    fun AnswerEntity.toResponse() = AnswerResponse(
        this.id,
        this.answer,
        this.year,
        this.createdTime,
        this.updatedTime,
    )

    fun AnswerRequest.toEntity() = AnswerEntity(
        0,
        this.answer,
        this.question,
    )

}