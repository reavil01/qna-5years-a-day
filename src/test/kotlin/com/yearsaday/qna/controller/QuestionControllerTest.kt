package com.yearsaday.qna.controller

import com.yearsaday.qna.repository.QuestionDataService
import com.yearsaday.qna.spring.controller.QuestionSpringController
import org.junit.jupiter.api.Test
import org.mockito.Mockito.verify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest(QuestionSpringController::class)
class QuestionControllerTest {
    @Autowired
    private lateinit var mock: MockMvc

    @MockBean
    private lateinit var questionService: QuestionDataService

    private val API_URL = "/questions"

    @Test
    fun getTodayQuestion() {
        // given

        // when
        mock.perform(get("$API_URL/"))
            .andExpect(status().isOk)

        // then
        verify(questionService).getTodayQuestion()
    }
}