package com.yearsaday.qna.spring.controller

import com.yearsaday.qna.controller.AnswerController
import com.yearsaday.qna.message.AnswerRequest
import com.yearsaday.qna.message.AnswerResponse
import com.yearsaday.qna.repository.AnswerDataService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("/answers")
class AnswerSpringController(
    answerService: AnswerDataService,
) {

    val answerControllerImpl = AnswerController(answerService)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(
        @RequestBody answerRequest: AnswerRequest
    ): AnswerResponse {
        return answerControllerImpl.save(answerRequest)
    }

    @DeleteMapping("/{id}")
    fun delete(
        @PathVariable("id") id: Int
    ) {
        answerControllerImpl.delete(id)
    }

    @PutMapping("/{id}")
    fun update(
        @PathVariable("id") id: Int,
        @RequestBody answerRequest: AnswerRequest
    ): AnswerResponse {
        return answerControllerImpl.update(id, answerRequest)
    }

    @GetMapping("/{id}")
    fun select(
        @PathVariable id: Int
    ): AnswerResponse? {
        return answerControllerImpl.select(id)
    }

    @GetMapping()
    fun selectAllByQuestionId(
        @RequestParam("qId") quesitonId: Int
    ): List<AnswerResponse> {
        return answerControllerImpl.selectAllByQuestionId(quesitonId)
    }

    // FIX: Answer에 대한 정보를 가져오는 URL에서 questionId를 넘기는 것은
    // RestAPI의 목적에 맞지 않는 것 같음. query로 날리는게 좋을듯.
    @PostMapping("/{questionId}")
    fun getTodayAnswer(
        @PathVariable("questionId") questionId: Int,
        response: HttpServletResponse
    ): AnswerResponse? {
        val result = answerControllerImpl.getTodayAnswer(questionId)

        return result ?: let{
            response.status = HttpStatus.NO_CONTENT.value()
            null
        }
    }

}