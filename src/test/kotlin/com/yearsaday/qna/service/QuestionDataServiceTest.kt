package com.yearsaday.qna.service

import com.yearsaday.qna.message.QuestionRequest
import com.yearsaday.qna.message.QuestionResponse
import com.yearsaday.qna.repository.QuestionDataService
import com.yearsaday.qna.spring.entity.QuestionEntity
import com.yearsaday.qna.spring.repository.QuestionRepository
import com.yearsaday.qna.spring.service.QuestionSpringDataService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.verify
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import java.lang.NullPointerException
import java.time.LocalDateTime
import java.util.*

class QuestionDataServiceTest {
    private lateinit var service: QuestionDataService

    @Mock
    private lateinit var repository: QuestionRepository

    private val request = makeQuestionRequest()
    private val entity = request.toEntity()
    private val response = entity.toResponse()

    @BeforeEach
    fun init() {
        MockitoAnnotations.openMocks(this)
        service = QuestionSpringDataService(repository)
    }

    @Test
    fun saveQuestionTest() {
        // given
        given(repository.save(entity))
            .willReturn(entity)

        // when
        val result = service.save(request)

        // then
        verify(repository).save(entity)
        assertThat(result).isEqualTo(response)
    }

    @Test
    fun findQuestionTest() {
        // given
        val id = 1
        given(repository.findById(id))
            .willReturn(Optional.of(entity))

        // when
        val result = service.select(id)

        // then
        verify(repository).findById(id)
        assertThat(result).isEqualTo(response)
    }

    @Test
    fun findQuestionWhenQuestionIsNotExist() {
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
    fun updateQuestionTest() {
        // given
        val updateRequest = QuestionRequest(
            "수정",
            LocalDateTime.now().monthValue,
            LocalDateTime.now().dayOfMonth
        )
        val updatedEntity = updateRequest.toEntity()
        val updateResponse = updatedEntity.toResponse()

        given(repository.findById(entity.id))
            .willReturn(Optional.of(entity))
        given(repository.save(updatedEntity))
            .willReturn(updatedEntity)

        // when
        val result = service.update(entity.id, updateRequest)

        // then
        verify(repository).findById(entity.id)
        verify(repository).save(updatedEntity)
        assertThat(result).isEqualTo(updateResponse)
    }

    @Test
    fun updateQuestionWhenPreviousQuestionIsNotExist() {
        assertThrows<NoSuchElementException> {
            // given
            val updateRequest = QuestionRequest(
                "수정",
                LocalDateTime.now().monthValue,
                LocalDateTime.now().dayOfMonth
            )

            given(repository.findById(entity.id))
                .willReturn(Optional.empty())

            // when
            service.update(entity.id, updateRequest)
        }
    }

    @Test
    fun deleteQuestionTest() {
        // given

        // when
        service.delete(entity.id)

        // then
        verify(repository).deleteById(entity.id)
    }

    @Test
    fun getTodayQuestionTest() {
        // given
        given(repository.findByMonthAndDay(request.month, request.day))
            .willReturn(entity)

        // when
        val result = service.getTodayQuestion()

        // then
        verify(repository).findByMonthAndDay(request.month, request.day)
        assertThat(result).isEqualTo(response)
    }

    @Test
    fun getTodayQuestionWhenQuestionIsNotExist() {
        assertThrows<NullPointerException> {
            // given
            given(repository.findByMonthAndDay(request.month, request.day))
                .willReturn(null)

            // when
            service.getTodayQuestion()
        }
    }

    private fun makeQuestionRequest(): QuestionRequest {
        return QuestionRequest(
            "질문1",
            LocalDateTime.now().monthValue,
            LocalDateTime.now().dayOfMonth,
        )
    }

    fun QuestionRequest.toEntity() = QuestionEntity(
        0,
        this.sentence,
        this.month,
        this.day,
    )

    fun QuestionEntity.toResponse() = QuestionResponse(
        this.id,
        this.sentence,
    )
}