package com.yearsaday.qna.controller

import com.yearsaday.qna.message.AnswerRequest
import com.yearsaday.qna.message.AnswerResponse
import com.yearsaday.qna.repository.AnswerDataService
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
        return answerService.selectAll()
    }

    @PostMapping("/{questionId}")
    fun getTodayAnswer(
        @PathVariable("questionId") questionId: Int,
        response: HttpServletResponse
    ): AnswerResponse? {
        val year = LocalDateTime.now().year
        val result = answerService.selectByYearAndQuestionId(year, questionId)

        return result ?: let{
            response.status = HttpStatus.NO_CONTENT.value()
            null
        }
    }

    @GetMapping("/{id}")
    fun findById(
        @PathVariable("id") id: Int
    ): AnswerResponse? {
        return answerService.select(id)
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(
        @RequestBody answerRequest: AnswerRequest
    ): AnswerResponse {
        return answerService.preventDuplicationSave(answerRequest)
    }

    @PutMapping("/{id}")
    fun update(
        @PathVariable("id") id: Int,
        @RequestBody answerRequest: AnswerRequest
    ): AnswerResponse {
        return answerService.update(id, answerRequest)
    }

    @DeleteMapping("/{id}")
    fun delete(
        @PathVariable("id") id: Int
    ) {
        answerService.delete(id)
    }
}