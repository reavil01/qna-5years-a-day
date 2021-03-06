package com.yearsaday.qna.service

import com.yearsaday.qna.message.QuestionRequest
import com.yearsaday.qna.repository.QuestionDataService
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.core.io.ResourceLoader
import org.springframework.stereotype.Component
import java.io.FileReader

@Component
private class QuestionInputService(
    val questionDataService: QuestionDataService,
    val resourceLoader: ResourceLoader
) {
    val monthList = (1..12)
    val maxDayList = listOf(31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31)

    @EventListener(ApplicationReadyEvent::class)
    fun insertQuestionsToDB() {
        val resource = resourceLoader.getResource("classpath:questions.txt").file
        val questions = FileReader(resource).readLines()
        println(questions)
        monthList.zip(maxDayList).flatMap { (month, maxDay) ->
            (1..maxDay).map { month to it }
        }.forEachIndexed { index, (month, day) ->
            val question = QuestionRequest(questions[index], month, day)
            questionDataService.save(question)
        }
    }
}