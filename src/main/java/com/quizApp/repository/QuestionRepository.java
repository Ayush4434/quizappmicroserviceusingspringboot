package com.quizApp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.quizApp.Question.Question;

public interface QuestionRepository extends JpaRepository<Question,Integer>{

	List<Question> findByCategory(String category);
     
	@Query(value="Select * from question q where q.category = :category order by random() limit :numQ",nativeQuery = true)
	List<Question> findRandomQuestionsByCategory(String category, int numQ);


	

}
