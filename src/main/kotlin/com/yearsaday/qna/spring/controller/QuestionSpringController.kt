package com.yearsaday.qna.spring.controller

import com.yearsaday.qna.controller.QuestionController
import com.yearsaday.qna.message.*
import com.yearsaday.qna.repository.QuestionDataService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/questions")
class QuestionSpringController(
    questionService: QuestionDataService
) {

    val questionController = QuestionController(questionService)

    @GetMapping("/")
    fun getTodayQuestion(
    ): QuestionResponse {
        return questionController.getTodayQuestion()
    }

}