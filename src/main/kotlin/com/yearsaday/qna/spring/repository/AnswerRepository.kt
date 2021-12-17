package com.yearsaday.qna.spring.repository

import com.yearsaday.qna.entity.Answer
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AnswerRepository: JpaRepository<Answer, Int> {

    fun findByYearAndQuestionId(year: Int, questionId: Int): Answer?

}
