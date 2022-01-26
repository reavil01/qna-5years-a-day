package com.yearsaday.qna.spring.repository

import com.yearsaday.qna.spring.entity.AnswerEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AnswerRepository: JpaRepository<AnswerEntity, Int> {

    fun findByYearAndQuestionId(year: Int, questionId: Int): AnswerEntity?

    fun findAllByQuestionId(questionId: Int): List<AnswerEntity>

}
