package com.yearsaday.qna.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.yearsaday.qna.message.QuestionRequest
import com.yearsaday.qna.message.QuestionResponse
import com.yearsaday.qna.service.QuestionDataService
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.time.LocalDateTime

@WebMvcTest(QuestionController::class)
class QuestionControllerTest {
    @Autowired
    private lateinit var mock: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @MockBean
    private lateinit var questionService: QuestionDataService

    private val API_URL = "/questions"
    private val QUESTION_ID = 1
    private val QUESTION_SENTENCE = "질문1"

    @Test
    fun findTest() {
        // given
        val questionResponse = getQuestionResponse(QUESTION_ID, QUESTION_SENTENCE)

        given(questionService.findById(QUESTION_ID))
            .willReturn(questionResponse)

        // when
        mock.perform(get("$API_URL/$QUESTION_ID"))
            .andExpect(status().isOk)
            .andExpect(content().string(containsString(QUESTION_SENTENCE)))

        // then
        verify(questionService).findById(QUESTION_ID)
    }

    @Test
    fun createTest() {
        // given
        val questionCreateRequest = QuestionRequest(
            QUESTION_SENTENCE,
            LocalDateTime.now().monthValue,
            LocalDateTime.now().dayOfMonth
        )
        val json = objectMapper.writeValueAsString(questionCreateRequest)

        val questionResponse = getQuestionResponse(QUESTION_ID, QUESTION_SENTENCE)
        given(questionService.save(questionCreateRequest))
            .willReturn(questionResponse)

        // when
        mock.perform(
            post(API_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        ).andExpect(status().isCreated)
            .andExpect(content().string(containsString(QUESTION_SENTENCE)))

        // then
        verify(questionService).save(questionCreateRequest)
    }

    @Test
    fun updateTest() {
        // given
        val questionUpdateRequest = QuestionRequest(
            QUESTION_SENTENCE,
            LocalDateTime.now().monthValue,
            LocalDateTime.now().dayOfMonth
        )
        val json = objectMapper.writeValueAsString(questionUpdateRequest)

        val questionResponse = getQuestionResponse(QUESTION_ID, QUESTION_SENTENCE)
        given(questionService.update(QUESTION_ID, questionUpdateRequest))
            .willReturn(questionResponse)

        // when
        mock.perform(
            put("$API_URL/$QUESTION_ID")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        ).andExpect(status().isOk)
            .andExpect(content().string(containsString(QUESTION_SENTENCE)))

        // then
        verify(questionService).update(QUESTION_ID, questionUpdateRequest)
    }

    @Test
    fun deleteTest() {
        // given

        // when
        mock.perform(delete("$API_URL/$QUESTION_ID"))
            .andExpect(status().isOk)

        // then
        verify(questionService).delete(QUESTION_ID)
    }

    private fun getQuestionResponse(
        questionId: Int,
        questionSentence: String
    ): QuestionResponse {
        return QuestionResponse(questionId, questionSentence)
    }
}