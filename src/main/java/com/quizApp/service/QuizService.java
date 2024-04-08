package com.quizApp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.quizApp.Question.Question;
import com.quizApp.Question.QuestionWrapper;
import com.quizApp.Question.Quiz;
import com.quizApp.Question.Response;
import com.quizApp.repository.QuestionRepository;
import com.quizApp.repository.QuizDao;

@Service
public class QuizService {
	@Autowired
	QuizDao dao;
	
	@Autowired
	QuestionRepository qDao;
    
	public QuizService(QuizDao quizDaoMock, QuestionRepository questionRepositoryMock) {
		dao = quizDaoMock;
		qDao = questionRepositoryMock;
		
	}

	public ResponseEntity<String> createQuiz(String category, int numQ, String title) {
		List<Question> questions = qDao.findRandomQuestionsByCategory(category,numQ);
		Quiz quiz = new Quiz();
	   quiz.setTitle(title);
	   quiz.setQuestion(questions);
	   dao.save(quiz);
	   return new ResponseEntity<>("Success",HttpStatus.CREATED);
	}

	public ResponseEntity<List<QuestionWrapper>> getQuizService(Integer id) {
		Optional<Quiz> quiz = dao.findById(id);
		List<Question> qdb = quiz.get().getQuestion(); 
		List<QuestionWrapper> quser = new ArrayList<>();
		for(Question q : qdb)
		{
			QuestionWrapper qw = new QuestionWrapper(q.getId(),q.getOption1(),q.getOption2(),q.getOption3(),q.getOption4(),q.getQuestiontitle());
			quser.add(qw);
		}
		return new ResponseEntity<>(quser,HttpStatus.OK);
	}

	public ResponseEntity<Integer> calculateResult(Integer id, List<Response> response) {
		Optional<Quiz> quiz = dao.findById(id);
		List<Question> questions = quiz.get().getQuestion();
		int i=0;
		int ri = 0;
		for(Response res: response)
		{
			if (res.getResponse().equals(questions.get(i).getRightanswer())) {
				ri++;
			}
		   i++;
		}
		return new ResponseEntity<>(ri,HttpStatus.OK);
	}

}
