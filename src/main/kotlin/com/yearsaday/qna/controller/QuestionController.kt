package com.yearsaday.qna.controller

import com.yearsaday.qna.message.*
import com.yearsaday.qna.service.QuestionDataService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/questions")
class QuestionController(
    val questionService: QuestionDataService
) {

    @GetMapping("/{id}")
    fun findById(
        @PathVariable("id") id: Int
    ): QuestionResponse {
        return questionService.findById(id)
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(
        @RequestBody questionCreateRequest: QuestionCreateRequest
    ): QuestionResponse {
        return questionService.save(questionCreateRequest)
    }

    @PutMapping("/{id}")
    fun update(
        @PathVariable("id") id: Int,
        @RequestBody questionUpdateRequest: QuestionUpdateRequest
    ): QuestionResponse {
        return questionService.update(id, questionUpdateRequest)
    }

    @DeleteMapping("/{id}")
    fun delete(
        @PathVariable("id") id: Int
    ) {
        questionService.delete(id)
    }
}