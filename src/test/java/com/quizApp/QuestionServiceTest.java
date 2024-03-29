package com.quizApp;

import org.mockito.Mock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.quizApp.Question.Question;
import com.quizApp.repository.QuestionRepository;
import com.quizApp.service.QuestionService;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;
import java.util.ArrayList;
import java.util.List;
public class QuestionServiceTest {
  @Mock
  private QuestionRepository repos;
  private QuestionService service;
  AutoCloseable auto;
  Question q,q1,q2,q3,q4;
  List<Question> questionList = new ArrayList<>();
  @BeforeEach
  void setUp() {
      auto = MockitoAnnotations.openMocks(this);
      service = new QuestionService(repos);
      q = new Question(2,"Java","Easy","Object-Oriented Programming","Object-Oriented Protocol","Object-Oriented Processing","Object-Oriented Procedure","What does OOP stand for in Java?","Object-Oriented Programming");
      q1 = new Question(3, "Java", "Medium", "final", "const", "static", "let", "Which keyword is used to define a constant in Java?", "final");
      q2 = new Question(4, "Python", "Easy", "Mutable", "Immutable", "Sequential", "Linear", "What is a Python list?", "Mutable");
      q3 = new Question(5, "Python", "Medium", "The current object instance", "The parent class", "The child class", "The global namespace", "In Python, what does the 'self' keyword represent in a class?", "The current object instance");
      q4 = new Question(6, "Python", "Medium", "Lists", "Tuples", "Dictionary", "Class", "Which of the following is not a core data type in Python?", "Class");
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
  void testaddquestion() {
      mock(Question.class);
      mock(QuestionRepository.class);
      when(repos.save(q1)).thenReturn(q1);

      // Call the method under test
      ResponseEntity<String> response = service.addquestion(q1);

      // Assert the response
      assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
      assertThat(response.getBody()).isEqualTo("success");
       
  }
  @Test
  void testgetAllQuestion()
  {
	  mock(Question.class);
      mock(QuestionRepository.class);
     
      when(repos.findAll()).thenReturn(questionList);

      List<Question> result = service.getAllQuestion().getBody();
      assertThat(result.get(4).getCategory()).isEqualTo(q4.getCategory());
      
  }
  @Test
  void testgetQuestionByCategory()
  {
	  mock(Question.class);
      mock(QuestionRepository.class);
      when(repos.findByCategory("Python")).thenAnswer(invocation -> {
          List<Question> javaQuestions = new ArrayList<>();
          for (Question question : questionList) {
              if ("Python".equals(question.getCategory())) {
                  javaQuestions.add(question);
              }
          }
          return javaQuestions;
      });
      ResponseEntity<List<Question>> responseEntity = service.getQuestionByCategory("Python");
      List<Question> result = responseEntity.getBody();
      assertThat(result).isNotNull();
      assertThat(result.size()).isEqualTo(3);
      assertThat(result.get(1).getCategory()).isEqualTo("Python");
  }
  @Test
  void testDeleteQuestion()
  {
	  mock(Question.class);
      mock(QuestionRepository.class, Mockito.CALLS_REAL_METHODS);

      doNothing().when(repos).deleteById(anyInt());

      // Call the service method
      ResponseEntity<String> response = service.DeleteQuestion(123);

      // Verify the behavior
      verify(repos, times(1)).deleteById(123); 
      assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
      assertThat(response.getBody()).isEqualTo("Successfully Deleted");
  }
}
