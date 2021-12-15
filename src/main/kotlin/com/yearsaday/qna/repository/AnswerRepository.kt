package com.yearsaday.qna.repository

import com.yearsaday.qna.entity.Answer
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface AnswerRepository: JpaRepository<Answer, Int> {
    fun findByYearAndQuestionId(year: Int, questionId: Int): Optional<Answer>
}
