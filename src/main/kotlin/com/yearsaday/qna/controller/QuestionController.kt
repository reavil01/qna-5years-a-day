package com.yearsaday.qna.controller

import com.yearsaday.qna.message.*
import com.yearsaday.qna.service.QuestionDataService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@RestController
@RequestMapping("/questions")
class QuestionController(
    val questionService: QuestionDataService
) {

    @GetMapping("/")
    fun getTodayQuestion(
    ): QuestionResponse {
        val month = LocalDateTime.now().monthValue
        val day = LocalDateTime.now().dayOfMonth
        return questionService.findByMonthAndDays(month, day)
    }

    @GetMapping("/{id}")
    fun findById(
        @PathVariable("id") id: Int
    ): QuestionResponse {
        return questionService.findById(id)
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(
        @RequestBody questionRequest: QuestionRequest
    ): QuestionResponse {
        return questionService.save(questionRequest)
    }

    @PutMapping("/{id}")
    fun update(
        @PathVariable("id") id: Int,
        @RequestBody questionRequest: QuestionRequest
    ): QuestionResponse {
        return questionService.update(id, questionRequest)
    }

    @DeleteMapping("/{id}")
    fun delete(
        @PathVariable("id") id: Int
    ) {
        questionService.delete(id)
    }
}