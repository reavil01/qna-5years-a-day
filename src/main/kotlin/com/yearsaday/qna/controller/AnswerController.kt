package com.yearsaday.qna.controller

import com.yearsaday.qna.entity.Answer
import com.yearsaday.qna.message.AnswerCreateRequest
import com.yearsaday.qna.message.AnswerResponse
import com.yearsaday.qna.message.AnswerUpdateRequest
import com.yearsaday.qna.service.AnswerDataService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/answers")
class AnswerController(
    val answerService: AnswerDataService
) {

    @GetMapping("/{id}")
    fun findById(
        @PathVariable("id") id: Int
    ): AnswerResponse {
        return answerService.findById(id)
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(
        @RequestBody answerCreateRequest: AnswerCreateRequest
    ): AnswerResponse {
        return answerService.save(answerCreateRequest)
    }

    @PutMapping("/{id}")
    fun update(
        @PathVariable("id") id: Int,
        @RequestBody answerUpdateRequest: AnswerUpdateRequest
    ): AnswerResponse {
        return answerService.update(id, answerUpdateRequest)
    }

    @DeleteMapping("/{id}")
    fun delete(
        @PathVariable("id") id: Int
    ) {
        answerService.delete(id)
    }
}