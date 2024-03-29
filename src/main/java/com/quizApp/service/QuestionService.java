package com.quizApp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import com.quizApp.Question.Question;
import com.quizApp.repository.QuestionRepository;

@Service
public class QuestionService {
	@Autowired
	QuestionRepository repos;

	public QuestionService(QuestionRepository repos2) {
		repos = repos2;
	}

	public ResponseEntity<String> addquestion(Question question) {
		try {
		repos.save(question);
		return new ResponseEntity<>("success",HttpStatus.CREATED);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return new ResponseEntity<>("Failure",HttpStatus.BAD_REQUEST);
		
	}

	public ResponseEntity<List<Question>> getAllQuestion() {
		try {
	     return new ResponseEntity<>(repos.findAll(),HttpStatus.OK);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return new ResponseEntity<>(new ArrayList<>(),HttpStatus.BAD_REQUEST); 
	}

	public ResponseEntity<List<Question>> getQuestionByCategory(String category) {
		try {
		return new ResponseEntity<>(repos.findByCategory(category),HttpStatus.OK);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return new ResponseEntity<>(new ArrayList<>(),HttpStatus.BAD_REQUEST);
	}

	public ResponseEntity<String> DeleteQuestion(int id) {
		try {
		repos.deleteById(id);
		return new ResponseEntity<>("Successfully Deleted",HttpStatus.OK);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return new ResponseEntity<>("Not Deleted Try Again",HttpStatus.BAD_REQUEST);
	}

	public Question updateQuestion(Question q,int id) {
		Optional<Question> optionalQuestion = repos.findById(id);
        if (optionalQuestion.isPresent()) {
            Question question = optionalQuestion.get();
            question.setDifficultylevel(q.getDifficultylevel());
            question.setCategory(q.getCategory());
            question.setOption1(q.getOption1());
            question.setOption2(q.getOption2());
            question.setOption3(q.getOption3());
            question.setOption4(q.getOption4());
            question.setQuestiontitle(q.getQuestiontitle());
            question.setRightanswer(q.getRightanswer());
            return repos.save(question);
        } else {
            throw new RuntimeException("Question not found with id: " + id);
        }
	}

	public ResponseEntity<String> addquestions(List<Question> question) {
		try {
			repos.saveAll(question);
			return new ResponseEntity<>("success",HttpStatus.CREATED);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			return new ResponseEntity<>("Failure",HttpStatus.BAD_REQUEST);
	}

	
    
}