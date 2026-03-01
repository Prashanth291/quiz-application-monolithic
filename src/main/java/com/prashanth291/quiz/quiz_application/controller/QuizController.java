package com.prashanth291.quiz.quiz_application.controller;

import com.prashanth291.quiz.quiz_application.model.Question;
import com.prashanth291.quiz.quiz_application.model.QuestionWrapper;
import com.prashanth291.quiz.quiz_application.model.Quiz;
import com.prashanth291.quiz.quiz_application.model.QuizResponse;
import com.prashanth291.quiz.quiz_application.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quiz")
public class QuizController {

    @Autowired
    private QuizService quizService;

    @PostMapping("/create")
    public ResponseEntity<String> createQuiz(@RequestParam String category, @RequestParam int numQues, @RequestParam String title)
    {
        return quizService.createQuiz(category, numQues, title);
    }

    @GetMapping("/get-quiz/{id}")
    public ResponseEntity<List<QuestionWrapper>> getQuizById(@PathVariable Integer id)
    {
        return quizService.getQuizById(id);
    }

    @PostMapping("/submit/{id}")
    public ResponseEntity<Integer> calculateScore(@PathVariable int id,@RequestBody List<QuizResponse> quizResponse)
    {
        return quizService.calculateScore(id,quizResponse);
    }
}
