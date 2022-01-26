package com.yearsaday.qna.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.yearsaday.qna.spring.entity.QuestionEntity
import com.yearsaday.qna.message.AnswerRequest
import com.yearsaday.qna.message.AnswerResponse
import com.yearsaday.qna.repository.AnswerDataService
import com.yearsaday.qna.spring.controller.AnswerSpringController
import org.hamcrest.Matchers.containsString
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito.verify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.LocalDateTime

@WebMvcTest(AnswerSpringController::class)
class AnswerControllerTest {
    @Autowired
    private lateinit var mock: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @MockBean
    private lateinit var answerService: AnswerDataService

    private val API_URL = "/answers"

    private val question = QuestionEntity(
        1,
        "질문1",
        LocalDateTime.now().monthValue,
        LocalDateTime.now().dayOfMonth,
    )

    @Test
    fun findAnswerTest() {
        // given
        val now = LocalDateTime.now()
        val year = now.year
        val answerSentence = "답변1"
        val answerId = 1
        val answerResponse = AnswerResponse(
            answerId,
            answerSentence,
            year,
            now,
            now,
        )
        given(answerService.select(1)).willReturn(answerResponse)

        // when
        mock.perform(get("$API_URL/$answerId"))
            .andExpect(status().isOk)
            .andExpect(content().string(containsString(answerSentence)))

        // then
        verify(answerService).select(answerId)
    }

    @Test
    fun createAnswerTest() {
        // given
        val answerCreateRequest = AnswerRequest(
            "답변1",
            question,
        )
        val json = objectMapper.writeValueAsString(answerCreateRequest)

        // when
        mock.perform(
            post(API_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        ).andExpect(status().isCreated)

        // then
        verify(answerService).create(answerCreateRequest)
    }

    @Test
    fun createAnswerWhenExistId() {
        // given
        val savedId = 1
        val answerCreateRequest = AnswerRequest(
            "답변1",
            question,
        )
        val json = objectMapper.writeValueAsString(answerCreateRequest)

        val answerResponse = AnswerResponse(
            savedId,
            "수정전",
            2022,
            LocalDateTime.now(),
            LocalDateTime.now(),
        )
        given(answerService.getTodayAnswer(question.id))
            .willReturn(answerResponse)

        // when
        mock.perform(
            post(API_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        ).andExpect(status().isCreated)

        // then
        verify(answerService).update(savedId, answerCreateRequest)
    }

    @Test
    fun updateTest() {
        // given
        val answerSentence = "답변1"
        val updateId = 1
        val answerUpdateRequest = AnswerRequest(
            answerSentence,
            question
        )
        val json = objectMapper.writeValueAsString(answerUpdateRequest)

        // when
        mock.perform(
            put("$API_URL/$updateId")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        ).andExpect(status().isOk)

        // then
        verify(answerService).update(updateId, answerUpdateRequest)
    }

    @Test
    fun deleteTest() {
        // given
        val deleteId = 1

        // when
        mock.perform(delete("$API_URL/$deleteId"))
            .andExpect(status().isOk)

        // then
        verify(answerService).delete(deleteId)
    }

    @Test
    fun selectAllByQuestionIdTest() {
        // given
        val questionId = 1

        // when
        mock.perform(get("$API_URL?qId=$questionId"))
            .andExpect(status().isOk)

        // then
        verify(answerService).selectAllByQuestionId(questionId)
    }

    @Test
    fun getTodayAnswerTest() {
        // given
        val todayQuestionId = question.id
        val todayAnswer = AnswerResponse(
            1,
            "오늘",
            2022,
            LocalDateTime.now(),
            LocalDateTime.now(),
        )
        given(answerService.getTodayAnswer(todayQuestionId)).willReturn(todayAnswer)

        // when
        mock.perform(
            post("$API_URL/$todayQuestionId")
        )
            .andExpect(status().isOk)

        // then
        verify(answerService).getTodayAnswer(todayQuestionId)
    }

    @Test
    fun getTodayAnswerWhenNotExistQuestionTest() {
        // given
        val todayQuestionId = question.id

        // when
        mock.perform(
            post("$API_URL/$todayQuestionId")
        ).andExpect(status().isNoContent)

        // then
        verify(answerService).getTodayAnswer(todayQuestionId)
    }
}