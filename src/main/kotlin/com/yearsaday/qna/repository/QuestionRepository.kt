package com.yearsaday.qna.repository

import com.yearsaday.qna.entity.Question
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface QuestionRepository: JpaRepository<Question, Int>{

    fun findByMonthAndDay(month: Int, day: Int): Question

}
