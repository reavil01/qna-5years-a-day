package com.yearsaday.qna.controller

import com.yearsaday.qna.message.AnswerCreateRequest
import com.yearsaday.qna.message.AnswerResponse
import com.yearsaday.qna.message.AnswerUpdateRequest
import com.yearsaday.qna.service.AnswerDataService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("/answers")
class AnswerController(
    val answerService: AnswerDataService
) {

    @GetMapping("")
    fun findAll(): List<AnswerResponse> {
        return answerService.findAll()
    }

    @PostMapping("/{questionId}")
    fun getTodayAnswer(
        @PathVariable("questionId") questionId: Int,
        response: HttpServletResponse
    ): AnswerResponse? {
        val year = LocalDateTime.now().year
        val result = answerService.findByYearAndQuestionId(year, questionId)

        return if (result.isPresent) {
            result.get()
        } else {
            response.status = HttpStatus.NO_CONTENT.value()
            null
        }
    }

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