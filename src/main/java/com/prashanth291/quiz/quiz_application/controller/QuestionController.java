package com.prashanth291.quiz.quiz_application.controller;


import com.prashanth291.quiz.quiz_application.model.Question;
import com.prashanth291.quiz.quiz_application.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("questions")
public class QuestionController {

    @Autowired
    private QuestionService serv;

    @GetMapping("/allQuestions")
    public List<Question> getAllQuestions()
    {
        return serv.getAllQuestions();
    }

    @GetMapping("/question/{id}")
    public ResponseEntity<Question> getQuestion(@PathVariable int id) {

        return serv.getQuestion(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/add-question")
    public Question addQuestion(@RequestBody Question ques)
    {
        return serv.addQuestion(ques);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteQuestion(@PathVariable int id)
    {
        serv.deleteQuestion(id);
    }

    @PutMapping("/update/{id}")
    public Question updateQueston(@RequestBody Question ques)
    {
        return serv.updateQuestion(ques);
    }

    @GetMapping("/category/{category}")
    public List<Question> getQuestionsByCategory(@PathVariable String category)
    {
        return serv.getQuestionsByCategory(category);
    }
}
