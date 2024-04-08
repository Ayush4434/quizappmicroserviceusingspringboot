package com.quizApp.service;

//import org.mockito.Mock;
//
//import com.quizApp.repository.QuestionRepository;
//import com.quizApp.repository.QuizDao;
//
//public class QuizServiceTest {
//	 @Mock
//	  private QuestionRepository repos;
//	  private QuizDao dao;
//	  AutoCloseable auto;
//	  
//	 
//}
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.quizApp.Question.Question;
import com.quizApp.Question.QuestionWrapper;
import com.quizApp.Question.Quiz;
import com.quizApp.Question.Response;
import com.quizApp.repository.QuestionRepository;
import com.quizApp.repository.QuizDao;
import com.quizApp.service.QuizService;

public class QuizServiceTest {

    private QuizDao quizDaoMock;
    private QuestionRepository questionRepositoryMock;
    private QuizService quizService;
    AutoCloseable auto;
    Question q, q1, q2, q3, q4;
    List<Question> questionList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        auto = MockitoAnnotations.openMocks(this);
        quizDaoMock = mock(QuizDao.class);
        questionRepositoryMock = mock(QuestionRepository.class);
        quizService = new QuizService(quizDaoMock, questionRepositoryMock);

        q = new Question(2, "Java", "Easy", "Object-Oriented Programming", "Object-Oriented Protocol",
                "Object-Oriented Processing", "Object-Oriented Procedure", "What does OOP stand for in Java?",
                "Object-Oriented Programming");
        q1 = new Question(3, "Java", "Medium", "final", "const", "static", "let",
                "Which keyword is used to define a constant in Java?", "final");
        q2 = new Question(4, "Python", "Easy", "Mutable", "Immutable", "Sequential", "Linear",
                "What is a Python list?", "Mutable");
        q3 = new Question(5, "Python", "Medium", "The current object instance", "The parent class", "The child class",
                "The global namespace", "In Python, what does the 'self' keyword represent in a class?",
                "The current object instance");
        q4 = new Question(6, "Python", "Medium", "Lists", "Tuples", "Dictionary", "Class",
                "Which of the following is not a core data type in Python?", "Class");
        questionList.add(q);
        questionList.add(q1);
        questionList.add(q2);
        questionList.add(q3);
        questionList.add(q4);
    }

    @AfterEach
    void tearDown() throws Exception {
        auto.close();
    }

    @Test
    void testCreateQuiz_Success() {
    	when(quizDaoMock.save(Mockito.any())).thenReturn(new Quiz());

        ResponseEntity<String> response = quizService.createQuiz("Java", 3, "Java Quiz");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo("Success");
    }

    @Test
    void testGetQuizService_Success() {
        Quiz quiz = new Quiz();
        quiz.setTitle("Java Quiz");
        quiz.setQuestion(questionList);
        when(quizDaoMock.findById(anyInt())).thenReturn(Optional.of(quiz));

        ResponseEntity<List<QuestionWrapper>> response = quizService.getQuizService(1);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().size()).isEqualTo(questionList.size());
        assertThat(response.getBody().get(0).getQuestiontitle()).isEqualTo(q.getQuestiontitle());
    }

    @Test
    void testCalculateResult_Success() {
        Quiz quiz = new Quiz();
        quiz.setQuestion(questionList);
        when(quizDaoMock.findById(anyInt())).thenReturn(Optional.of(quiz));

        List<Response> responses = new ArrayList<>();
        Response response1 = new Response();
        response1.setResponse("Object-Oriented Programming");
        responses.add(response1);
        Response response2 = new Response();
        response2.setResponse("final");
        responses.add(response2);
        Response response3 = new Response();
        response3.setResponse("Mutable");
        responses.add(response3);
        Response response4 = new Response();
        response4.setResponse("The current object instance");
        responses.add(response4);
        Response response5 = new Response();
        response5.setResponse("Classes");
        responses.add(response5);

        ResponseEntity<Integer> result = quizService.calculateResult(1, responses);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(4); 
    }
}


