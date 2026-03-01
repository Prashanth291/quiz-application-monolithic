package com.prashanth291.quiz.quiz_application.repository;

import com.prashanth291.quiz.quiz_application.model.Question;
import com.prashanth291.quiz.quiz_application.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizRepository extends JpaRepository<Quiz, Integer> {
}
