package com.yearsaday.qna.controller

import com.yearsaday.qna.message.*
import com.yearsaday.qna.repository.QuestionDataService
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
        return questionService.getTodayQuestion()
    }

    @GetMapping("/{id}")
    fun findById(
        @PathVariable("id") id: Int
    ): QuestionResponse {
        return questionService.select(id)
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