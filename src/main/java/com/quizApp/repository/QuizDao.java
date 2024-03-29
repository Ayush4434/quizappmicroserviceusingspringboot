package com.quizApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.quizApp.Question.Quiz;

public interface QuizDao extends JpaRepository<Quiz,Integer> {

}
