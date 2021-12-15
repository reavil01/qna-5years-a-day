package com.yearsaday.qna.service

import com.yearsaday.qna.message.QuestionCreateRequest
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import java.io.FileReader

@Component
private class QuestionInputService(
    val questionDataService: QuestionDataService
) {
    val monthList = (1..12)
    val maxDayList = listOf(31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31)

    @EventListener(ApplicationReadyEvent::class)
    fun insertQuestionsToDB() {
        val fileName = "questions.txt"
        val questions = FileReader(fileName).readLines()

        monthList.zip(maxDayList).flatMap { (month, maxDay) ->
            (1..maxDay).map { month to it }
        }.forEachIndexed { index, (month, day) ->
            val question = QuestionCreateRequest(questions[index], month, day)
            questionDataService.save(question)
        }
    }
}