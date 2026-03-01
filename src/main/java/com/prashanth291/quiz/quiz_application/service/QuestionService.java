package com.prashanth291.quiz.quiz_application.service;

import com.prashanth291.quiz.quiz_application.model.Question;
import com.prashanth291.quiz.quiz_application.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository repo;
    public List<Question> getAllQuestions()
    {
        return repo.findAll();
    }

    public Optional<Question> getQuestion(int id) {
        return repo.findById(id);
    }

    public Question addQuestion(Question ques) {
        return repo.save(ques);
    }

    public void deleteQuestion(int id) {
        repo.deleteById(id);
    }

    public Question updateQuestion(Question ques) {
        return repo.save(ques);
    }

    public List<Question> getQuestionsByCategory(String cat) { 
        return repo.findAllByCategory(cat);
    }
}
