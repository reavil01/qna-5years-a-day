package com.yearsaday.qna.repository

import com.yearsaday.qna.entity.Question
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import javax.persistence.Entity

@Repository
interface QuestionRepository: JpaRepository<Question, Int>{
}
