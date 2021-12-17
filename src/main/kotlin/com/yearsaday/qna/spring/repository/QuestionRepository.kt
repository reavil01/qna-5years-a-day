package com.yearsaday.qna.spring.repository

import com.yearsaday.qna.spring.entity.QuestionEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface QuestionRepository: JpaRepository<QuestionEntity, Int>{

    fun findByMonthAndDay(month: Int, day: Int): QuestionEntity

}
