package com.yearsaday.qna.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.yearsaday.qna.entity.Question
import com.yearsaday.qna.message.AnswerCreateRequest
import com.yearsaday.qna.message.AnswerResponse
import com.yearsaday.qna.message.AnswerUpdateRequest
import com.yearsaday.qna.service.AnswerDataService
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

@WebMvcTest(AnswerController::class)
class AnswerControllerTest {
    @Autowired
    private lateinit var mock: MockMvc

    @MockBean
    private lateinit var answerService: AnswerDataService

    private val question = Question(1, "질문1")
    private val objectMapper = ObjectMapper()

    @Test
    fun findAnswerTest() {
        // given
        val answerSentence = "답변1"
        val answerResponse = AnswerResponse(
            1,
            answerSentence,
            question,
            LocalDateTime.now(),
            LocalDateTime.now()
        )
        given(answerService.findById(1)).willReturn(answerResponse)

        // when
        mock.perform(get("/answers/1"))
            .andExpect(status().isOk)
            .andExpect(content().string(containsString(answerSentence)))

        // then
        verify(answerService).findById(1)
    }

    @Test
    fun createAnswerTest() {
        // given
        val answerCreateRequest = AnswerCreateRequest(
            "답변1",
            question,
        )
        val json = objectMapper.writeValueAsString(answerCreateRequest)

        // when
        mock.perform(
            post("/answers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        )
            .andExpect(status().isCreated)

        // then
        verify(answerService).save(answerCreateRequest)
    }

    @Test
    fun updateTest() {
        // given
        val answerSentence = "답변1"
        val updateId = 1
        val answerUpdateRequest = AnswerUpdateRequest(
            answerSentence,
            question
        )
        val json = objectMapper.writeValueAsString(answerUpdateRequest)

        // when
        mock.perform(
            put("/answers/$updateId")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        )
            .andExpect(status().isOk)

        // then
        verify(answerService).update(updateId, answerUpdateRequest)
    }

    @Test
    fun deleteTest() {
        // given
        val deleteId = 1

        // when
        mock.perform(delete("/answers/$deleteId"))
            .andExpect(status().isOk)

        // then
        verify(answerService).delete(deleteId)
    }
}