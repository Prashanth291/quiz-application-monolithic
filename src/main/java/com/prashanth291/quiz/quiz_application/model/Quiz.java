package com.prashanth291.quiz.quiz_application.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToMany;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Quiz {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Integer Id;
    private String title;
    @ManyToMany
    private List<Question> questions;

}
